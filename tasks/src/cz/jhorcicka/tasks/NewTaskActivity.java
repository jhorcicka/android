package cz.jhorcicka.tasks;

import cz.jhorcicka.tasks.model.Interval;
import cz.jhorcicka.tasks.model.Task;
import cz.jhorcicka.tasks.utils.DateConverter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.CheckBox;

public class NewTaskActivity extends BaseActivity {
    private static final String MENU_SAVE = "Save";
    private static final String MENU_TASKS = "Tasks";
    private static final String MENU_EXIT = "Exit";

    private Task task = null;
    private EditText name = null;
    private CheckBox startImmediately = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        name = (EditText) findViewById(R.id.input_name);
        startImmediately = (CheckBox) findViewById(R.id.input_start_immediately);
    }

    private void fillTaskFromInput() {
        String nameString = name.getText().toString();
        boolean checked = startImmediately.isChecked();

        if (task == null) {
            task = new Task();
            task.setName(nameString);
            task.setCreated(DateConverter.getNow());
        }

        if (checked) {
            Interval interval = new Interval();
            interval.setStarted(DateConverter.getNow());
            task.getIntervals().add(interval);
        }
    }

    private void saveTask() {
        getDb().saveTask(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(MENU_SAVE);
        menu.add(MENU_TASKS);
        menu.add(MENU_EXIT);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        if (MENU_SAVE.equals(title)) {
            fillTaskFromInput();
            saveTask();
            goToActivity(MainActivity.class);
        } else if (title.equals(MENU_TASKS)) {
            goToActivity(MainActivity.class);
        } else if (title.equals(MENU_EXIT)) {
            finish();
        }

        return true;
    }
}
