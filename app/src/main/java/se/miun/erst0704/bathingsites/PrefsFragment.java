package se.miun.erst0704.bathingsites;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Erik on 30/5 030.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences
        addPreferencesFromResource(R.xml.preferences);

        // set initial summary to weather preference
        EditTextPreference pref = (EditTextPreference) getPreferenceScreen().findPreference("weather_url");
        String tmp = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("weather_url", null);
        pref.setSummary(tmp);

        // set initial summary to download preference
        pref = (EditTextPreference) getPreferenceScreen().findPreference("download_url");
        tmp = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("download_url", null);
        pref.setSummary(tmp);
    }
}
