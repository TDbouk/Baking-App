package tdbouk.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;

public class StepActivity extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_NUMBER = "step_number";

    ViewPager mPager;
    MyAdapter mAdapter;
    Recipe mRecipe;
    int mStepNumber = 0;

    public static StepActivity newInstance(Recipe recipe, int stepNumber) {
        StepActivity fragment = new StepActivity();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putInt(ARG_STEP_NUMBER, stepNumber);
        args.putInt(ARG_STEP_NUMBER, stepNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
            mStepNumber = getArguments().getInt(ARG_STEP_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_step, container, false);

        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager = (ViewPager) rootView.findViewById(R.id.pager);

     /*   Intent callingIntent = getActivity().getIntent();
        if (callingIntent != null && callingIntent.hasExtra("recipe")
                && callingIntent.hasExtra("step_number")) {
            mRecipe = callingIntent.getParcelableExtra("recipe");
            mStepNumber = callingIntent.getIntExtra("step_number", 0);
        }*/
        mPager.setAdapter(mAdapter);

        // set pager item to the step selected by the user
        mPager.setCurrentItem(mStepNumber);
        return rootView;
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
