package cz.jhorcicka.tasks.db;

import java.util.ArrayList;
import java.util.List;

import cz.jhorcicka.tasks.model.Interval;
import cz.jhorcicka.tasks.model.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "time_manager";
    private static final String TABLE_NAME_TASKS = "task";
    private static final String TABLE_NAME_INTERVALS = "interval";
    private static final String COL_TASK_ID = "id";
    private static final String COL_TASK_NAME = "name";
    private static final String COL_TASK_CREATED = "createdDate";
    private static final String COL_INTERVAL_ID = "id";
    private static final String COL_INTERVAL_FK_TASK = "fx_task";
    private static final String COL_INTERVAL_STARTED = "started";
    private static final String COL_INTERVAL_STOPPED = "stopped";

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTasks = String.format(
                "CREATE TABLE %s (" + "%s INTEGER PRIMARY KEY, " + "%s TEXT, "
                        + "%s TEXT " + "); ", TABLE_NAME_TASKS, COL_TASK_ID,
                COL_TASK_NAME, COL_TASK_CREATED);
        db.execSQL(createTasks);

        String createIntervals = String.format("CREATE TABLE %s ("
                        + "%s INTEGER PRIMARY KEY, " + "%s INTEGER, " + "%s TEXT, "
                        + "%s TEXT " + "); ", TABLE_NAME_INTERVALS, COL_INTERVAL_ID,
                COL_INTERVAL_FK_TASK, COL_INTERVAL_STARTED,
                COL_INTERVAL_STOPPED);
        db.execSQL(createIntervals);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INTERVALS);
        onCreate(db);
    }

    public Task getTask(long id) {
        String selectQuery = String.format(
                "SELECT %s, %s, %s FROM %s WHERE id = %d", COL_TASK_ID,
                COL_TASK_NAME, COL_TASK_CREATED, TABLE_NAME_TASKS, id);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Task task = null;

        if (cursor.moveToFirst()) {
            task = getTaskFromCursor(cursor);
            task.setIntervals(getIntervals(task.getId()));
        }

        return task;
    }

    public List<Task> getAllTasks() {
        String selectQuery = String.format("SELECT %s, %s, %s FROM %s",
                COL_TASK_ID, COL_TASK_NAME, COL_TASK_CREATED, TABLE_NAME_TASKS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Task> taskList = new ArrayList<Task>();

        if (cursor.moveToFirst()) {
            do {
                Task task = getTaskFromCursor(cursor);
                task.setIntervals(getIntervals(task.getId()));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    private Task getTaskFromCursor(Cursor cursor) {
        long taskId = cursor.getLong(0);
        String taskName = cursor.getString(1);
        String taskCreated = cursor.getString(2);

        Task task = new Task();
        task.setId(taskId);
        task.setName(taskName);
        task.setCreated(taskCreated);

        return task;
    }

    public void deleteTask(Task task) {
        List<Interval> intervals = task.getIntervals();

        for (Interval i : intervals) {
            deleteInterval(i);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_TASKS, COL_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void deleteInterval(Interval interval) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_INTERVALS, COL_INTERVAL_ID + " = ?",
                new String[]{String.valueOf(interval.getId())});
        db.close();
    }

    public List<Interval> getIntervals(long taskId) {
        String selectQuery = String.format(
                "SELECT %s, %s, %s FROM %s WHERE %s = %s", COL_INTERVAL_ID,
                COL_INTERVAL_STARTED, COL_INTERVAL_STOPPED,
                TABLE_NAME_INTERVALS, COL_INTERVAL_FK_TASK, taskId);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Interval> intervalList = new ArrayList<Interval>();

        if (cursor.moveToFirst()) {
            do {
                Interval interval = getIntervalFromCursor(cursor);
                intervalList.add(interval);
            } while (cursor.moveToNext());
        }

        return intervalList;
    }

    private Interval getIntervalFromCursor(Cursor cursor) {
        long intervalId = cursor.getLong(0);
        String started = cursor.getString(1);
        String stopped = cursor.getString(2);

        Interval interval = new Interval();
        interval.setId(intervalId);
        interval.setStarted(started);
        interval.setStopped(stopped);

        return interval;
    }

    public void saveTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TASK_NAME, task.getName());
        values.put(COL_TASK_CREATED, task.getCreated());

        if (task.getId() == 0) {
            long newId = db.insert(TABLE_NAME_TASKS, null, values);
            task.setId(newId);
        } else {
            String[] whereArgs = new String[]{String.valueOf(task.getId())};
            db.update(TABLE_NAME_TASKS, values, COL_TASK_ID + " = ?", whereArgs);
        }

        for (Interval interval : task.getIntervals()) {
            saveInterval(interval, task.getId());
        }

        db.close();
    }

    public void saveInterval(Interval interval, long taskId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_INTERVAL_FK_TASK, taskId);
        values.put(COL_INTERVAL_STARTED, interval.getStarted());
        values.put(COL_INTERVAL_STOPPED, interval.getStopped());

        if (interval.getId() == 0) {
            db.insert(TABLE_NAME_INTERVALS, null, values);
        } else {
            String[] whereArgs = new String[]{String
                    .valueOf(interval.getId())};
            db.update(TABLE_NAME_INTERVALS, values, COL_INTERVAL_ID + " = ?",
                    whereArgs);
        }

        db.close();
    }
}
