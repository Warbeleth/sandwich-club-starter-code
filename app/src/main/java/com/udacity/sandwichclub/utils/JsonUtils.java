package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static private final String TAG = "JsonUtils.java";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN = "alsoKnownAs";
    private static final String KEY_PLACE_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Log.d(TAG, "parseSandwichJson: " + json);
        /* Example JSON string.
        parseSandwichJson:
        {
            "name":
            {
                "mainName":"Ham and cheese sandwich",
                "alsoKnownAs":[]
            },
            "placeOfOrigin":"",
            "description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.",
            "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
            "ingredients":["Sliced bread","Cheese","Ham"]
            }
         */
        Sandwich sandwich = null;
        JSONObject jsonObject = null;

        try {
            //Create JSON object from json string.
            jsonObject = new JSONObject(json);
            //Create sandwich for return
            sandwich = new Sandwich();

            //Extract the name object
            JSONObject name = jsonObject.getJSONObject(KEY_NAME);

            //Extract main name from name object and store in new sandwich
            sandwich.setMainName(name.getString(KEY_MAIN_NAME));

            //Extract known as from name object.
            JSONArray alsoKnownAsJSON = name.getJSONArray(KEY_ALSO_KNOWN);
            List<String> alsoKnownAsStrings = new ArrayList<>();

            //Parse knownas into list of strings.
            for(int index = 0; index < alsoKnownAsJSON.length(); index++)
            {
                alsoKnownAsStrings.add(alsoKnownAsJSON.getString(index));
            }

            //Store known as into sandwich
            sandwich.setAlsoKnownAs(alsoKnownAsStrings);

            //Extract place of origin and store in new sandwich
            sandwich.setPlaceOfOrigin(jsonObject.getString(KEY_PLACE_ORIGIN));

            //Extract description and store in new sandwich
            sandwich.setDescription(jsonObject.getString(KEY_DESCRIPTION));

            //Extract image and store in new sandwich
            sandwich.setImage(jsonObject.getString(KEY_IMAGE));

            //Extract ingredients from object.
            JSONArray ingredientsJSON = jsonObject.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredientsStrings = new ArrayList<>();

            //Parse knownas into list of strings.
            for(int index = 0; index < ingredientsJSON.length(); index++)
            {
                ingredientsStrings.add(ingredientsJSON.getString(index));
            }

            //Store ingredients into sandwich
            sandwich.setIngredients(ingredientsStrings);

        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: " + e.getMessage() );
            e.printStackTrace();
        }


        return sandwich;
    }
}
