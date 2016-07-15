package cz.jhorcicka.noxml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ControllerActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonA = new Button(this);
        buttonA.setText("Go to A");
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(Page.A);
            }
        });
        Button buttonB = new Button(this);
        buttonB.setText("Go to B");
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(Page.B);
            }
        });

        LinearLayout layout = new LinearLayout(this);
        layout.addView(buttonA);
        layout.addView(buttonB);
        setContentView(layout);
    }

    private void goTo(Page page) {
        Intent intent = new Intent(this, page.getActivityClass());
        //finish();
        startActivity(intent);
    }
}
