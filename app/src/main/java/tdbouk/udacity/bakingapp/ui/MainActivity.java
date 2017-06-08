package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.idlingResource.SimpleIdlingResource;
import tdbouk.udacity.bakingapp.ui.fragment.RecipeFragment;

public class MainActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener {


    //Idling resource used for testing
    @Nullable
    @VisibleForTesting
    private SimpleIdlingResource mSimpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getIdlingResource() {
        if (mSimpleIdlingResource == null)
            mSimpleIdlingResource = new SimpleIdlingResource();
        return mSimpleIdlingResource;
    }

    public SimpleIdlingResource getSimpleIdlingResource() {
        return mSimpleIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
            mSimpleIdlingResource.setIdleState(false);
        }
    }

    @Override
    public void onRecipeFragmentInteraction(Recipe recipe, int position) {

        Intent recipeSteps = new Intent(this, RecipeStepsActivity.class);
        recipeSteps.putExtra("recipe", recipe);
        recipeSteps.putExtra("position", position);
        startActivity(recipeSteps);

    }
}
