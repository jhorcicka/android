package cz.jhorcicka.activity;

import com.activeandroid.ActiveAndroid;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cz.jhorcicka.features.EntityTester;
import cz.jhorcicka.features.PhotoTester;
import cz.jhorcicka.features.SoundTester;
import cz.jhorcicka.logging.Logger;
import cz.orm.activeandroid.R;

public class Main extends Activity {
    private static final Logger logger = new Logger(Main.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
				.commit();
		}
	}
	
	public void onCreate() {
		ActiveAndroid.initialize(this);
	}
	
	public void onTerminate() {
		ActiveAndroid.dispose();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			//testEntity(rootView);
			//testPhoto();
			testSound();

			return rootView;
		}

		private void testSound() {
			SoundTester soundTester = new SoundTester(getActivity());
			soundTester.onRecord(true);
		}

		private void testEntity(View rootView) {
			EntityTester entityTester = new EntityTester();
			String result = entityTester.getResult();

			TextView text = (TextView) rootView.findViewById(R.id.text);
			text.setText(result);
		}

		private void testPhoto() {
			PhotoTester photoTester = new PhotoTester(getActivity());
			photoTester.dispatchTakePictureIntent();
		}
	}
}
