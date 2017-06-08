package tdbouk.udacity.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.events.ViewPagerEvent;
import tdbouk.udacity.bakingapp.utils.Utility;

public class StepFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_NUMBER = "step_number";

    ViewPager mPager;
    MyAdapter mAdapter;
    Recipe mRecipe;
    int mStepNumber = 0;

    public static StepFragment newInstance(Recipe recipe, int stepNumber) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
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
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mPager = (ViewPager) rootView.findViewById(R.id.pager);

        mPager.setAdapter(mAdapter);

        // set pager item to the step selected by the user
        mPager.setCurrentItem(mStepNumber);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Method called only in tablets to change selected item position
                setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set toolbar image on screen orientation change
        setToolBarImage(Utility.getRecipeImageResource(mRecipe.getPositionInGridView()));
    }

    private void setActionBarTitle(String text) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(text);
    }

    private void setToolBarImage(int resource) {
        ((ImageView) getActivity().findViewById(R.id.img_toolbar)).setImageResource(resource);
    }

    // Notify Master layout in 2-pane layouts to modify
    // Selected item by firing an event
    public void setSelectedItem(int position) {
        if (getActivity().getResources().getBoolean(R.bool.has_two_panes))
            EventBus.getDefault().post(new ViewPagerEvent(position));
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
            setActionBarTitle(mRecipe.getName());
            setToolBarImage(Utility.getRecipeImageResource(mRecipe.getPositionInGridView()));
            return StepPageFragment.newInstance(mRecipe, position);
        }
    }
}
