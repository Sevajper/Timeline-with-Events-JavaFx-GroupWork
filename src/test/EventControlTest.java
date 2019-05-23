package test;

import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controls.ChangeListener;
import controls.EventControl;
import functions.App;
import functions.Timeline;

public class EventControlTest implements ChangeListener {
	
	private static App app;
	private static EventControl eventC;
	
	//Timeline input
	private final String EXPECTED_TIMELINE_NAME = "Timeline Test 1";
	private final LocalDateTime EXPECTED_TIMELINE_START = LocalDateTime.of(2001, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END = LocalDateTime.of(2002, 01, 01, 00, 00);
	
	//Event without duration input
	private final String EXPECTED_EVENT_NAME1 = "Event Test 1";
	private final String EXPECTED_EVENT_DESCR1 = "This is first event";
	private final LocalDateTime EXPECTED_EVENT_START1 = LocalDateTime.of(2001, 03, 12, 05, 00) ;
	
	private final String EXPECTED_EVENT_NAME2 = "Event Test 2";
	private final String EXPECTED_EVENT_DESCR2 = "This is second event";
	private final LocalDateTime EXPECTED_EVENT_START2 = LocalDateTime.of(2001, 04, 25, 05, 00) ;
	
	//Event with duration input
	private final String EXPECTED_EVENT_DUR_NAME1 = "Event Test Duration1";
	private final String EXPECTED_EVENT_DUR_DESCR1 = "This is first duration Event";
	private final LocalDateTime EXPECTED_EVENT_DUR_START1 = LocalDateTime.of(2001, 04, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_DUR_END1 = LocalDateTime.of(2001, 05, 12, 05, 00);
	
	private final String EXPECTED_EVENT_DUR_NAME2 = "Event Test Duration2";
	private final String EXPECTED_EVENT_DUR_DESCR2 = "This is second duration Event";
	private final LocalDateTime EXPECTED_EVENT_DUR_START2 = LocalDateTime.of(2001, 06, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_DUR_END2 = LocalDateTime.of(2001, 07, 12, 05, 00);

	private Timeline timeline;
	private boolean changeTimeline;
	private boolean newTimeline;
	private boolean editTimeline;
	private boolean editEvent;
	
	
	@BeforeClass
	public static void beforeAllTests() {
		app = new App();
		eventC = new EventControl();
		eventC.setApp(app);
	}
	
	@AfterClass
	public static void afterAllTests() {
		
	}
	
	@Before
	public void setUp() {
		app.addListener(this);
		app.addTimeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		
		//initialize ChangeListener booleans (to check if correct changelistener is called)
		newTimeline = false;
		changeTimeline = false;
		editTimeline = false;
		editEvent = false;
		
	}
	
	@Test 
	public void testonAddEventDuration() {
		//correct input
		assertEquals(app.getCurrentTimeline().size(), 0);
		assertTrue(eventC.onAddEventDuration(EXPECTED_EVENT_DUR_NAME1, EXPECTED_EVENT_DUR_DESCR1, EXPECTED_EVENT_DUR_START1, EXPECTED_EVENT_DUR_END1));
		//to assert that changelistener is called 
		assertTrue(editTimeline);
		editTimeline = false;
		//assert that Event is created correctly
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_DUR_NAME1);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DUR_DESCR1);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_DUR_START1);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventEnd(), EXPECTED_EVENT_DUR_END1);
		
