package com.tomward.worldbook.WorldView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tomward.worldbook.R;
import com.tomward.worldbook.WhirlyGlobe.FileProcessor;

import org.json.simple.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GlobeManager extends AppCompatActivity {

    private ArrayList<String> geojsons = new ArrayList<>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globe);
        try {
            FileProcessor fp = new FileProcessor(getApplicationContext());
            JSONObject geo = fp.parseFileToJSON("assets/geojson/countries.geojson");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }







}
