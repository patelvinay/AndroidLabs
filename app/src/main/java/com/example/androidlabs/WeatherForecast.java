package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView imageViewWeather;
    TextView textMinTemp;
    TextView textMaxTemp;
    TextView textTemp;
    TextView textUVRating;
    TextView textWindSpeed;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = (ProgressBar) findViewById(R.id.progressWeather);
        progressBar.setVisibility(View.VISIBLE);
        textMinTemp = (TextView) findViewById(R.id.textMinTemperature);
        textMaxTemp = (TextView) findViewById(R.id.textMaxTemperature);
        textTemp = (TextView) findViewById(R.id.textTemperature);
        textUVRating = (TextView) findViewById(R.id.textUVRating);
        imageViewWeather = (ImageView) findViewById(R.id.imageViewWeather);
        textWindSpeed = (TextView) findViewById(R.id.textWindSpeed);

        new ForecastQuery().execute();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String speed;
        String minTemp;
        String maxTemp;
        String currentTemp;
        Bitmap imageCurrentWeather;
        String weatherIcon;
        String uvRating;

        private final String imageURL = "http://openweathermap.org/img/w/";

        @Override
        protected void onPreExecute() {
            //Setup precondition to execute some task
        }

        @Override
        protected String doInBackground(String... params) {
            //Do some task
            String url1 = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

            try {
                URL url = new URL(url1);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                InputStream inputStream = conn.getInputStream();
                 /*BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                 String line="";
                 while(line!=null){
                     line=bufferedReader.readLine();
                     speed=speed+line;
                 }
                 temp=speed;*/

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(inputStream, "UTF8");//new StringReader ( "<foo>Hello World!</foo>" ) );
                //int eventType = xpp.getEventType();
                //textMinTemp.append(String.valueOf(Character.getNumericValue(eventType)));
                while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        String tag = xpp.getName();
                        if (tag.equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");
                            Log.d("AsyncTasK", "Temperature: " + currentTemp);
                            publishProgress(25);
                            Thread.sleep(200);
                            minTemp = xpp.getAttributeValue(null, "min");
                            Log.e("AsyncTask", "Minimum Temperature: " + minTemp);
                            publishProgress(50);
                            Thread.sleep(200);
                            maxTemp = xpp.getAttributeValue(null, "max");
                            Log.e("AsyncTask", "Maximum Temperature: " + maxTemp);
                            publishProgress(75);
                        }
                        else if(tag.equals("speed"))
                        {
                            speed=xpp.getAttributeValue(null,"value");
                            Log.e("AsyncTask","wind speed found");
                        }
                        else if (tag.equals("weather")) {
                            weatherIcon = xpp.getAttributeValue(null, "icon");
                            String fName = weatherIcon + ".png";
                            Log.e("AsyncTask", "Weather-Image: " + weatherIcon);


                            if (fileExistance(fName)) {
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(getBaseContext().getFileStreamPath(fName));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                imageCurrentWeather = BitmapFactory.decodeStream(fis);


                            } else {
                                Log.e("AsyncTask", "Downloading image");

                                imageCurrentWeather = HTTPUtils.getImage(imageURL + fName);
                                FileOutputStream outputStream = null;
                                try {
                                    outputStream = openFileOutput(fName, Context.MODE_PRIVATE);
                                    imageCurrentWeather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                } catch (Exception e) {
                                    Log.e("AsyncTask", "Error");
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    xpp.next();
                    //System.out.println("End document");
                }
                URL uvURL = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection UVConnection = (HttpURLConnection) uvURL.openConnection();
                inputStream = UVConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject jObject = new JSONObject(result);
                uvRating = String.valueOf(jObject.getDouble("value"));
                Log.e("AsyncTask", "UV Rating: " + uvRating);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return "abcd";
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Update the progress of current task
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Show the result obtained from doInBackground
            textMinTemp.setText(textMinTemp.getText().toString() + " = " + minTemp);
            textTemp.setText(textTemp.getText().toString()+" = "+currentTemp);
            textMaxTemp.setText(textMaxTemp.getText().toString()+" = "+maxTemp);
            textUVRating.setText(textUVRating.getText().toString()+" = "+uvRating);
            imageViewWeather.setImageBitmap(imageCurrentWeather);
            textWindSpeed.setText(textWindSpeed.getText().toString()+" = "+speed);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private static class HTTPUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public  static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}
