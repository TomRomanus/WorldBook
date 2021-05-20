package com.tomward.worldbook;

import androidx.appcompat.app.AppCompatActivity;

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
import com.tomward.worldbook.WorldView.GlobeManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView txtPassword;

    private static final String GETUSER_URL = "https://studev.groept.be/api/a20sd101/getUser/";
    private static final String TAG = "MainActivity";

    private RequestQueue requestQueue;

    private int passwordDB = -1;
    private String userName = "";
    private int password = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void onBtnLogin_Clicked(View caller) {
        userName = txtUserName.getText().toString().toLowerCase().trim();
        password = txtPassword.getText().toString().toLowerCase().trim().hashCode();

        String getUserURL = GETUSER_URL + userName;
        JsonArrayRequest getUserRequest = new JsonArrayRequest(Request.Method.GET,getUserURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject o = response.getJSONObject(0);
                    passwordDB = o.getInt("Password");
                    checkPasswords();
                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Username not found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(MainActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                Log.d(TAG, e.getMessage());
            }
        });
        requestQueue.add(getUserRequest);
    }

    private void checkPasswords() {
        if(password == passwordDB && password != -1) {
            try {
                Intent intent = new Intent(this, GlobeManager.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Wrong password, please try again", Toast.LENGTH_LONG).show();
        }
    }

    public void onBtnToRegister_Clicked(View caller) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}