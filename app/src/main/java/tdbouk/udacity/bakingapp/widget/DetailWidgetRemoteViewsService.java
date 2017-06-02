package tdbouk.udacity.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.utils.Utility;

public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            private ArrayList<String> ingredints;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                // Get last viewed recipe from shared preferences
                ingredints = Utility.getLastViewedRecipeId(DetailWidgetRemoteViewsService.this);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                if (ingredints == null || ingredints.size() < 0) return 0;
                return ingredints.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                // Set widgets data
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_widget);
                views.setTextViewText(R.id.tv_widget_ingredient, ingredints.get(position));

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
