package se.miun.erst0704.bathingsites;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Erik on 3/6 003.
 */
public class DownloadActivity extends AppCompatActivity {
    private String fileURLPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up action

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());

        // download listener
        browser.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                fileURLPath = url;
                new DownloadFile().execute();
            }
        });

        // load web page, based on url in preferences
        String downloadURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("download_url", null);
        browser.loadUrl(downloadURL);
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {
        private ProgressDialog pDialog;


        @Override
        protected String doInBackground(String... args) {
            InputStream is;
            DatabaseManager dbManager = DatabaseManager.getInstance(getBaseContext());
            try {
                URL url = new URL(fileURLPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);
                con.connect();

                // can we get size of file?
                int fileLength = con.getContentLength();

                is = con.getInputStream();

                File outputFile = new File(getCacheDir(), "tmp.csv");
                FileOutputStream fos = new FileOutputStream(outputFile);

                // download file
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0; // progressed length
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;

                    // update progress
                    if(fileLength > 0)
                        publishProgress((int) (total * 50 / fileLength));
//                        publishProgress((int) ((total * 100 / fileLength)*.5));

                    Thread.sleep(10);
                }

                fos.close();
                is.close();

                // find out total amount of lines in file
                LineNumberReader lineNr = new LineNumberReader(new FileReader(outputFile));
                lineNr.skip(Long.MAX_VALUE);
                int lineAmount = lineNr.getLineNumber();
                int curLine = (int) lineAmount / 2; // continue progress from 50%
                lineNr.close();

                // parse csv file
                is = new FileInputStream(outputFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;

                while((line = br.readLine()) != null) {
                    // split string based on qoutation marks
                    String[] details = line.split("\"");

                    // split coordinates
                    String[] coord = details[1].split(",");

                    // split into name and address
                    String[] tmp = details[3].split(",");
                    String name = tmp[0];

                    StringBuilder addressBuilder = new StringBuilder();
                    for(int i = 1; i < tmp.length; i++) {
                        if(i > 1 && i < tmp.length)
                            addressBuilder.append(", ");
                        addressBuilder.append(tmp[i].trim());
                    }

                    String address = addressBuilder.toString();


                    // add site to database
                    BathingSite site = new BathingSite(name, address, coord[0], coord[1]);
                    dbManager.addBathingSite(site);

                    // update progress
                    publishProgress((int) (++curLine * 100 / lineAmount));
                }

                br.close();



                outputFile.delete(); // delete file



            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate();
            pDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DownloadActivity.this);
            pDialog.setTitle("Downloading file");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            // text in dialog
            int pos = fileURLPath.lastIndexOf("/");
            String name = fileURLPath.substring(pos+1);
            pDialog.setMessage(name);
            pDialog.setProgressNumberFormat(null);

            pDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }
    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
