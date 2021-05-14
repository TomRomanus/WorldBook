package com.tomward.worldbook.WorldView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tomward.worldbook.CountyInfo.CountryActivity;
import com.tomward.worldbook.MainActivity;
import com.tomward.worldbook.R;

public class EnterCountryName extends AppCompatActivity {
    private String UserName = "";

    private TextView txtCountryIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_country_name);

        txtCountryIn = (TextView) findViewById(R.id.txtCountryIn);

        Bundle extras = getIntent().getExtras();
        UserName = extras.getString("UserName");
    }

    public void onBtnNextEnterCountryName_Clicked(View caller) {
        String countryIn = txtCountryIn.getText().toString().toLowerCase().trim();
        String key = UserName + countryIn;

        Intent intent =new Intent(this, CountryActivity.class);
        intent.putExtra("CountryName", countryIn);
        intent.putExtra("Key", key);
        intent.putExtra("UserName", UserName);
        startActivity(intent);
    }

    public void onBtnBackEnterCountryName_Clicked(View caller) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}