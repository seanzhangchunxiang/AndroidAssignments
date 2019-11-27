package wilfridlaurier.chunxiang.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    static final String ACTIVITY_NAME = "WeatherForecast";
    static final String citylist[] = {"Ottawa", "Ajax", "Calgary","Charlottetown", "Quebec", "Halifax", "Iqaluit", "Montreal", "North Bay", "Oakville", "Regina", "Saint John", "Toronto", "Vancouver"  };
    private String cityName;

    ImageView imageView;
    ProgressBar progressBar;
    TextView currTemp;
    TextView minTemperature;
    TextView maxTemperature;
    Spinner citySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currTemp = findViewById(R.id.currTemp);
        minTemperature = findViewById(R.id.minTemp);
        maxTemperature = findViewById(R.id.maxTemp);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        imageView =findViewById(R.id.imageView3);
        citySpinner = findViewById(R.id.citySpinner);
        cityName = "ottawa"; //default setting

        citySpinner.setPrompt("Please select a city");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, citylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                //code
                cityName = citySpinner.getSelectedItem().toString();
                ForecastQuery f = new ForecastQuery();
                f.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView){
                //do nothing
            }
        });

        ForecastQuery f = new ForecastQuery();
        f.execute();
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        //private String windSpeed;
        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private Bitmap picture;


        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + cityName +",ca&APPID=9588e6039aa392133c9a6ea05de74ced&mode=xml&units=metric");

                Log.i("doInBackground", url.toString());

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream in = conn.getInputStream();

                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    //While you're not at the end of the document:
                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                    {
                        //Are you currently at a Start Tag?
                        if(parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            if(parser.getName().equals("temperature") )
                            {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            }
                            else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + "@2x.png";

                                Log.i(ACTIVITY_NAME,"Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    }
                                    catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME,"Found the file locally");
                                    picture = BitmapFactory.decodeStream(fis);
                                }
                                else {
                                    String iconUrl = "https://openweathermap.org/img/wn/" + fileName;
                                    //For example:  https://openweathermap.org/img/wn/10n@2x.png
                                    Log.i(ACTIVITY_NAME,"Start downloading from the Internet:" + iconUrl);

                                    picture = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = openFileOutput( fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME,"Downloaded the file from the Internet:" + iconUrl);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                            /*else if (parser.getName().equals("wind")) {
                                parser.nextTag();
                                if(parser.getName().equals("speed") )
                                {
                                    windSpeed = parser.getAttributeValue(null, "value");
                                }
                            }*/
                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return "";

        }

        boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {

                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    //Log.i(ACTIVITY_NAME,"enter getImage(URL url), return response 200");
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else {
                    //Log.i(ACTIVITY_NAME,"enter getImage(URL url), return null");
                    return null;
                }
            } catch (Exception e) {
                //Log.i(ACTIVITY_NAME,"Exception during downloading" + e);
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
/*        public Bitmap getImage(String urlString) {
            try {
                Log.i(ACTIVITY_NAME,"enter getImage(String urlString)");
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }*/

        @Override
        protected void onPostExecute(String a) {
            Resources myResources = getResources();

            imageView.setImageBitmap(picture);
            currTemp.setText(myResources.getText(R.string.curr_temp) + currentTemp + "C\u00b0");
            minTemperature.setText(myResources.getText(R.string.min_temp) + minTemp + "C\u00b0");
            maxTemperature.setText(myResources.getText(R.string.max_temp)+ maxTemp + "C\u00b0");
            progressBar.setVisibility(View.INVISIBLE);
            //wind_speed.setText(windSpeed + "km/h");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

    }
}
