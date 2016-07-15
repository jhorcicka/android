package cz.jhorcicka.counters.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.jhorcicka.counters.model.Counter;

public class Db extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "counters";
    private static final String TABLE_NAME = "counter";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_NOTE= "note";
    private static final String COL_VALUE = "value";
    private static final String COL_CREATED = "created";
    private static final String COL_LAST_UPDATE = "last_update";
    
	public Db(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("CREATE TABLE %s (" +
			"%s INTEGER PRIMARY KEY, " + 
			"%s TEXT, " + 
			"%s TEXT, " + 
			"%s INTEGER, " + 
			"%s TEXT, " + 
			"%s TEXT " + 
		") ", TABLE_NAME, COL_ID, COL_NAME, COL_NOTE, COL_VALUE, COL_CREATED, COL_LAST_UPDATE);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void addCounter(Counter counter) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COL_NAME, counter.getName()); 
		values.put(COL_NOTE, counter.getNote()); 
		values.put(COL_VALUE, counter.getValue()); 
		values.put(COL_CREATED, counter.getCreated()); 
		values.put(COL_LAST_UPDATE, counter.getLastUpdate()); 

		db.insert(TABLE_NAME, null, values);
		db.close(); 
	}
	

	public Counter getCounter(long id) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { 
				COL_ID, COL_NAME, COL_NOTE, COL_VALUE, COL_CREATED, COL_LAST_UPDATE
			}, COL_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		Counter counter = getCounterFromCursor(cursor);
		
		return counter;
	}
	
	private Counter getCounterFromCursor(Cursor cursor) {
		long counterId = cursor.getLong(0);
		String counterName = cursor.getString(1);
		String counterNote = cursor.getString(2);
		long counterValue = cursor.getLong(3);
		String counterCreated = cursor.getString(4);
		String counterLastUpdate = cursor.getString(5);
		
		Counter counter = new Counter(counterName);
		counter.setId(counterId);
		counter.setNote(counterNote);
		counter.setValue(counterValue);
		counter.setCreated(counterCreated);
		counter.setLastUpdate(counterLastUpdate);
				
		return counter;
	}

	public List<Counter> getAllCounters() {
		List<Counter> counterList = new ArrayList<Counter>();
		String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s", 
			COL_ID, COL_NAME, COL_NOTE, COL_VALUE, COL_CREATED, COL_LAST_UPDATE, TABLE_NAME);

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				counterList.add(getCounterFromCursor(cursor));
			} 
			while (cursor.moveToNext());
		}

		return counterList;
	}

	public int getCountersCount() {
		String countQuery = String.format("SELECT * FROM %s", TABLE_NAME);
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		return count;
	}

	public int updateCounter(Counter counter) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, counter.getName());
		values.put(COL_VALUE, counter.getValue());
		values.put(COL_NOTE, counter.getNote());
		Date now = new Date();
		values.put(COL_LAST_UPDATE, now.toString());
		String query = String.format("UPDATE %s SET %s = %s WHERE %s = %s", 
			TABLE_NAME, COL_VALUE, counter.getValue(), COL_ID, counter.getId());
		String[] whereArgs = new String[] { String.valueOf(counter.getId()) };
		SQLiteDatabase db = getWritableDatabase();
		int rowsUpdated = db.update(TABLE_NAME, values, COL_ID + " = ?", whereArgs);
		db.close();
		
		return rowsUpdated;
	}

	public void deleteCounter(Counter counter) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, COL_ID + " = ?",
			new String[] { String.valueOf(counter.getId()) });
		db.close();
	}
}
