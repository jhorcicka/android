package cz.jhorcicka.tasks;

import cz.jhorcicka.tasks.db.Db;
import android.app.Activity;
import android.content.Intent;

public abstract class BaseActivity extends Activity {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getDb().close();
    }
}
