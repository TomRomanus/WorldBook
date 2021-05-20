package com.tomward.worldbook.WorldView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tomward.worldbook.CountyInfo.CountryActivity;
import com.tomward.worldbook.R;

public class CheckCountryName extends AppCompatActivity {
    private String userName = "";
    private String countryName = "";

    private TextView txtCountryIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_country_name);

        txtCountryIn = findViewById(R.id.txtCountryIn);

        Bundle extras = getIntent().getExtras();
        userName = extras.getString("UserName");
        countryName = extras.getString("CountryName");
        txtCountryIn.setText(countryName);
    }

    public void onBtnNextCheckCountryName_Clicked(View caller) {
        String key = userName + countryName;

        Intent intent =new Intent(this, CountryActivity.class);
        intent.putExtra("CountryName", countryName);
        intent.putExtra("Key", key);
        intent.putExtra("UserName", userName);
        startActivity(intent);
    }

    public void onBtnBackCheckCountryName_Clicked(View caller) {
        Intent intent = new Intent(this, GlobeManager.class);
        startActivity(intent);
    }
}