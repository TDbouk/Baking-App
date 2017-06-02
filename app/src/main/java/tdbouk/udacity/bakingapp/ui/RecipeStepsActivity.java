package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements
        RecipeStepsFragment.OnFragmentInteractionListener {

    private boolean mTowPane;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_steps);

        // Set up Bottom Sheet
        mIngredientsTextView = (TextView) findViewById(R.id.tv_ingredients);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        FloatingActionButton ingredientsFab = (FloatingActionButton)
                findViewById(R.id.fab_show_ingredients);

        ingredientsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });

        if (savedInstanceState == null) {

            FragmentManager mFragmentManager = getSupportFragmentManager();

            Intent callingIntent = getIntent();
            if (callingIntent != null && callingIntent.hasExtra("recipe")
                    && callingIntent.hasExtra("position")) {
                Recipe recipe = callingIntent.getParcelableExtra("recipe");
                int position = callingIntent.getIntExtra("position", 0);
                RecipeStepsFragment fragment = RecipeStepsFragment.newInstance(recipe, position);
                mFragmentManager.beginTransaction().add(R.id.steps_main_container, fragment).commit();


            }

        }
    }


    @Override
    public void onFragmentInteraction(Recipe recipe, int stepNumber) {
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            StepActivity fragment = StepActivity.newInstance(recipe, stepNumber);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_detail_container, fragment)
                    .commit();

        } else {
            startActivity(new Intent(this, StepActivity.class)
                    .putExtra("recipe", recipe)
                    .putExtra("step_number", stepNumber));
        }
    }

    @Override
    public void onSetIngredientsText(String text) {
        mIngredientsTextView.setText(text);
    }

    @Override
    public void onSetActionBarTitle(String text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(text);
    }

    @Override
    public void onSetToolBarImage(int resource) {
        ((ImageView) findViewById(R.id.img_toolbar)).setImageResource(resource);
    }
}
