package tdbouk.udacity.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Ingredient;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.data.Step;

public final class Utility {

    private Utility() {
    }

    /**
     * Parse Json string to get the recipe info
     *
     * @param recipesJson
     * @return ArrayList of Recipes
     */
    public static ArrayList<Recipe> parseRecipeJson(JSONArray recipesJson) {

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe;
        Ingredient ingredient;
        Step step;

        for (int i = 0; i < recipesJson.length(); i++) {

            recipe = new Recipe();
            try {
                // get recipe object
                JSONObject recipeJsonObject = recipesJson.getJSONObject(i);
                recipe.setId(recipeJsonObject.getInt(Constants.JSON_ID));
                recipe.setName(recipeJsonObject.getString(Constants.JSON_NAME));
                recipe.setNumberOfServings(recipeJsonObject.getInt(Constants.JSON_SERVINGS));
                recipe.setImageUrl(recipeJsonObject.getString(Constants.JSON_IMAGE));

                // get recipe ingredient array
                JSONArray ingredientsJsonArray = recipeJsonObject.getJSONArray(Constants.JSON_INGREDIENTS);
                for (int j = 0; j < ingredientsJsonArray.length(); j++) {

                    // get recipe ingredient object
                    JSONObject ingredientsJsonObject = ingredientsJsonArray.getJSONObject(j);
                    ingredient = new Ingredient();
                    ingredient.setQuantity(ingredientsJsonObject.getDouble(Constants.JSON_INGREDIENT_QUANTITY));
                    ingredient.setMeasure(ingredientsJsonObject.getString(Constants.JSON_INGREDIENT_MEASURE));
                    ingredient.setIngredient(ingredientsJsonObject.getString(Constants.JSON_INGREDIENT_NAME));

                    recipe.getIngredients().add(ingredient);
                }

                // get recipe steps array
                JSONArray stepsJsonArray = recipeJsonObject.getJSONArray(Constants.JSON_STEPS);
                for (int x = 0; x < stepsJsonArray.length(); x++) {

                    // get recipe step object
                    JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(x);
                    step = new Step();
                    step.setId(stepsJsonObject.getInt(Constants.JSON_STEP_ID));
                    step.setShortDescription(stepsJsonObject.getString(Constants.JSON_STEP_SHORT_DESCRIPTION));
                    step.setDescription(stepsJsonObject.getString(Constants.JSON_STEP_DESCRIPTION));
                    step.setVideoUrl(stepsJsonObject.getString(Constants.JSON_STEP_VIDEO_URL));
                    step.setThumbnailUrl(stepsJsonObject.getString(Constants.JSON_STEP_THUMBNAIL_URL));

                    recipe.getSteps().add(step);
                }

                // fill recipe array
                recipes.add(recipe);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return (ArrayList<Recipe>) recipes;
    }

    /**
     * Return recipe image resource based on position in recycler view
     *
     * @param position position in list
     * @return Resource ID - int
     */
    public static int getRecipeImageResource(int position) {
        int recipeResources[] = new int[]{R.drawable.ic_recipe_1, R.drawable.ic_recipe_2,
                R.drawable.ic_recipe_3, R.drawable.ic_recipe_4,};

        if (position >= 0 && position < recipeResources.length)
            return recipeResources[position];

        return 0;
    }

    /**
     * Save the last viewed recipe's ingredients in shared preferences
     *
     * @param context Context
     * @param recipe  Recipe
     */
    public static void saveLastViewedRecipeId(Context context, Recipe recipe) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (recipe != null && recipe.getIngredients().size() > 0) {
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                prefs.edit().putString(QuickPreferences.LAST_VIEWED_RECIPE + "_" + i,
                        formatIngredients(recipe.getIngredients().get(i))).apply();
            }
            prefs.edit().putInt(QuickPreferences.NUMBER_OF_INGREDIENTS,
                    recipe.getIngredients().size()).apply();
        }
    }

    /**
     * Get the last viewed recipe's Ingredients from shared preferences
     *
     * @param context Context
     * @return ArrayList of strings containing all ingredients of recipe
     */
    public static ArrayList<String> getLastViewedRecipeId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<String> ingredients = new ArrayList<>();
        final int numberOfIngredients = prefs.getInt(QuickPreferences.NUMBER_OF_INGREDIENTS, 0);
        for (int i = 0; i < numberOfIngredients; i++) {
            ingredients.add(prefs.getString(QuickPreferences.LAST_VIEWED_RECIPE + "_" + i, ""));
        }
        return ingredients;
    }

    /**
     * Format ingredients into 1 string
     *
     * @param ingredient
     * @return String as follows (ingredients - quantity - measure)
     */
    public static String formatIngredients(Ingredient ingredient) {
        return ingredient.getIngredient()
                + " x " + ingredient.getQuantity() + " "
                + ingredient.getMeasure();
    }
}

