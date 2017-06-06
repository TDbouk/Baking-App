package tdbouk.udacity.bakingapp.events;

/**
 * This class represents an event associated with the user
 * switching between the different steps via the View Pager.
 * The event includes the Step's position that is changing based
 * on the user's interaction.
 */

public class ViewPagerEvent {
    public final int position;

    public ViewPagerEvent(int position) {
        this.position = position;
    }
}
