package functions;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.LocalDateTimeXmlAdapter;

@XmlRootElement(name="Event")
@XmlType(propOrder = {"eventName", "description", "dateStart", "dateEnd"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {

	@XmlElement(name = "EventName")
	private String eventName;
	@XmlElement(name = "EventDescription")
	private String description;
	@XmlJavaTypeAdapter(value = LocalDateTimeXmlAdapter.class)
	@XmlElement(name = "EventStartDate")
	private LocalDateTime dateStart;
	@XmlJavaTypeAdapter(value = LocalDateTimeXmlAdapter.class)
	@XmlElement(name = "EventEndDate")
	private LocalDateTime dateEnd;
	@XmlTransient
	private boolean isDuration;

	/**
	 * Constructor, created an Event with duration
	 * @param eventName, name of the Event (String)
	 * @param eventDescription, description of the Event (String)
	 * @param start, start date of the Event (LocalDateTime)
	 * @param end, end date of the Event (LocalDateTime)
	 */
	public Event(String name, String eventDescription, LocalDateTime start, LocalDateTime end) {
		eventName = name;
		description = eventDescription;
		dateStart = start;
		dateEnd = end;
		isDuration = true;
	}
	/**
	 * Constructor, created an Event with duration
	 * @param eventName, name of the Event (String)
	 * @param eventDescription, description of the Event (String)
	 * @param start, start date of the Event (LocalDateTime)
	 */
	public Event(String name, String eventDescription, LocalDateTime start) {
		eventName = name;
		description = eventDescription;
		dateStart = start;
		dateEnd = null;
		isDuration = false;
		
	}
	public Event() {
		
	}
	
	/**
	 * gets the name of the Event
	 * @return String
	 */
	public String getEventName() {
		return eventName;
	}
	
	/**
	 * change the name of the Event
	 * @param eventName (String)
	 */
	public void setEventName(String name) {
		eventName = name;
	}
	/**
	 * gets the description of the Event
	 * @return String
	 */
	public String getEventDescription() {
		return description;
	}
	/**
	 * change the description of an Event
	 * @param eventDescription (String)
	 */
	public void setEventDescription(String eventDescription) {
		description = eventDescription;
	}
	
	/**
	 * gets the start date of the Event
	 * @return LocalDateTime
	 */
	
	public LocalDateTime getEventStart() {
		return dateStart;
	}
	/**
	 * change the start time of an Event
	 * @param start (LocalDateTime)
	 */
	public void setEventStart(LocalDateTime start) {
		dateStart = start;
	}
	
	/**
	 * gets the end date of the Event
	 * @return LocalDateTime
	 */
	
	public LocalDateTime getEventEnd() {
		return dateEnd;
	}
	
	/**
	 * change the end date of an Event
	 * @param end (LocalDateTime)
	 */
	public void setEventEnd(LocalDateTime end) {
		dateEnd = end;
	}
	/**
	 * checks if the current event is an event with duration or not
	 * @return boolean
	 */
	public boolean isDuration() {
		return (this.getEventEnd() != null);
	}
	
}