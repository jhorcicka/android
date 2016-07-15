package cz.jhorcicka.environment;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class GlobalState extends Application {
	private int value = 0;
	
   @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
