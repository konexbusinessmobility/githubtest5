// Reference KostasAnagnostou @ https://github.com/KostasAnagnostou/sandwich-club-app/blob/master/app/src/main/java/com/udacity/sandwichclub/DetailActivity.java
// Reference Wallace Jackson, JSON Quick Syntax Reference,

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Initialize
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView alsoKnownAsLabel = findViewById(R.id.alsoKnownAs_label);
        TextView alsoKnownAsTextView = findViewById(R.id.alsoKnownAs_label);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        String placeOfOriginText = sandwich.getPlaceOfOrigin();
        if (placeOfOriginText.isEmpty()) {

            originTextView.setText(R.string.empty_message);

        } else  {

            originTextView.setText(placeOfOriginText);
        }

        List<String>alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.size() == 0) {

            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);

        } else {
            alsoKnownAsLabel.setVisibility(View.VISIBLE);
            alsoKnownAsTextView.setVisibility(View.VISIBLE);

            //Build to set value
            StringBuilder akaNames = new StringBuilder();

            for (String akaName : alsoKnownAsList) {
                akaNames.append(akaName).append(", ");
            }

            akaNames.setLength(akaNames.length() -2);

            alsoKnownAsTextView.setText(akaNames);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.size() == 0) {

            ingredientsTextView.setText(R.string.empty_message);
        } else {

            StringBuilder  setIngredients = new StringBuilder();

            for (String ingredient : ingredientsList) {

                setIngredients.append(ingredient).append(", ");

            }

            setIngredients.setLength(setIngredients.length() -2);

            ingredientsTextView.setText(setIngredients);

        }

        //Sandwich Description
        String sandwichDescription = sandwich.getDescription();
        if (sandwichDescription.isEmpty()) {

            descriptionTextView.setText(R.string.empty_message);
        } else {

            descriptionTextView.setText(sandwichDescription);
        }

    }
}
