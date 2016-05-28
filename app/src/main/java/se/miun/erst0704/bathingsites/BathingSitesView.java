package se.miun.erst0704.bathingsites;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Erik on 28/5 028.
 */
public class BathingSitesView extends LinearLayout {
//    private ImageButton image;
//    private TextView text;
    private int amountOfBathingSites = 0;
    private String appName;

    public BathingSitesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // store appname
        appName = getResources().getString(R.string.app_name);
    }

    private void updateBathingSitesText() {
        TextView text = (TextView) findViewById(R.id.bathingText);
        text.setText(amountOfBathingSites + " " + appName);
    }

    public void setAmountOfBathingSites(int amount) {
        amountOfBathingSites = amount;
        updateBathingSitesText();
    }

    public int getAmountOfBathingSites() { return amountOfBathingSites; }
}
