package tdbouk.udacity.bakingapp.utils;

/**
 * Created by toufik on 5/31/2017.
 */

public class Constants {

    // Base url to fetch recipes
    public static final String JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    // json base keys
    public static final String JSON_ID = "id";
    public static final String JSON_NAME = "name";
    public static final String JSON_SERVINGS = "servings";
    public static final String JSON_IMAGE = "image";
    public static final String JSON_INGREDIENTS = "ingredients";
    public static final String JSON_STEPS = "steps";

    // json keys for ingredients
    public static final String JSON_INGREDIENT_QUANTITY = "quantity";
    public static final String JSON_INGREDIENT_MEASURE = "measure";
    public static final String JSON_INGREDIENT_NAME = "ingredient";

    // json keys for steps
    public static final String JSON_STEP_ID = "id";
    public static final String JSON_STEP_SHORT_DESCRIPTION = "shortDescription";
    public static final String JSON_STEP_DESCRIPTION = "description";
    public static final String JSON_STEP_VIDEO_URL = "videoURL";
    public static final String JSON_STEP_THUMBNAIL_URL = "thumbnailURL";
}
