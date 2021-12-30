package com.example.webdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String weather;
    String query;
    EditText editTextTextPersonName;
    TextView textView;
    ImageView imageView;
    String weatherdesc;
    public void google (View view){
        Intent intent = new Intent(getApplicationContext(),secondActivity.class);
        startActivity(intent);

    }
    public void clicked(View view){
        InputMethodManager manage=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manage.hideSoftInputFromWindow(editTextTextPersonName.getWindowToken(),0);

        html weburl = new html();

        weburl.execute("http://api.weatherstack.com/current?access_key=ebfb493d2e3e40e6e72caadec111d9d4&query="+editTextTextPersonName.getText().toString());
        Log.i("clicked ", editTextTextPersonName.getText().toString());
        textView.setBackground(getDrawable(R.drawable.bg));


    }

    public class html extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try { StringBuilder result= new StringBuilder();
                HttpURLConnection webConnection = null;
                URL web = new URL(strings[0]);
                webConnection = (HttpURLConnection) web.openConnection();
                InputStream in = webConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    data = reader.read();
                    result.append(current);


                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                textView.setText("ERROR!Please check your internet connection");

            }


            return "fucked!!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("dakj",s);
            try {
                JSONObject jsonObject =new JSONObject(s);
                String info = jsonObject.getString("current");
                Log.i("dakj",info);
               /* textView.setText(info);*/
                JSONObject fuck = jsonObject.getJSONObject("current");
                   JSONArray weathericon =fuck.getJSONArray("weather_icons");
                   JSONArray weatherdes= fuck.getJSONArray("weather_descriptions");
                for(int i=0;i<weatherdes.length();i++) {
                    weatherdesc = weatherdes.getString(i);

                }

                   for(int i=0;i<weathericon.length();i++){
                        weather = weathericon.getString(i);
                       imageDownload task= new imageDownload();
                       Bitmap icon;
                       try {
                           icon=task.execute(weather).get();
                           imageView.setImageBitmap(icon);

                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                       Log.i("dbkuwe",weather);
                   }



                   String fucks = fuck.getString("temperature");
                   String windspeed=fuck.getString("wind_speed");
                   String winddir=fuck.getString("wind_dir");
                   String humidity=fuck.getString("humidity");

                   Log.i("temperature",fucks );
                textView.setText(" "+"\r\n"+weatherdesc.toUpperCase()+"\r\n"+"temperature : "+fucks+" C"+"\r\n"+"windspeed : "+windspeed+"km/hr "+winddir+"\r\n"+"humidity : "+humidity+" %");






            } catch (Exception e) {
                e.printStackTrace();
                textView.setText("could not find weather:(");

            }
           /* Log.i("jh",s);*/
        }


    }

    public class imageDownload extends AsyncTask<String ,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            HttpURLConnection webConnectionimage = null;
            URL image = null;
            try {
                image = new URL(urls[0]);
                webConnectionimage = (HttpURLConnection) image.openConnection();
                webConnectionimage.connect();
                InputStream in= webConnectionimage.getInputStream();
                Bitmap myImage = BitmapFactory.decodeStream(in);
                return myImage;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
        imageView=(ImageView) findViewById(R.id.imageView);
        editTextTextPersonName=(EditText) findViewById(R.id.editTextTextPersonName);






    }



}