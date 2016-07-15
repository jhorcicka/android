package cz.jhorcicka.androidframework.utils;

import android.util.Log;

/**
 * Simple logger class.
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
