package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Set the title
        setTitle(sandwich.getMainName());

        //Set the image
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);

        populateAliasUI(sandwich.getAlsoKnownAs());

        populateOriginUI(sandwich.getPlaceOfOrigin());

        populateDescriptionUI(sandwich.getDescription());

        populateIngredientsUI(sandwich.getIngredients());
    }

    private void populateAliasUI(List<String> aka) {
        //Retrieve the AKA text view
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);

        if(aka == null || aka.size() == 0) {
            //This has largely been replaced by utilizing JSONObject.optString() in the JsonUtils class.
            alsoKnownAsTV.setText(R.string.alsoKnownAsUnavailable);
            return;
        }
        else {
            StringBuilder alsoKnownAsBuffer = new StringBuilder(""); //Set text buffer.

            //Append each alias to the list, appending commas or period based on position in list.
            for (int index = 0; index < aka.size(); index++) {

                if (index > 0 && index != (aka.size()))
                    alsoKnownAsBuffer.append(", ");

                alsoKnownAsBuffer.append(aka.get(index));
            }
            alsoKnownAsTV.setText(alsoKnownAsBuffer.toString());
        }
    }

    private void populateOriginUI(String origin) {
        //Retrieve the origin text view
        TextView originTV = findViewById(R.id.origin_tv);

        if(origin == null || origin.equals("")) {
            //This has largely been replaced by utilizing JSONObject.optString() in the JsonUtils class.
            originTV.setText(R.string.placeOfOriginUnavailable);
            return;
        }
        else
            originTV.setText(origin);
    }

    private void populateDescriptionUI(String description) {
        //Retrieve the description text view
        TextView descriptionTV = findViewById(R.id.description_tv);

        if(description == null || description.equals("")) {
            //This has largely been replaced by utilizing JSONObject.optString() in the JsonUtils class.
            descriptionTV.setText(R.string.descriptionUnavailable);
            return;
        }
        else
            descriptionTV.setText(description);
    }

    private void populateIngredientsUI(List<String> ingredients) {
        //Retrieve the ingredients text view
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        if(ingredients == null || ingredients.size() == 0) {
            //This has largely been replaced by utilizing JSONObject.optString() in the JsonUtils class.
            ingredientsTV.setText(R.string.ingredientsUnavailable);
            return;
        }
        else {
            ingredientsTV.setText(""); //Clear text buffer.

            //Append each ingredient to the list, appending commas or period based on position in list.
            /*
            for(int index = 0; index < ingredients.size(); index++) {

                if(index > 0 && index != ingredients.size())
                    ingredientsTV.append(", ");

                ingredientsTV.append(ingredients.get(index));
            }
            */
            String str = TextUtils.join(", ", ingredients);
            ingredientsTV.setText(str);
        }
    }
}
