package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import functions.App;
import functions.Event;
import functions.Timeline;
import io.FileHandler;

public class FileHandlerTest {
	private static App app;
	private static FileHandler fileHandler;
	
	private static Timeline timeline;
	
	//files for tests (empty one to write to and expected one to compare to)
	private static File test;
	private static File expected;
	
	//timeline input
	private final String EXPECTED_TIMELINE_NAME = "Timeline Test 1";
	private final LocalDateTime EXPECTED_TIMELINE_START = LocalDateTime.of(2001, 01, 01, 00, 00);
	private final LocalDateTime EXPECTED_TIMELINE_END = LocalDateTime.of(2002, 01, 01, 00, 00);
	
	//Event input
	private final String EXPECTED_EVENT_NAME1 = "Event Test 1";
	private final String EXPECTED_EVENT_DESCR1 = "This is first event";
	private final LocalDateTime EXPECTED_EVENT_START1 = LocalDateTime.of(2001, 03, 12, 05, 00) ;
	
	private final String EXPECTED_EVENT_NAME2 = "Event Test Duration";
	private final String EXPECTED_EVENT_DESCR2 = "This is second Event";
	private final LocalDateTime EXPECTED_EVENT_START2 = LocalDateTime.of(2001, 04, 12, 05, 00);
	private final LocalDateTime EXPECTED_EVENT_END2 = LocalDateTime.of(2001, 05, 12, 05, 00);
	
	@BeforeClass
	public static void beforeAllTests() {
		app = new App();
		fileHandler = new FileHandler();
		test = new File("src/test/Test.xml");
		expected = new File("src/test/TestExpected.xml");
		
	}
	
	@AfterClass
	public static void afterAllTests() {
		restoreXmlFile();
	}
	
	@Before
	public void setUp() throws Exception {
		timeline = new Timeline(EXPECTED_TIMELINE_NAME, EXPECTED_TIMELINE_START, EXPECTED_TIMELINE_END);
		timeline.addEvent(EXPECTED_EVENT_NAME1, EXPECTED_EVENT_DESCR1, EXPECTED_EVENT_START1);
		timeline.addEventDuration(EXPECTED_EVENT_NAME2, EXPECTED_EVENT_DESCR2, EXPECTED_EVENT_START2, EXPECTED_EVENT_END2);
		}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testSaveTimeline() {
		try {
			//save timeline to the test.xml file
			fileHandler.saveTimeline(timeline, test);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//check if test.xml is equal to expected.xml
		assertTrue(checkXmlFile());
	}
	
	@Test (expected = NullPointerException.class)
	public void testLoadTimeline() throws Exception {
		//loads the timeline from test.xml
		app.setCurrentTimeline(fileHandler.loadTimeline(test));
		
		//check if timeline is created correctly
		assertEquals(app.getCurrentTimeline().getName(), EXPECTED_TIMELINE_NAME);
		assertEquals(app.getCurrentTimeline().getStart(), EXPECTED_TIMELINE_START);
		assertEquals(app.getCurrentTimeline().getEnd(), EXPECTED_TIMELINE_END);
		
		//check if events are pressent
		for (Event e : app.getCurrentTimeline().getEvents()) {
			if (e.getEventName().equals(EXPECTED_EVENT_NAME1)) {
				assertEquals(e.getEventDescription(), EXPECTED_EVENT_DESCR1);
				assertEquals(e.getEventStart(), EXPECTED_EVENT_START1);
			}
			else {
				assertEquals(e.getEventName(), EXPECTED_EVENT_NAME2);
				assertEquals(e.getEventDescription(), EXPECTED_EVENT_DESCR2);
				assertEquals(e.getEventStart(), EXPECTED_EVENT_START2);
				assertEquals(e.getEventEnd(), EXPECTED_EVENT_END2);
			}
		}
	}
	
	/**
	 * help method to compare the saved xml-file with a file with expected result.
	 * @return true if they are equal else false.
	 */
	private boolean checkXmlFile() {
		Scanner testScanner = null;
		Scanner expectedScanner = null;
		try {
			testScanner = new Scanner(test);
			expectedScanner = new Scanner(expected);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		while (testScanner.hasNextLine() && expectedScanner.hasNextLine()) {
			
			if (!testScanner.nextLine().equals(expectedScanner.nextLine())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * help method to remove content of the test xml-file
	 */
	private static void restoreXmlFile() {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(test);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		printWriter.print("");
		printWriter.close();
	}

}
