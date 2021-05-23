package com.tomward.worldbook.WorldView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tomward.worldbook.PropertiesSingleton;
import com.tomward.worldbook.R;


public class GlobeManager extends AppCompatActivity {

    public static String countryName;
    public static String userName;
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getExtras().getString("UserName");
        mContext = this;
        setContentView(R.layout.activity_globe);
    }
    public static void  setCountryName(String newCountryName)
    {
        countryName = newCountryName;
        startCountryView();
    }

    private static void startCountryView()
    {
        try {
            Intent intent = new Intent(mContext, CheckCountryName.class);
            mContext.startActivity(intent);

            PropertiesSingleton propertiesSingleton = PropertiesSingleton.THE_INSTANCE;
            propertiesSingleton.setUserName(userName);
            propertiesSingleton.setCountryName(countryName);
            propertiesSingleton.setKey(userName+countryName);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
