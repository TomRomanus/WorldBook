package com.tomward.worldbook.WorldView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tomward.worldbook.R;


public class GlobeManager extends AppCompatActivity {

    public static String countryName;
    public static String userName;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("UserName");
        mContext = this;
        setContentView(R.layout.activity_globe);
    }
    public static void  setCountryName(String newCountryName)
    {
        countryName = newCountryName;
        System.out.println(countryName);
        startCountryView();
    }

    private static void startCountryView()
    {
        try {
            Intent intent = new Intent(mContext, CheckCountryName.class);
            intent.putExtra("CountryName", countryName);
            intent.putExtra("UserName", userName);
            mContext.startActivity(intent);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
