package se.miun.erst0704.bathingsites;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Erik on 28/5 028.
 */
public class BathingSitesView extends LinearLayout {

    public BathingSitesView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public static void updateBathingSitesAmount(Activity activity) {
        try {
            int sitesAmount = DatabaseManager.getInstance(activity).getAmountOfSites();
            TextView text = (TextView) activity.findViewById(R.id.bathingText);
            String appName = activity.getResources().getString(R.string.app_name);
            text.setText(sitesAmount + " " + appName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public int getAmountOfBathingSites() {
        return DatabaseManager.getInstance(getContext()).getAmountOfSites();
    }
}
