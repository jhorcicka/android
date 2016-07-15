package cz.jhorcicka.tasks;

import java.util.ArrayList;
import java.util.List;

import cz.jhorcicka.tasks.model.Task;
import cz.jhorcicka.tasks.utils.DateConverter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class OverviewActivity extends BaseActivity {
    private final String MENU_TASKS = "TASKS";
    private final String MENU_EXIT = "EXIT";

    private List<Task> tasks = new ArrayList<Task>();
    private TextView totalCount = null;
    private TextView totalTime = null;
    private TextView intervalList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_activity);

        handleParameters();
        totalCount = (TextView) findViewById(R.id.total_count);
        totalTime = (TextView) findViewById(R.id.total_time);
        intervalList = (TextView) findViewById(R.id.interval_list);
        intervalList.setMovementMethod(new ScrollingMovementMethod());
        fillContent();
    }

    private void handleParameters() {
        Bundle parameters = getIntent().getExtras();

        if (parameters != null && parameters.containsKey("id_list")) {
            long ids[] = parameters.getLongArray("id_list");
            tasks.clear();

            for (Long id : ids) {
                Task selectedTask = getDb().getTask(id);
                tasks.add(selectedTask);
            }
        }
    }

    private void fillContent() {
        int count = tasks.size();
        totalCount.setText("" + count);
        long time = 0;

        String intervalsString = "";

        for (Task task : tasks) {
            intervalsString += task.getInfo() + "\n";
            time += task.getLengthInMilis();
        }

        totalTime.setText(DateConverter.getLengthString(time));
        intervalList.setText(intervalsString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(MENU_TASKS);
        menu.add(MENU_EXIT);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        if (title.equals(MENU_TASKS)) {
            goToActivity(MainActivity.class);
        } else if (title.equals(MENU_EXIT)) {
            finish();
        }

        return true;
    }
}
