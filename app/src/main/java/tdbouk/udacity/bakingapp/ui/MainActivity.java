package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener {


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
}
