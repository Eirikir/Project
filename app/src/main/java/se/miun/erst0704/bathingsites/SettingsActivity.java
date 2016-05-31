package se.miun.erst0704.bathingsites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by Erik on 30/5 030.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new PrefsFragment()).commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        // update amount of stored bathing sites
        BathingSitesView sites = (BathingSitesView) findViewById(R.id.bathingView);
        int sitesAmount = DatabaseManager.getInstance(this).getAmountOfSites();
        sites.setAmountOfBathingSites(sitesAmount);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if(key.equals("weather_url")) {
            String tmp = prefs.getString(key, null);
            findPreference(key).setSummary(tmp);
        }
    }

}
