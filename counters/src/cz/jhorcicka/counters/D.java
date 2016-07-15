package cz.jhorcicka.counters;

import android.util.Log;

public class D {
	public static final String LOG_CAT_TAG = "MY_DEBUG";
	
	public static void a(String message) {
		l(message);
		d(message);
	}

	public static void l(String message) {
		Log.d(LOG_CAT_TAG, message);
	}
	public static void d(String message) {
		System.out.println("DEBUG: " + message);
	}
}
