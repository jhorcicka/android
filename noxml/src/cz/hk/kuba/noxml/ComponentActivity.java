package cz.hk.kuba.noxml;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComponentActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);


        final MyInput myInput = new MyInput(this);
        linearLayout.addView(myInput, params);
        Button button = new Button(this);
        button.setText("Save");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInput.getValue();
                myInput.setValue("EMPTY");
            }
        });
        linearLayout.addView(button, params);

        setContentView(linearLayout);
    }
}
