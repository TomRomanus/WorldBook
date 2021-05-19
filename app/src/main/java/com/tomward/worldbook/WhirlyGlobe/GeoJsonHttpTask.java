package com.tomward.worldbook.WhirlyGlobe;


import android.graphics.Color;
import android.os.AsyncTask;
import com.mousebird.maply.BaseController;
import com.mousebird.maply.VectorInfo;
import com.mousebird.maply.VectorObject;

//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GeoJsonHttpTask extends AsyncTask<String, Void, String> {

    BaseController controller;

    public GeoJsonHttpTask(BaseController maplyBaseController) {
        controller = maplyBaseController;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection;
        try {
            String urlStr = params[0];
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(7000);
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (Exception e) {
            // didn't work
        }
        return null;
    }
    @Override
    protected void onPostExecute(String json) {
        System.out.println("Countries have been loaded");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json);
            JSONArray countryObjects = (JSONArray) obj.get("features");
            Iterator<JSONObject> listIterator = countryObjects.iterator();
            while (listIterator.hasNext()){
                JSONObject country = listIterator.next();
                drawVector(country);
            }
            System.out.println("Finished drawing all countries");
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

private void drawVector(JSONObject country){
    System.out.println("We have drawn a country");
    VectorObject object = new VectorObject();
    if (object.fromGeoJSON(country.toJSONString())) {
        VectorInfo vectorInfo = new VectorInfo();
        vectorInfo.setColor(Color.RED);
        vectorInfo.setLineWidth(4.f);
        controller.addVector(object, vectorInfo, BaseController.ThreadMode.ThreadAny);
    }

}
}

