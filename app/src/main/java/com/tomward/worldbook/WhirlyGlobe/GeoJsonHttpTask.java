package com.tomward.worldbook.WhirlyGlobe;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mousebird.maply.BaseController;
import com.mousebird.maply.VectorInfo;
import com.mousebird.maply.VectorObject;

import com.tomward.worldbook.WorldView.GlobeManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class GeoJsonHttpTask extends AsyncTask<String, Void, String> {

    BaseController controller;

    public GeoJsonHttpTask(BaseController maplyBaseController) {
        controller = maplyBaseController;
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(String json) {
        System.out.println("Countries have been loaded");
        try {
            FileProcessor fp = new FileProcessor(GlobeManager.mContext);
            JSONArray countryObjects = fp.parseFileToJSON("geojson/world.geojson");
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
    int vectorColor = Color.BLACK;
    float lineWidth =0.f;

    VectorObject object = new VectorObject();
    if (object.fromGeoJSON(country.toJSONString())) {
        VectorInfo vectorInfo = new VectorInfo();
        vectorInfo.setColor(vectorColor);
        vectorInfo.setLineWidth(lineWidth);
        controller.addVector(object, vectorInfo, BaseController.ThreadMode.ThreadAny);
    }

}
}

