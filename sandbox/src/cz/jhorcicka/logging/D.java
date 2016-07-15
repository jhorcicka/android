package cz.jhorcicka.logging;

import android.util.Log;

/**
 * Created by horcicka on 11. 09 . 2015.
 */
public class D {
    public static final String DEBUG_TAG = "DBG";
    public static final boolean DEBUG_ON = true;

    public static void d(Object... arguments) {
        if (! D.DEBUG_ON) {
            return;
        }

        for (Object parameter : arguments) {
            Log.d(D.DEBUG_TAG, parameter.toString());
        }
    }
}
