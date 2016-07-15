package cz.jhorcicka.tasks;

import cz.jhorcicka.tasks.db.Db;
import android.app.ListActivity;
import android.content.Intent;

public abstract class BaseListActivity extends ListActivity {
    private Db db = null;

    protected Db getDb() {
        if (db == null) {
            db = new Db(this);
        }

        return db;
    }

    protected void goToActivity(Class targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        finish();
        startActivity(intent);
    }

    protected void goToActivity(Class targetActivity, long id) {
        Intent intent = new Intent(this, targetActivity);

        if (id > 0) {
            intent.putExtra("id", id);
        }

        finish();
        startActivity(intent);
    }

    protected void goToActivity(Class targetActivity, long ids[]) {
        Intent intent = new Intent(this, targetActivity);

        if (ids.length > 0) {
            intent.putExtra("id_list", ids);
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
