package com.tomward.worldbook.CountyInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.R;
import com.tomward.worldbook.WorldView.GlobeManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryActivity extends AppCompatActivity {

    private TextView txtCountryName;
    private RatingBar ratingBar;
    private TextView txtInfo;
    private TextView txtMonth;
    private TextView txtYear;

    private String key = "";
    private String userName = "";
    private String countryName = "";
    private float rating = 0.0f;

    private static final String SAVE_URL = "https://studev.groept.be/api/a20sd101/saveToWorldBook/";
    private static final String GET_URL = "https://studev.groept.be/api/a20sd101/getFromWorldBook/";
    private static final String UPDATE_URL = "https://studev.groept.be/api/a20sd101/updateInWorldBook/";
    private static final String TAG = "CountryActivity";

    private RequestQueue requestQueue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        txtCountryName = findViewById(R.id.txtCountryName);
        ratingBar = findViewById(R.id.ratingBar);
        txtInfo = findViewById(R.id.txtInfo);
        txtInfo.setSingleLine(false);
        txtMonth = findViewById(R.id.txtMonth);
        txtYear = findViewById(R.id.txtYear);

        context = this;

        Bundle extras = getIntent().getExtras();
        key = extras.getString("Key");
        userName = extras.getString("UserName");
        countryName = extras.getString("CountryName");
        txtCountryName.setText(countryName);

        requestQueue = Volley.newRequestQueue(this);

        String getURL = GET_URL + key;
        JsonArrayRequest setRequest = new JsonArrayRequest(Request.Method.GET,getURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject o = response.getJSONObject(0);
                    txtMonth.setText(o.getString("Month"));
                    txtYear.setText(o.getString("Year"));
                    rating = Float.parseFloat(o.getString("Rating"));
                    ratingBar.setRating(rating);
                    String info = o.getString("Info").replace("µµn","\n");
                    if(info.equals("null"))
                        txtInfo.setText("");
                    else txtInfo.setText(info);

                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountryActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(setRequest);
    }

    public void onBtnBackCountry_Clicked(View caller) {
        saveToDB('B');
    }

    public void onBtnPictures_Clicked(View caller) {
        saveToDB('P');
    }

    public void onBtnSave_Clicked(View caller) {
        saveToDB('S');
    }

    private void saveToDB(char e) {
        rating = ratingBar.getRating();
        String infoToSave = txtInfo.getText().toString().replace("\n","µµn");

        String saveURL = SAVE_URL + key + "/" +
                userName + "/" +
                countryName + "/" +
                txtMonth.getText() + "/" +
                txtYear.getText() + "/" +
                rating + "/" +
                infoToSave;

        StringRequest saveRequest = new StringRequest(Request.Method.GET, saveURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (e) {
                    case 'B':
                        Intent intentB = new Intent(context, GlobeManager.class);
                        intentB.putExtra("UserName", userName);
                        startActivity(intentB);
                        break;
                    case 'P':
                        Intent intentP = new Intent(context, PicturesActivity.class);
                        intentP.putExtra("CountryName", countryName);
                        intentP.putExtra("Key", key);
                        intentP.putExtra("UserName", userName);
                        startActivity(intentP);
                        break;
                    case 'S':
                        Toast.makeText(CountryActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(CountryActivity.this, "Unable to save, please try again", Toast.LENGTH_LONG).show();
                Log.d(TAG, e.getMessage());
            }
        });

        String updateURL = UPDATE_URL +
                txtMonth.getText() + "/" +
                txtYear.getText() + "/" +
                rating + "/" +
                infoToSave + "/" +
                key;

        StringRequest updateRequest = new StringRequest(Request.Method.GET, updateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue.add(saveRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(CountryActivity.this, "Unable to save, please try again", Toast.LENGTH_LONG).show();
                Log.d(TAG, e.getMessage());
            }
        });
        requestQueue.add(updateRequest);
    }
}