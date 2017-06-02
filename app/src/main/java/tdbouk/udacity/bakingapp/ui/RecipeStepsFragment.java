package tdbouk.udacity.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.data.Step;
import tdbouk.udacity.bakingapp.utils.Utility;


public class RecipeStepsFragment extends Fragment {
    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_POSITION = "position";

    private RecyclerView mRecyclerView;
    private Recipe mRecipe;
    private int mPosition;
    private OnFragmentInteractionListener mListener;
    private String mIngredientsText = "";

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method used to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe   Recipe.
     * @param position list position.
     * @return A new instance of fragment RecipeStepsFragment.
     */
    public static RecipeStepsFragment newInstance(Recipe recipe, int position) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
            mPosition = getArguments().getInt(ARG_POSITION);
            getIngredients();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        // Set up Bottom Sheet Text
        setIngredientsText(mIngredientsText);

        // Change actionbar title to recipe name
        setActionBarTitle(mRecipe.getName());

        // Change toolbar image based on recipe
        setToolBarImage(Utility.getRecipeImageResource(mPosition));

        // Set up Recycler View
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity());
        RecipeStepsAdapter mAdapter = new RecipeStepsAdapter(getActivity(), mRecipe);
        mRecyclerView.setLayoutManager(linearManager);

        // Add a divider between items of Recycler View
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // Set the Recycler View's data
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void getIngredients() {
        if (mRecipe != null && mRecipe.getIngredients().size() > 0) {
            for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                mIngredientsText = mIngredientsText.concat(
                        Utility.formatIngredients(mRecipe.getIngredients().get(i)));
            }
        }
    }

    public void onButtonPressed(Recipe recipe, int stepNumber) {
        if (mListener != null) {
            mListener.onFragmentInteraction(recipe, stepNumber);
        }
    }

    public void setIngredientsText(String text) {
        if (mListener != null) {
            mListener.onSetIngredientsText(text);
        }
    }

    public void setActionBarTitle(String title) {
        if (mListener != null) {
            mListener.onSetActionBarTitle(title);
        }
    }

    public void setToolBarImage(int resource) {
        if (mListener != null) {
            mListener.onSetToolBarImage(resource);
        }
    }

    /**
     * Select the first item as a default item in 2 pane mode
     */
    public void setDefaultView() {
        // delay the operation until the view is created and initialized
        // by the adapter
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        }, 400);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getResources().getBoolean(R.bool.has_two_panes))
            setDefaultView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        void onFragmentInteraction(Recipe recipe, int stepNumber);

        void onSetIngredientsText(String text);

        void onSetActionBarTitle(String text);

        void onSetToolBarImage(int resource);
    }

    class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
        private Recipe mRecipe;

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mRecipeShortDescription;

            ViewHolder(TextView v) {
                super(v);
                mRecipeShortDescription = (TextView) v.findViewById(R.id.tv_recipe_step_short_description);
            }
        }

        RecipeStepsAdapter(Context context, Recipe recipe) {
            mRecipe = recipe;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecipeStepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_recipe_step, parent, false);

            final RecipeStepsAdapter.ViewHolder vh = new RecipeStepsAdapter.ViewHolder(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = vh.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onButtonPressed(mRecipe, position);
                    }
                }
            });

            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(RecipeStepsAdapter.ViewHolder holder, int position) {

            // Get recipe object at position
            Step step = mRecipe.getSteps().get(position);

            // Set the views
            holder.mRecipeShortDescription.setText(step.getShortDescription());
        }

        // Return the size of dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            if (mRecipe == null || mRecipe.getSteps().size() == 0) return 0;
            return mRecipe.getSteps().size();
        }
    }
}
