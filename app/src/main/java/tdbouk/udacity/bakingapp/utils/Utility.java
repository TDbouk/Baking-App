package tdbouk.udacity.bakingapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tdbouk.udacity.bakingapp.data.Ingredient;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.data.Step;

public class Utility {

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
                    step.setShortDescription(stepsJsonObject.getString(Constants.JSON_STEP_DESCRIPTION));
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

}
