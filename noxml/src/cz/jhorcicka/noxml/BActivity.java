package cz.jhorcicka.noxml;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("B");
        setContentView(textView);
    }
}
