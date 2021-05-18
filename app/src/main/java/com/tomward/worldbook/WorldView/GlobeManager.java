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

import com.mousebird.maply.BaseController;
import com.tomward.worldbook.R;
import com.tomward.worldbook.WhirlyGlobe.FileProcessor;
import com.tomward.worldbook.WhirlyGlobe.HelloGeoJsonFragment;
import com.tomward.worldbook.WhirlyGlobe.HelloGlobeFragment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class GlobeManager extends AppCompatActivity {

    private ArrayList<String> geojsons = new ArrayList<>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globe);
        try {
            FileProcessor fp = new FileProcessor(getApplicationContext());
            JSONArray geo = fp.parseFileToJSON("assets/geojson/countries.geojson");
            Iterator<JSONObject> listIterator = geo.iterator();
            HelloGeoJsonFragment fragment = new HelloGeoJsonFragment();
            BaseController baseController = fragment.getBaseController();
            while (listIterator.hasNext()){
                JSONObject country = listIterator.next();
                /*JSONObject properties = (JSONObject) country.get("properties");
                if(properties.get("ADMIN").equals("Belgium")){
                    System.out.println(country);
                }*/
                fragment.drawVector(country,baseController);

            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

    }







}
