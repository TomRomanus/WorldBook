package com.tomward.worldbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tomward.worldbook.WorldView.GlobeManager;

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

        txtUserName = findViewById(R.id.txtUserNameRegister);
        txtPassword1 = findViewById(R.id.txtPassword1);
        txtPassword2 = findViewById(R.id.txtPassword2);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void onBtnRegister_Clicked(View caller) {
        String userName = txtUserName.getText().toString().toLowerCase().trim();
        String password1 = txtPassword1.getText().toString().toLowerCase().trim();
        String password2 = txtPassword2.getText().toString().toLowerCase().trim();
        int password = password1.hashCode();

        if(userName.equals("") || password1.equals("") || password2.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter username and passwords", Toast.LENGTH_LONG).show();
        }
        else {
            String registerURL = ADDUSER_URL + userName + "/" + password;
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerURL, response -> goToNextActivity(userName), e -> {
                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d(TAG, e.getMessage());
            });

            String getUserURL = GETUSER_URL + userName;
            JsonArrayRequest getUserRequest = new JsonArrayRequest(Request.Method.GET, getUserURL, null, response -> {
                if(response.length() != 0) {
                    Toast.makeText(RegisterActivity.this, "Username taken", Toast.LENGTH_LONG).show();
                }
                else {
                    if (password1.equals(password2)) {
                        requestQueue.add(registerRequest);
                    } else {
                        Toast.makeText(RegisterActivity.this, "password inconsistent", Toast.LENGTH_LONG).show();
                    }
                }
            }, e -> {
                Toast.makeText(RegisterActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                Log.d(TAG, e.getMessage());
            });
            requestQueue.add(getUserRequest);
        }
    }

    private void goToNextActivity(String UserName){
        Intent intent =new Intent(this, GlobeManager.class);
        intent.putExtra("UserName", UserName);
        startActivity(intent);
    }

    public void onBtnBackRegister_Clicked(View caller) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}