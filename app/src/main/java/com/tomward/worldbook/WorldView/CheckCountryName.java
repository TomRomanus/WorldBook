package com.tomward.worldbook.WorldView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tomward.worldbook.PropertiesSingleton;
import com.tomward.worldbook.CountyInfo.CountryActivity;
import com.tomward.worldbook.R;

public class CheckCountryName extends AppCompatActivity {
    private final PropertiesSingleton propertiesSingleton = PropertiesSingleton.THE_INSTANCE;
    private final String userName = propertiesSingleton.getUserName();
    private final String countryName = propertiesSingleton.getCountryName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_country_name);

        TextView txtCountryIn = findViewById(R.id.txtCountryIn);
        txtCountryIn.setText(countryName);
    }

    public void onBtnNextCheckCountryName_Clicked(View caller) {
        startActivity(new Intent(this, CountryActivity.class));
    }

    public void onBtnBackCheckCountryName_Clicked(View caller) {
        Intent intent = new Intent(this, GlobeManager.class);
        intent.putExtra("UserName", userName);
        startActivity(intent);
    }
}