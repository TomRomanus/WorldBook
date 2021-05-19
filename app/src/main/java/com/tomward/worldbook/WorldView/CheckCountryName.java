package com.tomward.worldbook.WorldView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tomward.worldbook.CountyInfo.CountryActivity;
import com.tomward.worldbook.MainActivity;
import com.tomward.worldbook.R;

public class CheckCountryName extends AppCompatActivity {
    private String UserName = "";
    private String countryName = "";

    private TextView txtCountryIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_country_name);

        txtCountryIn = (TextView) findViewById(R.id.txtCountryIn);

        Bundle extras = getIntent().getExtras();
        UserName = extras.getString("UserName");
        countryName = extras.getString("CountryName");
        txtCountryIn.setText(countryName);
    }

    public void onBtnNextCheckCountryName_Clicked(View caller) {
        String countryIn = txtCountryIn.getText().toString().toLowerCase().trim();
        String key = UserName + countryIn;

        Intent intent =new Intent(this, CountryActivity.class);
        intent.putExtra("CountryName", countryIn);
        intent.putExtra("Key", key);
        intent.putExtra("UserName", UserName);
        startActivity(intent);
    }

    public void onBtnBackCheckCountryName_Clicked(View caller) {
        Intent intent = new Intent(this, GlobeManager.class);
        startActivity(intent);
    }
}