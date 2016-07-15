package cz.hk.kuba.noxml;

import android.content.Context;
import android.widget.EditText;

import java.util.logging.Logger;

public class MyInput extends EditText {
    private static final Logger logger = Logger.getLogger(MyInput.class.getName());

    public MyInput(Context context) {
        super(context);
        setValue("HELLO");
    }

    public String getValue() {
        String value = getText().toString();
        logger.info("getValue: " + value);

        return value;
    }

    public void setValue(String value) {
        logger.info("setValue: " + value);
        setText(value);
    }
}
