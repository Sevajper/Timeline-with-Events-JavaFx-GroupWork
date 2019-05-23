package functions;

import java.time.LocalDateTime;
import java.util.ArrayList;

import controls.ChangeListener;

public class App {
	
	ChangeListener changeListener;
	private ArrayList<Timeline> timelines;
	private Timeline currentTimeline;
	private Event currentEvent;
	
	/**
	 * Constructor, initializes and ArrayList<Timeline>
	 */
	public App() {
		timelines = new ArrayList<Timeline>();
	}
	
	/**
	 * Create a new timeline and adds it to the timeline list, also update the "current timeline"
	 * variable.
	 * @param name - String name of the timeline
	 * @param start - LocalDate the start of the timeline
	 * @param end - LocalDate the end of the timeline
	 */
	public void addTimeline(String name, LocalDateTime start, LocalDateTime end) {
		setCurrentTimeline(new Timeline(name, start, end));
		timelines.add(currentTimeline);
		changeListener.onChangedTimeline(timelines, currentTimeline);
	}
	
	/**
	 * Adds an Event to current timeline by calling the addEvent method in Timeline
	 * @param name, event name
	 * @param description , event description
	 * @param start , event start date
	 */
	public void addEventToCurrent(String name, String description, LocalDateTime start) {
		currentTimeline.addEvent(name, description, start);
		setCurrentEvent(currentTimeline.getEvents().get(currentTimeline.getEvents().size()-1));
		changeListener.onEditTimeline(currentTimeline);
	}
	
	/**
	 * Adds an Event with duration to current timeline by calling the addEvent method in Timeline
	 * @param name, event name
	 * @param description , event description
	 * @param start , event start date
	 * @param end , event end date
	 */
	public void addEventToCurrentDuration(String name, String description, LocalDateTime start, LocalDateTime end) {
		currentTimeline.addEventDuration(name, description, start, end);
		setCurrentEvent(currentTimeline.getEvents().get(currentTimeline.getEvents().size()-1));
		changeListener.onEditTimeline(currentTimeline);
	}
	
	/**
	 * Removes selected timeline from timeline list.
	 * @param timeline to be removed
	 */
	public void removeTimeline() {
		getTimelines().remove(currentTimeline);
		if (timelines.size() > 0) {
			currentTimeline = timelines.get(0);
		}
		else {
			currentTimeline = null;
		}
		changeListener.onChangedTimeline(timelines, currentTimeline);
	}
	
	public void removeFile() {
		currentTimeline.getFile().delete();
	}
	
	/**
	 * Removes selected event from timeline
	 * @param event to be removed
	 */
	public void removeEvent() {
		currentTimeline.deleteEvent(currentEvent);
		changeListener.onEditTimeline(currentTimeline);
	}
	
	/**
	 * Update the ChangeListener variable with the ChangeListener given as input
	 * @param cl , (ChangeListener)
	 */
	public void addListener(ChangeListener cl) {
		changeListener = cl;
	}
	
	/**
	 * update the variable current with a new current Timeline
	 * @param t , the Timeline used at the moment
	 */
	public void setCurrentTimeline(Timeline t) {
		currentTimeline = t;
		changeListener.onNewTimelineSelected(currentTimeline);
	}
	
	/**
	 * returns the currently open timeline
	 * @return Timeline
	 */
	public Timeline getCurrentTimeline() {
		return currentTimeline;
	}

	/**
	 * returns a list of all open timelines in the App
	 * @return ArrayList<Timeline>
	 */
	public ArrayList<Timeline> getTimelines(){
		return timelines;
	}
	
	/**
	 * Adds timeline object to list of loaded timelines
	 * @param t timeline object
	 */
	public void addTimelineToList(Timeline t) {
		timelines.add(t);
		currentTimeline = t;
		changeListener.onChangedTimeline(timelines, currentTimeline);
	}
	
	/**
	 * Returns true if event is duration event
	 * @return true
	 */
	public boolean isEventDuration() {
		return currentEvent.isDuration();
	}
	
	/**
	 * Changes current event
	 * @param e event to be set to current event
	 */
	public void setCurrentEvent(Event e) {
		currentEvent = e;
	}
	
	/**
	 * Returns current event
	 * @return current event
	 */
	public Event getCurrentEvent() {
		return currentEvent;
	}
	
	/**
	 * Informs change listener about changed timeline
	 */
	public void eventEdited() {
		changeListener.onEditEvent(currentTimeline);
	}
	
}