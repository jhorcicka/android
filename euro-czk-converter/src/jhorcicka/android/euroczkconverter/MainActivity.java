package cz.jhorcicka.android.euroczkconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cz.kuba.androidframework.view.GUIBuilder;

import java.util.logging.Logger;

/**
 * Simple converter between CZK end Euro.
 */
public class MainActivity extends Activity {
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private static final int EURO_INPUT_ID = 1;
    private static final int CZK_INPUT_ID = 2;
    private static final int RESULT_TEXT_ID = 3;
    private static final double BUY_EURO_RATE = 29.00;
    private static final double SELL_EURO_RATE = 26.45;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GUIBuilder builder = new GUIBuilder(this);
        builder.space()
                .textInput("Euro", EURO_INPUT_ID)
                .textInput("CZK", CZK_INPUT_ID)
                .button("Clear", getOnClearListener(builder))
                .button("Convert", getOnConvertListener(builder))
                .text("", RESULT_TEXT_ID);
        setContentView(builder.build());
    }

    private View.OnClickListener getOnClearListener(final GUIBuilder builder) {
        View.OnClickListener onClear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText euro = (EditText) builder.get(EURO_INPUT_ID);
                euro.setText("");
                EditText czk = (EditText) builder.get(CZK_INPUT_ID);
                czk.setText("");
            }
        };

        return onClear;
    }

    private View.OnClickListener getOnConvertListener(final GUIBuilder builder) {
        View.OnClickListener onConvert = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText euroText = (EditText) builder.get(EURO_INPUT_ID);
                String euroValue = euroText.getText().toString();

                if (euroValue.isEmpty()) {
                    euroValue = "0.0";
                }

                Double euro = Double.parseDouble(euroValue);

                EditText czkText = (EditText) builder.get(CZK_INPUT_ID);
                String czkValue = czkText.getText().toString();

                if (czkValue.isEmpty()) {
                    czkValue = "0.0";
                }

                Double czk = Double.parseDouble(czkValue);
                double euroToCzk = euro * SELL_EURO_RATE;
                double czkToEuro = czk / BUY_EURO_RATE;

                String resultText = String.format("%.2f E\t\t\t=> %.2f CZK (%.2f)\n%.2f CZK\t=> %.2f E (%.2f)",
                        euro, euroToCzk, SELL_EURO_RATE, czk, czkToEuro, BUY_EURO_RATE);

                TextView resultView = (TextView) builder.get(RESULT_TEXT_ID);
                resultView.setText(resultText);
            }
        };

        return onConvert;
    }
}