		//incorrect input
		assertFalse(eventC.onAddEventDuration("", "", null, null));
		//assert that changelistener wasn't called
		assertFalse(editTimeline);
		//assert that no event has been added
		assertEquals(app.getCurrentTimeline().size(), 1);
	}
	
	
	@Test
	public void testonAddEvent(){
		//correct input
		assertTrue(eventC.onAddEvent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1));
		//Assure that changelistener has been called 
		assertTrue(editTimeline);
		editTimeline = false;
		//check if event was created correctly
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_NAME1);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DESCR1);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_START1);
		//incorect input
		assertFalse(eventC.onAddEvent("", "", null));
		//assure that changeListener wasn't called
		assertFalse(editTimeline);
	}
	
	
	@Test
	public void testonEditEvent() {
		//create event
		app.addEventToCurrent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		//assure that changelistener was called
		assertTrue(editTimeline);
		
		app.setCurrentEvent(app.getCurrentTimeline().getEvents().get(0));
		//Edit event with correct input
		assertTrue(eventC.onEditEvent(EXPECTED_EVENT_NAME2, EXPECTED_EVENT_DESCR2, EXPECTED_EVENT_START2));
		//assure that changelistener was called
		assertTrue(editEvent);
		editEvent = false;
		//check if event was edited correctly
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_NAME2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DESCR2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_START2);
		
		//test incorrect input
		assertFalse(eventC.onEditEvent("", "", null));
		//assure that changelistener wasn't called
		assertFalse(editEvent);
		//assure that event wasnt changed
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_NAME2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DESCR2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_START2);
		
	}
	
	
	@Test
	public void testonEditEventDuration() {
		//create event
		app.addEventToCurrent(EXPECTED_EVENT_DUR_NAME1, EXPECTED_EVENT_DUR_DESCR1, EXPECTED_EVENT_DUR_START1);
		app.setCurrentEvent(app.getCurrentTimeline().getEvents().get(0));
		//asure that changelistener was called
		assertTrue(editTimeline);
		
		//Edit event with correct input
		assertTrue(eventC.onEditEventDuration(EXPECTED_EVENT_DUR_NAME2, EXPECTED_EVENT_DUR_DESCR2, EXPECTED_EVENT_DUR_START2, EXPECTED_EVENT_DUR_END2));
		//assure that changelistener was called
		assertTrue(editEvent);
		editEvent = false;
		//check that event was edited correctly
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_DUR_NAME2 );
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DUR_DESCR2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_DUR_START2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventEnd(), EXPECTED_EVENT_DUR_END2);
	
		//edit event with incorrect input
		assertFalse(eventC.onEditEventDuration("", "", null, null));
		//assure changelistener wasn't called
		assertFalse(editEvent);
		//check that event hasn't been modified
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_DUR_NAME2 );
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DUR_DESCR2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_DUR_START2);
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventEnd(), EXPECTED_EVENT_DUR_END2);
	
	}
	
	@Test
	public void testOnDeleteEvent() {
		//add event to current timeline
		app.addEventToCurrent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		//assure that size is +1
		assertEquals(app.getCurrentTimeline().getEvents().size(), 1);
		
		//add one more event
		app.addEventToCurrent(EXPECTED_EVENT_NAME2, EXPECTED_EVENT_DESCR2, EXPECTED_EVENT_START2);
		//assure that size is +1
		assertEquals(app.getCurrentTimeline().getEvents().size(), 2);
		
		//set current event to the first event added
		app.setCurrentEvent(app.getCurrentTimeline().getEvents().get(0));
		//delete event
		assertTrue(eventC.onDeleteEvent());
		//assure that changelistener was called
		assertTrue(editTimeline);
		editTimeline = false;
		//make sure event list is -1
		assertEquals(app.getCurrentTimeline().getEvents().size(), 1);
		
		//check that the correct event was removed
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventName(), EXPECTED_EVENT_NAME2 );
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventDescription(), EXPECTED_EVENT_DESCR2 );
		assertEquals(app.getCurrentTimeline().getEvents().get(0).getEventStart(), EXPECTED_EVENT_START2 );
		
		//set current event
		app.setCurrentEvent(app.getCurrentTimeline().getEvents().get(0));
		//delete event
		assertTrue(eventC.onDeleteEvent());
		//assure changelistener was called
		assertTrue(editTimeline);
		editTimeline = false;
		//assure that the ArrayList of events now is empty
		assertEquals(app.getCurrentTimeline().getEvents().size(), 0);
		
		//try to remove non existing event
		assertFalse(eventC.onDeleteEvent());
		//assure that changelistener wasn't called
		assertFalse(editTimeline);
	}
	

	@Override
	public void onChangedTimeline(ArrayList<Timeline> timelines, Timeline current) {
		changeTimeline = true;
		
	}

	@Override
	public void onNewTimelineSelected(Timeline current) {
		newTimeline = true;
		
	}

	@Override
	public void onEditTimeline(Timeline current) {
		editTimeline = true;
		
	}

	@Override
	public void onEditEvent(Timeline current) {
		editEvent = true;
		
	}

	@Override
	public void onTimelineSaved(Timeline current) {
		// TODO Auto-generated method stub
		
	}

	
}