package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    public static Sandwich parseSandwichJson(String json)  {
        Sandwich sandwich = null;
        try {
            JSONObject containerObject = new JSONObject(json);

            JSONObject name = containerObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

            String placeOfOrigin = containerObject.getString("placeOfOrigin");

            String description = containerObject.getString("description");

            String image = containerObject.getString("image");

            JSONArray ingredients = containerObject.getJSONArray("ingredients");

            sandwich = new Sandwich(mainName, JSONArrayToList(alsoKnownAs), placeOfOrigin, description, image,JSONArrayToList(ingredients));
        }

        catch(JSONException j){
            j.printStackTrace();
        }

        return sandwich;
    }

    private static ArrayList<String> JSONArrayToList(JSONArray jsonArray) throws JSONException{
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) list.add(jsonArray.getString(i));
        return list;
    }
}
