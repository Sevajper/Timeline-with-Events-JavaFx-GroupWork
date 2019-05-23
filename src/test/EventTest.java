package test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import functions.Event;

public class EventTest {

	Event event;
	Event eventDur;
	
	// Event without duration input
	private final String EXPECTED_EVENT_NAME1 = "Event Test 1";
	private final String EXPECTED_EVENT_DESCR1 = "This is first event";
	private final LocalDateTime EXPECTED_EVENT_START1 = LocalDateTime.of(2001, 03, 12, 05, 00);

	private final String EXPECTED_EVENT_NAME2 = "Event Test 2";
	private final String EXPECTED_EVENT_DESCR2 = "This is second event";
	private final LocalDateTime EXPECTED_EVENT_START2 = LocalDateTime.of(2001, 04, 25, 05, 00);

	// Event with duration input
	private final String EXPECTED_EVENT_DUR_NAME1 = "Event Test Duration1";
	private final String EXPECTED_EVENT_DUR_DESCR1 = "This is first duration Event";
	private final LocalDateTime EXPECTED_EVENT_DUR_START1 = LocalDateTime.of(2001, 04, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_DUR_END1 = LocalDateTime.of(2001, 05, 12, 05, 00);

	private final String EXPECTED_EVENT_DUR_NAME2 = "Event Test Duration2";
	private final String EXPECTED_EVENT_DUR_DESCR2 = "This is second duration Event";
	private final LocalDateTime EXPECTED_EVENT_DUR_START2 = LocalDateTime.of(2001, 06, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_DUR_END2 = LocalDateTime.of(2001, 07, 12, 05, 00);

	@Before
	public void setUp() {
		//create event without duration
		event = new Event(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		//create event with duration
		eventDur = new Event(EXPECTED_EVENT_DUR_NAME1, EXPECTED_EVENT_DUR_DESCR1, EXPECTED_EVENT_DUR_START1, EXPECTED_EVENT_DUR_END1);
		
	}

	@Test
	public void testEvent() {
		//check if event got correct input while created with constructor (non-duration)
		assertEquals(event.getEventName(), EXPECTED_EVENT_NAME1);
		assertEquals(event.getEventDescription(), EXPECTED_EVENT_DESCR1);
		assertEquals(event.getEventStart(), EXPECTED_EVENT_START1);

		//check if event got correct input while created with constructor (duration)
		assertEquals(eventDur.getEventName(), EXPECTED_EVENT_DUR_NAME1);
		assertEquals(eventDur.getEventDescription(), EXPECTED_EVENT_DUR_DESCR1);
		assertEquals(eventDur.getEventStart(), EXPECTED_EVENT_DUR_START1);
		assertEquals(eventDur.getEventEnd(), EXPECTED_EVENT_DUR_END1);

	}


	@Test
	public void testSetEventName() {
		//test to change event name for both duration and non-duration event
		event.setEventName(EXPECTED_EVENT_NAME2);
		assertEquals(event.getEventName(), EXPECTED_EVENT_NAME2);
		
		eventDur.setEventName(EXPECTED_EVENT_DUR_NAME2);
		assertEquals(eventDur.getEventName(), EXPECTED_EVENT_DUR_NAME2);

	}


	@Test
	public void testSetEventDescription() {
		//test to change event description for both duration and non-duration event
		event.setEventDescription(EXPECTED_EVENT_DESCR2);
		assertEquals(event.getEventDescription(), EXPECTED_EVENT_DESCR2);

		eventDur.setEventDescription(EXPECTED_EVENT_DUR_DESCR2);
		assertEquals(eventDur.getEventDescription(), EXPECTED_EVENT_DUR_DESCR2);

	}


	@Test
	public void testSetEventStart() {
		//test to change event start for both duration and non-duration event
		event.setEventStart(EXPECTED_EVENT_START2);
		assertEquals(event.getEventStart(), EXPECTED_EVENT_START2);

		eventDur.setEventStart(EXPECTED_EVENT_DUR_START2);
		assertEquals(eventDur.getEventStart(), EXPECTED_EVENT_DUR_START2);

	}


	@Test
	public void testSetEventEnd() {
		//test to change event end (for duration)
		eventDur.setEventEnd(EXPECTED_EVENT_DUR_END2);
		assertEquals(eventDur.getEventEnd(), EXPECTED_EVENT_DUR_END2);

	}

}
