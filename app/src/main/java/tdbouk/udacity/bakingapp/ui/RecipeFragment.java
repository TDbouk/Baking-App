package tdbouk.udacity.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.utils.Constants;
import tdbouk.udacity.bakingapp.utils.Utility;

public class RecipeFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private OnFragmentInteractionListener mListener;
    private ArrayList<Recipe> mReceipes = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridManager;
    private RecipesAdapter mAdapter;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET, Constants.JSON_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                        Log.d(TAG, "json res: " + response);
                        mReceipes = Utility.parseRecipeJson(response);

                        // Swap adapter's data
                        if (mReceipes != null)
                            mAdapter.swapAdapter(mReceipes);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_recipe, container, false);

        mRecyclerView = (RecyclerView) rooView.findViewById(R.id.rv_recipes);
        mGridManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_view_columns));
        mAdapter = new RecipesAdapter(getActivity(), null);
        mRecyclerView.setLayoutManager(mGridManager);
        mRecyclerView.setAdapter(mAdapter);

        return rooView;
    }

    public void onButtonPressed(Recipe recipe, int position) {
        if (mListener != null) {
            mListener.onRecipeFragmentInteraction(recipe, position);
        }
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

    public interface OnFragmentInteractionListener {
        void onRecipeFragmentInteraction(Recipe recipe, int position);
    }

    class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
        private ArrayList<Recipe> mRecipes;
        private Context mContext;

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView recipeTitle;
            final TextView recipeServings;
            final TextView recipeSteps;
            final TextView recipeIngredients;
            final ImageView recipeImage;

            ViewHolder(View v) {
                super(v);
                recipeTitle = (TextView) v.findViewById(R.id.tv_recipe_title);
                recipeServings = (TextView) v.findViewById(R.id.tv_recipe_number_servings);
                recipeSteps = (TextView) v.findViewById(R.id.tv_recipe_number_steps);
                recipeIngredients = (TextView) v.findViewById(R.id.tv_recipe_number_ingredients);
                recipeImage = (ImageView) v.findViewById(R.id.iv_recipe_image);
            }
        }

        RecipesAdapter(Context context, ArrayList<Recipe> recipes) {
            mContext = context;
            mRecipes = recipes;
        }

        void swapAdapter(ArrayList<Recipe> recipes) {
            if (recipes == null || recipes.size() == 0) return;
            mRecipes = recipes;
            notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_recipe, parent, false);

            final ViewHolder vh = new ViewHolder(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = vh.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Recipe r = mRecipes.get(position);
                        onButtonPressed(r, position);

                        // Save id if recipe in shared preferences
                        Utility.saveLastViewedRecipeId(getActivity(), r);

                        // Notify that a recent viewed recipe changed
                        getActivity().sendBroadcast(new Intent(Constants.ACTION_RECENT_RECIPE_UPDATED)
                                .setPackage(getActivity().getPackageName()));
                    }
                }
            });

            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            // Get recipe object at position
            Recipe recipe = mRecipes.get(position);

            // Set the views
            holder.recipeTitle.setText(recipe.getName());
            holder.recipeServings.setText(mContext.getString(
                    R.string.title_servings, recipe.getNumberOfServings()));
            holder.recipeSteps.setText(mContext.getString(
                    R.string.title_steps, recipe.getSteps().size()));
            holder.recipeIngredients.setText(mContext.getString(
                    R.string.title_ingredients, recipe.getIngredients().size()));

            holder.recipeImage.setImageResource(Utility.getRecipeImageResource(position));

            // set content description based on recipe's name
            holder.recipeImage.setContentDescription(mContext.getString(
                    R.string.cd_recipe, recipe.getName()));
        }

        // Return the size of dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            if (mRecipes == null || mRecipes.size() == 0) return 0;
            return mRecipes.size();
        }
    }
}

