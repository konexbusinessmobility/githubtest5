package com.udacity.sandwichclub.utils;
//Reference https://github.com/YankeeDK/Sandwich-Club/blob/master/app/src/main/java/com/udacity/sandwichclub/utils/JsonUtils.java

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.udacity.sandwichclub.model.Sandwich;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {

            //Create Object
            JSONObject sandObject = new JSONObject(json);

            JSONObject sandNameObject = sandObject.getJSONObject("name");

            String nameOfSandwich = sandNameObject.getString("mainName");

            JSONArray jaAlsoKnownAs = sandNameObject.getJSONArray("alsoKnownAs");
            //Also Known As
            List<String> alsoKnownAsName = jsonArrayToStringList(jaAlsoKnownAs);

            String placeOfOrigin = sandObject.getString("placeOfOrigin");

            String sandDescription = sandObject.getString("description");

            String sandImage = sandObject.getString("image");


            JSONArray jaIngredients = sandObject.getJSONArray("ingredients");
            //Sandwich Ingredients
            List<String> sandwichIngredients = jsonArrayToStringList(jaIngredients);

            sandwich = new Sandwich(nameOfSandwich, alsoKnownAsName, placeOfOrigin, sandDescription, sandImage, sandwichIngredients);

            } catch (JSONException jsonExcep) {

            jsonExcep.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> jsonArrayToStringList (JSONArray jsonArray) throws JSONException {

        int arrayLength = jsonArray.length();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arrayLength; i++) {
            result.add(jsonArray.getString(i));
        }

        return result;
    }
}

