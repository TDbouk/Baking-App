package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener,
        RecipeStepsFragment.OnFragmentInteractionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeFragmentInteraction(Recipe recipe) {

        Intent recipeSteps = new Intent(this, RecipeStepsActivity.class);
        recipeSteps.putExtra("recipe", recipe);
        startActivity(recipeSteps);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
