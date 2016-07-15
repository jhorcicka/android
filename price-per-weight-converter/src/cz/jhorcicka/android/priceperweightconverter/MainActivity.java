package cz.jhorcicka.android.priceperweightconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cz.kuba.androidframework.view.GUIBuilder;

/**
 * Simple calculator that takes a price for a given weight and returns a price for a standard weight(s).
 */
public class MainActivity extends Activity {
    private static final int PRICE_INPUT_ID = 1;
    private static final int WEIGHT_INPUT_ID = 2;
    private static final int RESULT_TEXT_ID = 3;
    private static final double SMALL_WEIGHT = 100.0;
    private static final double BIG_WEIGHT = 1000.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GUIBuilder builder = new GUIBuilder(this);
        builder.space()
                .textInput("Price", PRICE_INPUT_ID)
                .textInput("Weight (g)", WEIGHT_INPUT_ID)
                .button("Clear", getOnClearListener(builder))
                .button("Calculate", getOnCalculateListener(builder))
                .text("", RESULT_TEXT_ID);
        setContentView(builder.build());
    }

    private View.OnClickListener getOnClearListener(final GUIBuilder builder) {
        View.OnClickListener onClear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText price = (EditText) builder.get(PRICE_INPUT_ID);
                price.setText("");
                EditText weight = (EditText) builder.get(WEIGHT_INPUT_ID);
                weight.setText("");
            }
        };

        return onClear;
    }

    private View.OnClickListener getOnCalculateListener(final GUIBuilder builder) {
        View.OnClickListener onConvert = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText priceText = (EditText) builder.get(PRICE_INPUT_ID);
                String priceValue = priceText.getText().toString();

                if (priceValue.isEmpty()) {
                    priceValue = "0.0";
                }

                Double price = Double.parseDouble(priceValue);
                EditText weightText = (EditText) builder.get(WEIGHT_INPUT_ID);
                String weightValue = weightText.getText().toString();

                if (weightValue.isEmpty()) {
                    weightValue = "0.0";
                }

                Double weight = Double.parseDouble(weightValue);

                double pricePerGram = price / weight;
                double pricePerSmallWeight = pricePerGram * SMALL_WEIGHT;
                double pricePerBigWeight = pricePerGram * BIG_WEIGHT;

                String resultText = String.format("%.2f g\t=> %.2f E\n%.2f g\t=> %.2f E",
                        SMALL_WEIGHT, pricePerSmallWeight, BIG_WEIGHT, pricePerBigWeight);

                TextView resultView = (TextView) builder.get(RESULT_TEXT_ID);
                resultView.setText(resultText);
            }
        };

        return onConvert;
    }
}
