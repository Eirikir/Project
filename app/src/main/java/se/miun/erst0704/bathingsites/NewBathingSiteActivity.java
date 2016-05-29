package se.miun.erst0704.bathingsites;

import android.os.Bundle;
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
}
