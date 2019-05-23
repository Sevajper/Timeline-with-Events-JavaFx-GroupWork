package test;

import static org.junit.Assert.*;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import functions.Timeline;

public class TimelineTest {
	
	Timeline timeline;
	
	//timeline input
	private final String EXPECTED_TIMELINE_NAME = "Timeline Test 1";
	private final LocalDateTime EXPECTED_TIMELINE_START = LocalDateTime.of(2001, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END = LocalDateTime.of(2002, 01, 01, 00, 00);
	
	private final String EXPECTED_TIMELINE_NAME2 = "Timeline Test 2";
	private final LocalDateTime EXPECTED_TIMELINE_START2 = LocalDateTime.of(2000, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END2 = LocalDateTime.of(2010, 01, 01, 00, 00);
	
	//Event without duration input
	private final String EXPECTED_EVENT_NAME1 = "Event Test 1";
	private final String EXPECTED_EVENT_DESCR1 = "This is first event";
	private final LocalDateTime EXPECTED_EVENT_START1 = LocalDateTime.of(2001, 03, 12, 05, 00) ;
		
	//Event with duration input
	private final String EXPECTED_EVENT_DUR_NAME1 = "Event Test Duration1";
	private final String EXPECTED_EVENT_DUR_DESCR1 = "This is first duration Event";
	private final LocalDateTime EXPECTED_EVENT_DUR_START1 = LocalDateTime.of(2001, 04, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_DUR_END1 = LocalDateTime.of(2001, 05, 12, 05, 00);
		
	
	@Before
	public void setUp() {
		
	}
	
	@Test 
	public void testTimeline() {
		//create a timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//check that name, start and end is correct
		assertEquals(timeline.getName(), EXPECTED_TIMELINE_NAME);
		assertEquals(timeline.getStart(), EXPECTED_TIMELINE_START);
		assertEquals(timeline.getEnd(), EXPECTED_TIMELINE_END);
		
	} 


	@Test
	public void testSetName() {
		//create timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//change name
		timeline.setName(EXPECTED_TIMELINE_NAME2);
		//check that name is correct
		assertEquals(timeline.getName(), EXPECTED_TIMELINE_NAME2);
	}

	@Test
	public void testSetStart() {
		//create timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//change start
		timeline.setStart(EXPECTED_TIMELINE_START2);
		//check that start is correct
		assertEquals(timeline.getStart(), EXPECTED_TIMELINE_START2);
	}

	@Test
	public void testSetEnd() {
		//create timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//change end
		timeline.setEnd(EXPECTED_TIMELINE_END2);
		//check that end is correct
		assertEquals(timeline.getEnd(), EXPECTED_TIMELINE_END2);
	}

	@Test
	public void testAddEventDuration() {
		//create timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//check that event ArrayList is empty
		assertEquals(timeline.getEvents().size(), 0);
		//add a duration event
		timeline.addEventDuration(EXPECTED_EVENT_DUR_NAME1, EXPECTED_EVENT_DUR_DESCR1, EXPECTED_EVENT_DUR_START1, EXPECTED_EVENT_DUR_END1);
		//check if list is +1
		assertEquals(timeline.getEvents().size(), 1);
		//add another duration event
		timeline.addEventDuration(EXPECTED_EVENT_DUR_NAME1, EXPECTED_EVENT_DUR_DESCR1, EXPECTED_EVENT_DUR_START1, EXPECTED_EVENT_DUR_END1);
		//check if list is +1
		assertEquals(timeline.getEvents().size(), 2);
	}

	@Test
	public void testAddEvent() {
		//create timeline
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//check that ArrayList is empty
		assertEquals(timeline.getEvents().size(), 0);
		//add event without duration
		timeline.addEvent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		//check if list is +1
		assertEquals(timeline.getEvents().size(), 1);
		//add another event
		timeline.addEvent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		//check if list is +1
		assertEquals(timeline.getEvents().size(), 2);
		 
	}
}
		