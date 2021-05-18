package com.tomward.worldbook.WhirlyGlobe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.mousebird.maply.BaseController;
import com.mousebird.maply.VectorInfo;
import com.mousebird.maply.VectorObject;
import com.mousebird.maply.BaseController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Iterator;


public class FileProcessor extends AppCompatActivity {
    private Context context;
    BaseController controller;



    public FileProcessor(Context context){
        this.context = context;
    }
    private JSONParser parser = new JSONParser();

    public JSONArray parseFileToJSON(String path) throws FileNotFoundException {
        try{
            //JSONObject obj = (JSONObject) parser.parse(new FileReader(path));
            //Context context = getApplicationContext();
            AssetManager manager = context.getAssets();
            InputStream is = manager.open("geojson/countries.geojson");
            Reader reader = new InputStreamReader(is);
            JSONObject obj = (JSONObject)parser.parse(reader);
            if (obj == null) {
                System.out.println("Object not found");
                return null;
            }
            JSONArray countryObjects = (JSONArray) obj.get("features");
            return countryObjects;




        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}