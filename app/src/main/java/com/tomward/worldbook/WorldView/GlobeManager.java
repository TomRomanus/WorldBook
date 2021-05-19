package com.tomward.worldbook.WorldView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mousebird.maply.BaseController;
import com.tomward.worldbook.MainActivity;
import com.tomward.worldbook.R;
import com.tomward.worldbook.WhirlyGlobe.FileProcessor;
import com.tomward.worldbook.WhirlyGlobe.HelloGeoJsonFragment;
import com.tomward.worldbook.WhirlyGlobe.HelloGlobeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class GlobeManager extends AppCompatActivity {

    public static String countryName;
    public static String userName;
    private static ArrayList<String> countryList = new ArrayList<>();
    private static final String GETCOUNTRIES_URL = "https://studev.groept.be/api/a20sd101/GetCountriesWithUserName/";
    private static final String TAG = "GlobeManager";

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("UserName");
        setContentView(R.layout.activity_globe);

        requestQueue = Volley.newRequestQueue(this);
        getCountriesWithUserName();

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
public static void setCountryName(String newCountryName)
{
    countryName = newCountryName;
    System.out.println(countryName);
}
private void startCountryView()
{

}

private ArrayList<String> getCountriesWithUserName() {
        ArrayList<String> list = new ArrayList<>();

        String getCountriesURL = GETCOUNTRIES_URL + userName;
    JsonArrayRequest getCountriesRequest = new JsonArrayRequest(Request.Method.GET,getCountriesURL,null,new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                int i = 0;
                while(i<response.length()) {
                    JSONObject o = response.getJSONObject(i);
                    countryList.add(o.getString("CountryName"));
                    i++;
                }
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError e) {
            Log.d(TAG, e.getMessage());
        }
    });
    requestQueue.add(getCountriesRequest);
}
}
