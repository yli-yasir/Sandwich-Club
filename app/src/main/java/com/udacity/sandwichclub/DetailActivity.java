package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JSONUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        if (intent!=null){
            /*The array that contains the the sandwich details, Where each index in the array
            corresponds to the position stored i n the intent that's started this activity.*/
            String[] sandwichDetails = getResources().getStringArray(R.array.sandwich_details);

            /*Get the position stored in the intent, if it's not found then put -1 as it represents
            an invalid index which will be used in the next check.*/
            int position = intent.getIntExtra(EXTRA_POSITION,-1);


            //Exit if the position represents an invalid index in the details array.
            if (!(position>= 0 && position<sandwichDetails.length)) {
                closeWithErrorToast();
                return;
            }


            //Parse the Json
            Sandwich selectedSandwich = JSONUtils.parseSandwichJson(sandwichDetails[position]);

            //If the selected sandwich was successfully parsed...
            if (selectedSandwich!=null) populateUI(selectedSandwich);

        }
        //Exit if the intent is null.
        else{
            closeWithErrorToast();
        }


    }


    private void closeWithErrorToast() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void populateUI(Sandwich sandwich) {

        ImageView sandwichImageView = findViewById(R.id.sandwich_iv);

        //Get references to the text views holding the content.
        TextView alsoKnownAs = findViewById(R.id.also_known_as_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView placeOfOrigin = findViewById(R.id.place_of_origin_tv);
        TextView description = findViewById(R.id.description_tv);

        hideOrPopulateSection(alsoKnownAs,sandwich.getAlsoKnownAs().toString());
        hideOrPopulateSection(ingredients,sandwich.getIngredients().toString());
        hideOrPopulateSection(placeOfOrigin,sandwich.getPlaceOfOrigin());
        hideOrPopulateSection(description,sandwich.getDescription());

        //Load the sandwich image into the image view.
        Picasso.get()
                .load(sandwich.getImage())
                .into(sandwichImageView);

        //Set the title of the activity.
        setTitle(sandwich.getMainName());

    }


    /*After checking if there is valid data in the content string, it populates the contentTextView with the provided content string.
      If there isn't valid data, then the visibility of the section, is
      set to GONE.*/
    private void hideOrPopulateSection(TextView contentTextView, String content){

        //Remove any array brackets, in case they exist...
        content= content.replace("[","").replace("]","");

        //Hide the header label and the content if there is no content.
        if (content == null || content.isEmpty()){
            contentTextView.setVisibility(View.GONE);
        }
        //Else just populate the content with the text.
        else{
            //Simplicity: using concatenation with setText(), since this won't ever be translated.
            contentTextView.setText(contentTextView.getText() + " " + content);
        }

    }
}
