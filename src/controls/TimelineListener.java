package controls;

import java.time.LocalDateTime;


public interface TimelineListener {
	
	/**
	 * Handles adding of a timeline
	 * @param name Name of a timeline
	 * @param start Start date of a timeline
	 * @param end End date of a timeline
	 * @return true if timeline has been successfully added
	 */
	boolean onAddTimeline(String name, LocalDateTime start, LocalDateTime end);
	
	
	/**
	 * Deletes specified timeline from timeline list
	 * @param timeline
	 * @return true if timeline was deleted successfully
	 */
	boolean onDeleteTimeline ();
	
	boolean onDeleteFile();
}
