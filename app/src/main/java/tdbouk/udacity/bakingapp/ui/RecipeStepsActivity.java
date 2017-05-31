package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_steps);

        if (savedInstanceState == null) {

            FragmentManager mFragmentManager = getSupportFragmentManager();

            Intent callingIntent = getIntent();
            if (callingIntent != null && callingIntent.hasExtra("recipe")) {
                Recipe recipe = callingIntent.getParcelableExtra("recipe");
                RecipeStepsFragment fragment = RecipeStepsFragment.newInstance(recipe);
                mFragmentManager.beginTransaction().add(R.id.detail_container, fragment).commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
