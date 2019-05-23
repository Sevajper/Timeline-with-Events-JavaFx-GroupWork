package controls;

import java.util.ArrayList;

import functions.Timeline;

public interface ChangeListener {
	
	/**
	 * Called to inform the ApplicationView that a Timeline has been Created
	 * or Deleted
	 * @param timelines , ArrayList<Timeline>
	 * @param current , Timeline
	 */
	void onChangedTimeline(ArrayList<Timeline> timelines, Timeline current);//used when a timeline is added or deleted
	
	/**
	 * Called to inform ApplicationView that a new Timeline has been selected
	 * @param current , Timeline
	 */
	void onNewTimelineSelected(Timeline current);							//used when current timeline is updated
	
	/**
	 * Called to inform the ApplicationView that a Timeline has been
	 * modified
	 * @param current , Timeline
	 */
	void onEditTimeline(Timeline current);									//used when current timeline is edited, either timeline or event
																			//or when new events are created or deleted

	/**
	 * Called to inform the ApplicationView that an Event has been modified
	 * in certain Timeline, therefore Timeline view has to change
	 * @param current Timeline
	 */
	void onEditEvent(Timeline current);

	void onTimelineSaved(Timeline current);
	
		
	}

