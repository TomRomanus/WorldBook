package com.tomward.worldbook.CountyInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.PropertiesSingleton;
import com.tomward.worldbook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PicturesActivity extends AppCompatActivity {

    private final Context context = this;

    private RecyclerView recyclerView;

    private final PropertiesSingleton propertiesSingleton = PropertiesSingleton.THE_INSTANCE;
    private final String key = propertiesSingleton.getKey();

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
        startActivity(new Intent(this, nextClass));
    }



    private void getImagesFromDB() {
        JsonArrayRequest getImageRequest = new JsonArrayRequest(Request.Method.GET, GETIMAGE_URL + key, null, response -> {
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
        }, e -> {
            progressDialog.dismiss();
            Log.d(TAG, e.getMessage());
            Toast.makeText(PicturesActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
        });
        requestQueue.add(getImageRequest);
    }
}