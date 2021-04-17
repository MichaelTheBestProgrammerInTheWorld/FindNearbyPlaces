package com.michaelmagdy.findnearbyplaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {



    private HashMap<String, String> getSingleNearbyPlace(JSONObject googlePlaceJson){

        HashMap<String, String> googlePlaceMap = new HashMap<>();

        String nameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")){
                nameOfPlace = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")){
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry")
                    .getJSONObject("location")
                    .getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", nameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("ref", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray){

        int counter = jsonArray.length();
        List<HashMap<String, String>> nearbyPlacesList = new ArrayList<>();
        HashMap<String, String> nearbyPlaceMap = null;
        for (int i=0; i<counter; i++){
            try {
                nearbyPlaceMap = getSingleNearbyPlace( (JSONObject) jsonArray.get(i));
                nearbyPlacesList.add(nearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyPlacesList;
    }

    public List<HashMap<String, String>> parse(String jsonData){

        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPlaces(jsonArray);
    }
}
