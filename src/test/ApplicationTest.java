package test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controls.ChangeListener;
import functions.App;
import functions.Timeline;

import ui.ApplicationView;


public class ApplicationTest implements ChangeListener {
	

	private static App app;
	@SuppressWarnings("unused")
	private static ApplicationView appView;
	
	//Timeline input
	private final String EXPECTED_TIMELINE_NAME = "Timeline Test 1";
	private final LocalDateTime EXPECTED_TIMELINE_START = LocalDateTime.of(2001, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END = LocalDateTime.of(2002, 01, 01, 00, 00);
	
	private final String EXPECTED_TIMELINE_NAME2 = "Timeline Test 2";
	private final LocalDateTime EXPECTED_TIMELINE_START2 = LocalDateTime.of(2003, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END2 = LocalDateTime.of(2005, 01, 01, 00, 00);
	
	//Event input
	private final String EXPECTED_EVENT_NAME1 = "Event Test 1";
	private final String EXPECTED_EVENT_DESCR1 = "This is first event";
	private final LocalDateTime EXPECTED_EVENT_START1 = LocalDateTime.of(2001, 03, 12, 05, 00) ;
	
	private final String EXPECTED_EVENT_NAME2 = "Event Test Duration";
	private final String EXPECTED_EVENT_DESCR2 = "This is second Event";
	private final LocalDateTime EXPECTED_EVENT_START2 = LocalDateTime.of(2001, 04, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_END2 = LocalDateTime.of(2001, 05, 12, 05, 00);
	
	
	private Timeline timeline;
	//to check if changelistener was called
	private boolean changeTimeline;
	private boolean newTimeline;
	private boolean editTimeline;
	@SuppressWarnings("unused")
	private boolean editEvent;
	
	@BeforeClass
	public static void beforeAllTests() {
		app = new App();
		
	}
	
	@AfterClass
	public static void afterAllTests() {
		
	}
	
	@Before
	public void setUp() throws Exception {
		app.addListener(this);
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		newTimeline = false;
		changeTimeline = false;
		editTimeline = false;
		editEvent = false;
	}
	

	@Test
	public void testAddTimeline() {
		//correct input
		app.addTimeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		//check if timeline was added correctly
		assertEquals(app.getCurrentTimeline().getName(), EXPECTED_TIMELINE_NAME);
		assertEquals(app.getCurrentTimeline().getStart(), EXPECTED_TIMELINE_START);
		assertEquals(app.getCurrentTimeline().getEnd(), EXPECTED_TIMELINE_END);
		//assure that changelistener was called
		assertTrue(changeTimeline);
	}
	
	@Test
	public void testAddEventToCurrent() {
		//set current timeline
		app.setCurrentTimeline(timeline);
		//assure that changelistener was called
		assertTrue(newTimeline);
		//add event with correct input
		app.addEventToCurrent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		assertEquals(app.getCurrentTimeline().size(), 1);
		//assure that changelistener was called
		assertTrue(editTimeline);
		
	}
	
	@Test
	public void addEventToCurrentDuration() {
		//set current timeline
		app.setCurrentTimeline(timeline);
		//assure that changelistener was called 
		assertTrue(newTimeline);
		//add event with duration with correct input
		app.addEventToCurrentDuration(EXPECTED_EVENT_NAME2, EXPECTED_EVENT_DESCR2, EXPECTED_EVENT_START2, EXPECTED_EVENT_END2);
		assertEquals(app.getCurrentTimeline().size(), 1);
		//assure that changelistener was called
		assertTrue(editTimeline);
	}
	
	@Test
	public void testSetCurrentTimeline() {
		app.setCurrentTimeline(timeline);
		assertEquals(app.getCurrentTimeline(), timeline);
		assertTrue(newTimeline);
	}
	
	@Test
	public void testGetTimelines() {
		assertEquals(app.getTimelines().size(), 1);
		app.addTimeline(EXPECTED_TIMELINE_NAME2, EXPECTED_TIMELINE_START2, EXPECTED_TIMELINE_END2);
		
		assertTrue(changeTimeline);
		assertEquals(app.getTimelines().size(), 2);
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
		
	}

	

}
