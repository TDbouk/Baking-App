package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class StepActivity extends AppCompatActivity {

    ViewPager mPager;
    MyAdapter mAdapter;
    Recipe mRecipe;
    int mStepNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);

        Intent callingIntent = getIntent();
        if (callingIntent != null && callingIntent.hasExtra("recipe")
                && callingIntent.hasExtra("step_number")) {
            mRecipe = callingIntent.getParcelableExtra("recipe");
            mStepNumber = callingIntent.getIntExtra("step_number", 0);
        }
        mPager.setAdapter(mAdapter);

        // set pager item to the step selected by the user
        mPager.setCurrentItem(mStepNumber);
    }

    private class MyAdapter extends FragmentStatePagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            if (mRecipe == null || mRecipe.getSteps().size() == 0) return 0;
            return mRecipe.getSteps().size();
        }

        @Override
        public Fragment getItem(int position) {
            return StepFragment.newInstance(mRecipe, position);
        }
    }

}
