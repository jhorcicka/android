package cz.jhorcicka.androidframework.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Builder class for GUI elements.
 */
public class GUIBuilder {
    private final Activity _activity;
    private final LinearLayout.LayoutParams _parameters;
    private final LinearLayout _layout;
    private final Display _display;
    private final Point _screenSize = new Point();
    private final Map<Integer, View> _elements = new HashMap<>();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public GUIBuilder(Activity activity) {
        _activity = activity;
        _display = _activity.getWindowManager().getDefaultDisplay();
        _display.getSize(_screenSize);
        _parameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        _layout = new LinearLayout(_activity);
        _layout.setOrientation(LinearLayout.VERTICAL);
        _layout.setLayoutParams(_parameters);
    }

    public GUIBuilder space() {
        TextView textView = new TextView(_activity);
        textView.setText("");
        _layout.addView(textView, _parameters);
        return this;
    }

    public GUIBuilder text(String label) {
        Random random = new Random();
        return text(label, random.nextInt());
    }

    public GUIBuilder text(String label, int id) {
        TextView textView = new TextView(_activity);
        textView.setText(label);
        _elements.put(id, textView);
        _layout.addView(textView, _parameters);
        return space();
    }

    public GUIBuilder textInput(String label, int id) {
        TextView textView = new TextView(_activity);
        textView.setWidth(_screenSize.x / 3);
        textView.setText(label);

        EditText editText = new EditText(_activity);
        editText.setLayoutParams(_parameters);
        editText.setId(id);
        _elements.put(id, editText);

        LinearLayout linearLayout = new LinearLayout(_activity);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(_parameters);
        linearLayout.addView(textView);
        linearLayout.addView(editText);

        _layout.addView(linearLayout, _parameters);
        return space();
    }

    public GUIBuilder button(String label, View.OnClickListener listener) {
        Button button = new Button(_activity);
        button.setText(label);
        button.setOnClickListener(listener);
        _layout.addView(button, _parameters);
        return space();
    }

    public GUIBuilder custom(View view) {
        _layout.addView(view, _parameters);
        return space();
    }

    public View get(int id) {
        return _elements.get(id);
    }

    public View build() {
        return _layout;
    }
}
