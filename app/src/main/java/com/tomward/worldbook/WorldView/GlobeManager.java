package com.tomward.worldbook.WorldView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.CountyInfo.CountryActivity;
import com.tomward.worldbook.CountyInfo.PicturesActivity;
import com.tomward.worldbook.R;
import com.tomward.worldbook.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GlobeManager extends AppCompatActivity {

    public static String countryName;
    public static String userName;
    private static Context mContext;

    public static ArrayList<String> countriesList = new ArrayList<>();
    private static final String GETCOUNTRIES_URL = "https://studev.groept.be/api/a20sd101/GetCountriesWithUserName/";
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("UserName");

        mContext = this;
        //load strings of countries the user has been to
        requestQueue = Volley.newRequestQueue(this);
        getCountriesWithUserName();
        //set content view to globe
        setContentView(R.layout.activity_globe);
//        try {
//            FileProcessor fp = new FileProcessor(getApplicationContext());
//            JSONArray geo = fp.parseFileToJSON("assets/geojson/countries.geojson");
//            Iterator<JSONObject> listIterator = geo.iterator();
//            HelloGeoJsonFragment fragment = new HelloGeoJsonFragment();
//            while (listIterator.hasNext()){
//                JSONObject country = listIterator.next();
//                /*JSONObject properties = (JSONObject) country.get("properties");
//                if(properties.get("ADMIN").equals("Belgium")){
//                    System.out.println(country);
//                }*/
//                fragment.drawVector(country);
//
//            }
//
//
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

    }
    public static void  setCountryName(String newCountryName)
    {
        countryName = newCountryName;
        System.out.println(countryName);
        startCountryView();
    }

    private static void startCountryView()
    {
        try {
            Intent intent = new Intent(mContext, CheckCountryName.class);
            intent.putExtra("CountryName", countryName);
            intent.putExtra("UserName", userName);
            mContext.startActivity(intent);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCountriesWithUserName() {
        String getCountriesURL = GETCOUNTRIES_URL + userName;

        JsonArrayRequest getCountriesRequest = new JsonArrayRequest(Request.Method.GET, getCountriesURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int i = 0;
                while (i < response.length()) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        countriesList.add(o.getString("CountryName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        });
        requestQueue.add(getCountriesRequest);
    }
}
