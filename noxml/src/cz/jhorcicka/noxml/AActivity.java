package cz.jhorcicka.noxml;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("A");
        setContentView(textView);
    }
}
