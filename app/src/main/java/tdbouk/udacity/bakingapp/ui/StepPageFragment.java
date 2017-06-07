package tdbouk.udacity.bakingapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import tdbouk.udacity.bakingapp.R;
import tdbouk.udacity.bakingapp.data.Recipe;
import tdbouk.udacity.bakingapp.data.Step;

public class StepPageFragment extends Fragment implements ExoPlayer.EventListener {
    private final String TAG = getClass().getSimpleName();

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_NUMBER = "step_number";
    private Recipe mRecipe;
    private int mStepNumber;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public StepPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe     Recipe.
     * @param stepNumber Recipe's step number.
     * @return A new instance of fragment StepFragment.
     */
    public static StepPageFragment newInstance(Recipe recipe, int stepNumber) {
        StepPageFragment fragment = new StepPageFragment();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                // Pause Video when pagers fragment changes
                if (mExoPlayer != null)
                    mExoPlayer.setPlayWhenReady(false);
            } else {
                // Resume Video once the fragment is visible again
                if (mExoPlayer != null)
                    mExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.pager_item_step, container, false);

        // Set up recipe description view
        TextView textView = (TextView) rootView.findViewById(R.id.tv_recipe_description);
        textView.setText(mRecipe.getSteps().get(mStepNumber).getDescription());

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        // Change actionbar title to recipe name
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(mRecipe.getName());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Initialize the player to play one video
        initializePlayer(mRecipe.getSteps().get(mStepNumber));
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param step The Step video to play.
     */
    private void initializePlayer(Step step) {
        // There is no step to show
        if (step == null)
            return;

        // Create an instance of the ExoPlayer.
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set a default image if video doesn't exist
            // and thumbnail is not available
            if (step.getVideoUrl() == null || step.getVideoUrl().isEmpty())
                if (step.getThumbnailUrl() == null || step.getVideoUrl().isEmpty()) {
                    Bitmap defaultIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_i_love_food_white);
                    mPlayerView.setDefaultArtwork(defaultIcon);
                    return;
                }

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Get video Uri
            Uri mediaUri = Uri.parse(step.getVideoUrl());

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);

            // Load up the media source
            // and start playing if the fragment is visible
            // the checking is necessary because the view pager adapter
            // keeps pages to the left and right loaded
            mExoPlayer.prepare(mediaSource);
            if (this.getUserVisibleHint())
                mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

}
