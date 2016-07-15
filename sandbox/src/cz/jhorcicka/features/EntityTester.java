package cz.jhorcicka.features;

import android.widget.TextView;
import cz.jhorcicka.entity.Category;
import cz.orm.activeandroid.R;

/**
 * Created by horcicka on 29. 09 . 2015.
 */
public class EntityTester {
    public EntityTester() {
    }

    public String getResult() {
        //GlobalState state = (GlobalState) this.getActivity().getApplicationContext();
        //Category c = Category.load(Category.class, 2L);
        Category c = new Category();
        c.save();

        return c.name;
    }
}
