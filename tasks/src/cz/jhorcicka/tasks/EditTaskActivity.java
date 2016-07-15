package cz.jhorcicka.tasks;

import cz.jhorcicka.tasks.model.Task;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;

public class EditTaskActivity extends BaseActivity {
    private static final String MENU_SAVE = "Save";
    private static final String MENU_TASKS = "Tasks";
    private static final String MENU_EXIT = "Exit";
    private static final String BUTTON_START = "START";
    private static final String BUTTON_STOP = "STOP";

    private Task task = null;
    private EditText name = null;
    private Button startStopButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        name = (EditText) findViewById(R.id.input_name);
        startStopButton = (Button) findViewById(R.id.start_stop_button);
        handleParameters();
        setStartStopButtonListener();
        setStartStopButtonLook();
    }

    private void setStartStopButtonListener() {
        startStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task.isRunning()) {
                    task.endInterval();
                } else {
                    task.startInterval();
                }

                saveTask();
                goToActivity(MainActivity.class);
            }
        });
    }

    private void setStartStopButtonLook() {
        if (task.isRunning()) {
            startStopButton.setBackgroundColor(Color.RED);
            startStopButton.setText(BUTTON_STOP);
        } else {
            startStopButton.setBackgroundColor(Color.GREEN);
            startStopButton.setText(BUTTON_START);
        }
    }

    private void handleParameters() {
        Bundle parameters = getIntent().getExtras();

        if (parameters != null && parameters.containsKey("id")) {
            long id = parameters.getLong("id");

            if (id > 0) {
                loadTask(id);
                fillInputFromTask();
            }
        }
    }

    private void loadTask(long id) {
        task = getDb().getTask(id);
    }

    private void fillInputFromTask() {
        name.setText(task.getName());
    }

    private void fillTaskFromInput() {
        if (task == null) {
            task = new Task();
        }

        String nameString = name.getText().toString();
        task.setName(nameString);
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
