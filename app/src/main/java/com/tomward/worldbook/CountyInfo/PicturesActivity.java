package com.tomward.worldbook.CountyInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PicturesActivity extends AppCompatActivity {

    private final Context context = this;

    private RecyclerView recyclerView;

    private String countryName ="";
    private String key = "";
    private String userName = "";

    private static final String TAG = "PicturesActivity";
    private static final String GETIMAGE_URL = "https://studev.groept.be/api/a20sd101/getImage/";

    private RequestQueue requestQueue;

    private ProgressDialog progressDialog;
    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("Key");
        userName = extras.getString("UserName");
        countryName = extras.getString("CountryName");

        uploads = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        getImagesFromDB();

    }

    public void onBtnBackPictures_Clicked(View caller) {
        saveData(CountryActivity.class);
    }

    public void onBtnAddImage_Clicked(View caller) {
        saveData(AddPictureActivity.class);
    }

    private void saveData(Class nextClass) {
        Intent intent =new Intent(this, nextClass);
        intent.putExtra("CountryName", countryName);
        intent.putExtra("Key", key);
        intent.putExtra("UserName", userName);
        startActivity(intent);
    }



    private void getImagesFromDB() {
        JsonArrayRequest getImageRequest = new JsonArrayRequest(Request.Method.GET, GETIMAGE_URL + key, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0; i<response.length();i++) {
                        JSONObject o = response.getJSONObject(i);
                        String url = o.getString("url").replace("µ","/");
                        Upload upload = new Upload(key, url);
                        uploads.add(upload);
                    }

                    //creating adapter
                    RecyclerView.Adapter adapter = new MyAdapter(context, uploads, key);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(PicturesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                progressDialog.dismiss();
                Log.d(TAG, e.getMessage());
                Toast.makeText(PicturesActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(getImageRequest);
    }
}