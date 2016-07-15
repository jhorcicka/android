package cz.hk.kuba.noxml;

/**
 * Created by horcicka on 29. 10 . 2015.
 */
public enum Page {
    A(AActivity.class),
    B(BActivity.class),
    ;

    private Class activityClass;

    Page(Class activityClass) {
        this.activityClass = activityClass;
    }

    public Class getActivityClass() {
        return activityClass;
    }
}
