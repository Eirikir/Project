package se.miun.erst0704.bathingsites;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NewBathingSiteActivity extends AppCompatActivity {
    private EditText inputName, inputDescription, inputAddress,
            inputLongitude, inputLatitude, inputTemp, inputDate;
    private RatingBar grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bathing_site);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up action

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        inputName = (EditText) findViewById(R.id.inputName);
        inputDescription = (EditText) findViewById(R.id.inputDescription);
        inputAddress = (EditText) findViewById(R.id.inputAddress);
        inputLongitude = (EditText) findViewById(R.id.inputLongitude);
        inputLatitude = (EditText) findViewById(R.id.inputLatitude);
        inputTemp = (EditText) findViewById(R.id.inputTemp);
        inputDate = (EditText) findViewById(R.id.inputTempDate);
        grade = (RatingBar) findViewById(R.id.inputGrade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_bathing_site, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clearBtn) {
            clearAll();
            return true;
        }

        else if(id == R.id.saveBtn) {
            saveSite();
            return true;
        }

        else if(id == R.id.weather) {
            new GetWeather().execute();
            return true;
        }

        else if(id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // clear all inputs
    private void clearAll() {
        inputName.setText("");
        inputDescription.setText("");
        inputAddress.setText("");
        inputLongitude.setText("");
        inputLatitude.setText("");
        inputTemp.setText("");
        inputDate.setText("");
        grade.setRating(0);
    }

    // validate that required fields are set.
    private boolean validateInput() {
        String nameStr = inputName.getText().toString(),
                addressStr = inputAddress.getText().toString(),
                longStr = inputLongitude.getText().toString(),
                latStr = inputLatitude.getText().toString();

        boolean valid = true;

        if(TextUtils.isEmpty(nameStr)) {
            inputName.setError(getResources().getString(R.string.new_bath_name) + " is required!");
            valid = false;
        }
        if(TextUtils.isEmpty(addressStr) && (TextUtils.isEmpty(longStr) || TextUtils.isEmpty(latStr))) {
            inputAddress.setError("");
            inputLongitude.setError("");
            inputLatitude.setError("");
            valid = false;
        }

        return valid;
    }

    private void saveSite() {
        if(validateInput()) {
            String message = "";
            message += getResources().getString(R.string.new_bath_name) + " "
                    + inputName.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_description) + " "
                    + inputDescription.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_address) + " "
                    + inputAddress.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_long) + " "
                    + inputLongitude.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_lat) + " "
                    + inputLatitude.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_grade) + " "
                    + grade.getRating()
                    + "\n";
            message += getResources().getString(R.string.new_bath_temp) + " "
                    + inputTemp.getText()
                    + "\n";
            message += getResources().getString(R.string.new_bath_temp_date) + " "
                    + inputDate.getText()
                    + "\n";

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

    }


    private class GetWeather extends AsyncTask<String, Integer, String> {
        private ProgressDialog pDialog;
        private Drawable weatherPic;
        private String condition, temp;

        @Override
        protected String doInBackground(String... args) {
            InputStream is;
//            String urlPath = "http://dt031g.programvaruteknik.nu/badplatser/weather.php?location=Stockholm&language=SW";
//            String urlPath = "http://dt031g.programvaruteknik.nu/badplatser/weather.php";
            HttpURLConnection con = null;


            try {
//                PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
                SharedPreferences settings_pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String urlPath = settings_pref.getString("weather_url", null);

                URL url = new URL(urlPath + "?location=Sundsvall&language=SW");
                con = (HttpURLConnection) url.openConnection();

                is = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;



                for (int count = 1;(line = reader.readLine()) != null; count++) {

                    // get relevant info, lines; 4,5,6,7,8,9
                    if(count < 4)
                        continue;
                    int pos = line.indexOf(':');
                    line = line.replace("<br>", ""); // remove end
                    line = line.substring(pos+1);   // remove beginning
//                    System.out.println(line);
                    line = line.trim();

                    switch(count) {
                        case 4: condition = line; break;
                        case 5: temp = line; break;

                        case 9:
                            URL url2 = new URL(line);
                            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
                            InputStream ips = con2.getInputStream();
                            weatherPic = Drawable.createFromStream(ips, null);
                            ips.close();
                            con2.disconnect();
                            break;
                    }
                }

                reader.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                if(con != null)
                    con.disconnect();
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // init progress dialog
            pDialog = new ProgressDialog(NewBathingSiteActivity.this);

            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Getting current weather...");

            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pDialog.dismiss();  // dismiss progress dialog

            // build new dialog for weather information
            new AlertDialog.Builder(NewBathingSiteActivity.this)
                    .setTitle("Current Weather")
                    .setMessage(condition + "\n" + temp + "\u00b0")
                    .setIcon(weatherPic)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .show();


        }
    }
}
