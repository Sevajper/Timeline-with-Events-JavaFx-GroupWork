package controls;

import java.util.ArrayList;

import functions.Event;
import functions.Timeline;

public interface ApplicationListener {
	
	/**
	 * Called when a new Timeline is selected from ComboBox in ApplicationView
	 * @param t , chosen Timeline
	 */
	void onTimelineSelected(Timeline t);

	void onNewEventSelected(Event e);
	/**
	 * Returns a list of currently loaded timeline
	 * @return arraylist containing timelines
	 */
	ArrayList<Timeline> getTimelines();
	

	void onTimelineSaved();
	
	void onTimelineLoaded();
}