package controls;

import java.time.LocalDateTime;


public interface EventListener {
	
	/**
	 * Requests to add an event with duration
	 * @param name, user input
	 * @param description, user input
	 * @param start, user input
	 * @param end, user input
	 * @return boolean, returns true if an event was created. 
	 * false if input wasn't valid and event could not be created
	 */
	boolean onAddEventDuration(String name, String description, LocalDateTime start, LocalDateTime end);	//add event with a duration 
	
	/**
	 * Requests to add an event without duration
	 * @param name, user input
	 * @param description, user input
	 * @param start, user input
	 * @return boolean, returns true if an event was created. 
	 * false if input wasn't valid and event could not be created
	 */
	boolean onAddEvent(String name, String description, LocalDateTime start);							//add event without a duration
	
	/**
	 * Request to edit an Event with duration
	 * @param name, user input
	 * @param description, user input
	 * @param start, user input
	 * @param end, user input
	 * @return boolean, returns true if the event was Edited. 
	 * false if input wasn't valid and the event could not be Edited
	 */
	boolean onEditEventDuration(String name, String description, LocalDateTime start, LocalDateTime end);
	
	/**
	 * Requests to edit an Event without duration
	 * @param name, user input
	 * @param description, user input
	 * @param start, user input
	 * @return boolean, returns true if the event was Edited. 
	 * false if input wasn't valid and the event could not be Edited
	 */
	boolean onEditEvent(String name, String description, LocalDateTime start);
	
	/**
	 * Requests to delete existing event.
	 * @return true if event was deleted successfully
	 */
	boolean onDeleteEvent();
}