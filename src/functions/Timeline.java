package functions;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Timeline")
@XmlType(propOrder = {"name", "start", "end", "events", "filePath"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Timeline {
	
	@XmlElement(name = "Event")
	private ArrayList<Event> events = new ArrayList<Event>();
	
	@XmlElement(name = "TimelineName")
	private String name;
	@XmlJavaTypeAdapter(value = io.LocalDateTimeXmlAdapter.class)
	@XmlElement(name = "TimelineStartDate")
	private LocalDateTime start;
	@XmlJavaTypeAdapter(value = io.LocalDateTimeXmlAdapter.class)
	@XmlElement(name = "TimelineEndDate")
	private LocalDateTime end;
	@XmlElement(name = "FilePath")
	private File filePath = new File("");
	
	
	// Constructor
	public Timeline (String name, LocalDateTime start, LocalDateTime end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}
	public Timeline() {
		
	}
	
	// Getters
	public String getName() {
		return name;
	}
	public LocalDateTime getStart() {
		return start;
	}
	
	public LocalDateTime getEnd() {
		return end;
	}
	
	public File getFile() {
		return filePath;
	}
	
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	// Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}
	
	// Methods
	public int size() {
		return events.size();
	}
	
	public boolean isEmpty() {
		return (size() == 0);
	}

	/**
	 * takes a LocalDateTime as input and recieves the year as an int
	 * @param date, LocalDateTime 
	 * @return int
	 */
	public int getYear(LocalDateTime date) {
		String str = date.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(str.charAt(0));
		sb.append(str.charAt(1));
		sb.append(str.charAt(2));
		sb.append(str.charAt(3));
		return Integer.parseInt(sb.toString());
	}
	/**
	 * takes a LocalDateTime as input and recieves the month as an int
	 * @param date, LocalDateTime 
	 * @return int
	 */
	public int getMonth(LocalDateTime date) {
		String str = date.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(str.charAt(5));
		sb.append(str.charAt(6));
		return Integer.parseInt(sb.toString());
	}
	/**
	 * takes a LocalDateTime as input and recieves the day as an int
	 * @param date, LocalDateTime 
	 * @return int
	 */
	public int getDay(LocalDateTime date) {
		String str = date.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(str.charAt(8));
		sb.append(str.charAt(9));
		return Integer.parseInt(sb.toString());
	}
	
	/**
	 * Adds event with a duration to event list
	 * @param eventName
	 * @param eventDescription
	 * @param start
	 * @param end
	 */
	public void addEventDuration(String eventName, String eventDescription, LocalDateTime start, LocalDateTime end) {
		events.add(new Event(eventName, eventDescription, start, end));
	}
	
	/**
	 * Adds event to event list
	 * @param eventName
	 * @param eventDescription
	 * @param start
	 */
	public void addEvent(String eventName, String eventDescription, LocalDateTime start) {
		events.add(new Event(eventName, eventDescription, start));
	}
	
	/**
	 * Removes event from event list
	 * @param event
	 */
	public void deleteEvent (Event event) {
		getEvents().remove(event);
	}
	/**
	 * Method to return the name of the Timeline 
	 * (is used by the ComboBox where timelines are selected)
	 */
	public String toString() {
		return name;
	}
	
	
}