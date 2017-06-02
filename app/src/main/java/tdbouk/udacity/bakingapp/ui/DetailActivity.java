package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Intent callingIntent = getIntent();

            if (callingIntent != null && callingIntent.hasExtra("recipe")
                    && callingIntent.hasExtra("step_number")) {

                Recipe recipe = callingIntent.getParcelableExtra("recipe");
                int stepNumber = callingIntent.getIntExtra("step_number", 0);

                StepFragment fragment = StepFragment.newInstance(recipe, stepNumber);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.steps_detail_container, fragment)
                        .commit();
            }
        }
    }
}
