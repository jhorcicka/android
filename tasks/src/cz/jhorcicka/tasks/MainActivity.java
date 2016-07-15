package cz.jhorcicka.tasks;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.jhorcicka.tasks.model.Task;

public class MainActivity extends BaseListActivity {
	private static final String MENU_NEW_TASK = "New task";
	private static final String MENU_DELETE = "Delete";
	private static final String MENU_STATS = "Statistics";
	private static final String MENU_EXIT = "Exit";
	private List<Task> items = new ArrayList<>();

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		items = getDb().getAllTasks();
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg, View v,
					int position, long id) {
				Task selectedTask = (Task) getListView().getItemAtPosition(
						position);
				goToActivity(EditTaskActivity.class, selectedTask.getId());

				return true;
			}
		});
		getListView().setAdapter(
				new ArrayAdapter<Task>(this,
						android.R.layout.simple_list_item_multiple_choice,
						items));
	}

	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(MENU_NEW_TASK);
		menu.add(MENU_DELETE);
		menu.add(MENU_STATS);
		menu.add(MENU_EXIT);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		String title = item.getTitle().toString();

		if (MENU_NEW_TASK.equals(title)) {
			goToActivity(NewTaskActivity.class, 0);
		} else if (MENU_STATS.equals(title)) {
			long selectedIds[] = getSelectedIds();
			goToActivity(OverviewActivity.class, selectedIds);
		} else if (MENU_DELETE.equals(title)) {
			deleteSelectedCounters();
			goToActivity(MainActivity.class, 0);
		} else if (MENU_EXIT.equals(title)) {
			finish();
		}

		return true;
	}

	private long[] getSelectedIds() {
		SparseBooleanArray checkedItems = getListView()
				.getCheckedItemPositions();
		long ids[] = new long[checkedItems.size()];

		for (int i = 0; i < checkedItems.size(); i++) {
			int position = checkedItems.keyAt(i);
			Task task = (Task) getListView().getItemAtPosition(position);
			ids[i] = task.getId();
		}

		return ids;
	}

	private void deleteSelectedCounters() {
		SparseBooleanArray checkedItems = getListView()
				.getCheckedItemPositions();

		for (int i = 0; i < checkedItems.size(); i++) {
			int position = checkedItems.keyAt(i);
			Task task = (Task) getListView().getItemAtPosition(position);
			getDb().deleteTask(task);
		}
	}
}
