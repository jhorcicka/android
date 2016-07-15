package cz.jhorcicka.logging;

import android.util.Log;

/**
 * Created by horcicka on 25. 09 . 2015.
 */
public class Logger {
    private Class sourceClass;

    public Logger(Class sourceClass) {
        this.sourceClass = sourceClass;
    }

    public void log(Object... arguments) {
        for (Object parameter : arguments) {
            Log.d(sourceClass.getName(), parameter.toString());
        }
    }
}
