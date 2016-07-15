package cz.jhorcicka.counters;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.jhorcicka.counters.db.Db;
import cz.jhorcicka.counters.model.Counter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
	private static final String MENU_NEW_COUNTER = "New counter";
	private static final String MENU_DELETE = "Delete";
	private static final String MENU_CLEAR = "Clear";
	private static final String MENU_EXIT = "Exit";
	
	private List<Counter> items = new ArrayList<Counter>();
	private Db db = null;
	
	private Db getDb() {
		if (db == null) {
			db = new Db(this);
		}
		
		return db;
	}

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		items = getDb().getAllCounters();
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg, View v, int position, long id) {
				Counter selectedCounter = (Counter) getListView().getItemAtPosition(position);
				goToActivity(EditActivity.class, selectedCounter.getId());
				
				return true;
			}
		});
		getListView().setAdapter(new ArrayAdapter<Counter>(
			this, android.R.layout.simple_list_item_multiple_choice, items));
	}
	
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(MENU_NEW_COUNTER);
		menu.add(MENU_DELETE);
		menu.add(MENU_CLEAR);
		menu.add(MENU_EXIT);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		String title = item.getTitle().toString();
		
		if (title.equals(MENU_NEW_COUNTER)) {
			goToActivity(EditActivity.class, 0);
		}
		else if (title.equals(MENU_DELETE)) {
			deleteSelectedCounters();
			goToActivity(MainActivity.class, 0);
		}
        else if (title.equals(MENU_CLEAR)) {
			clearSelectedCounters();
			goToActivity(MainActivity.class, 0);
		}
		else if (title.equals(MENU_EXIT)) {
			finish();
		}
		
		return true;
	}
	
	private void deleteSelectedCounters() {
		SparseBooleanArray checkedItems = getListView().getCheckedItemPositions();
			
		for (int i = 0; i < checkedItems.size(); i++) {
			int position = checkedItems.keyAt(i);
			Counter counter = (Counter) getListView().getItemAtPosition(position);
			getDb().deleteCounter(counter);
		}
	}

	private void clearSelectedCounters() {
		SparseBooleanArray checkedItems = getListView().getCheckedItemPositions();

		for (int i = 0; i < checkedItems.size(); i++) {
			int position = checkedItems.keyAt(i);
			Counter counter = (Counter) getListView().getItemAtPosition(position);
			counter.setValue(0L);
			getDb().updateCounter(counter);
		}
	}

	private void goToActivity(Class targetActivity, long id) {
		Intent intent = new Intent(this, targetActivity);
		
		if (id > 0) {
			intent.putExtra("id", id);
		}
		
		finish();
		startActivity(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getDb().close();
	}
}
