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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.WorldView.EnterCountryName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private TextView txtUserName;
    private TextView txtPassword1;
    private TextView txtPassword2;

    private static final String ADDUSER_URL = "https://studev.groept.be/api/a20sd101/AddUser/";
    private static final String GETUSER_URL = "https://studev.groept.be/api/a20sd101/getUser/";
    private static final String TAG = "RegisterActivity";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUserName = (TextView) findViewById(R.id.txtUserNameRegister);
        txtPassword1 = (TextView) findViewById(R.id.txtPassword1);
        txtPassword2 = (TextView) findViewById(R.id.txtPassword2);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void onBtnRegister_Clicked(View caller) {
        String UserName = txtUserName.getText().toString().toLowerCase().trim();
        String Password1 = txtPassword1.getText().toString().toLowerCase().trim();
        String Password2 = txtPassword2.getText().toString().toLowerCase().trim();

        if(UserName.equals("") || Password1.equals("") || Password2.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter username and passwords", Toast.LENGTH_LONG).show();
        }
        else {
            String registerURL = ADDUSER_URL + UserName + "/" + Password1;
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    goToNextActivity(UserName);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, e.getMessage());
                }
            });

            String getUserURL = GETUSER_URL + UserName;
            JsonArrayRequest getUserRequest = new JsonArrayRequest(Request.Method.GET, getUserURL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        //Als Username nog niet bestaat wordt een kolom van 0 rijen teruggegeven --> index 0 zorgt voor indexOutOfBoundsException
                        JSONObject o = response.getJSONObject(0);
                        Toast.makeText(RegisterActivity.this, "Username taken", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.d(TAG, e.getMessage());
                        if (Password1.equals(Password2)) {
                            requestQueue.add(registerRequest);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Password inconsistent", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    Toast.makeText(RegisterActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                    Log.d(TAG, e.getMessage());
                }
            });
            requestQueue.add(getUserRequest);
        }
    }

    private void goToNextActivity(String UserName){
        Intent intent =new Intent(this, EnterCountryName.class);
        intent.putExtra("UserName", UserName);
        startActivity(intent);
    }

    public void onBtnBackRegister_Clicked(View caller) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}