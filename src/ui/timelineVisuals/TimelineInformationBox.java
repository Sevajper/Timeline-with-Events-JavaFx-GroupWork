package ui.timelineVisuals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import functions.Event;
import functions.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimelineInformationBox extends HBox {
	
	private Text text;

	public TimelineInformationBox() {
		this.setAlignment(Pos.BOTTOM_LEFT);
		this.setPadding(new Insets(10));
	}
	
	/**
	 * sets information for year at format "(Timeline Name): (number of events) Events"
	 * @param events - ArrayList<Event>, events belonging to the yearview
	 * @param current - Timeline, current Timeline
	 * @param width - int, width of the year box
	 */
	public void setYearInformation(ArrayList<Event> events, Timeline current, int width) {
		getChildren().clear();
		setMaxSize(width, 50);
		setMinSize(width, 50);
		text = new Text();
		text.setFont(Font.font("monospace", 25));
		text.setText(current.getName()+": "+events.size()+" Events");
		getChildren().add(text);
	}
	
	/**
	 * sets information for month view at format "(Year): (number of events) Events"
	 * @param events - ArrayList<Event>, events belonging to the specific year
	 * @param width - int, width of the month box
	 * @param start - LocalDateTime, start date of the monthView 
	 */
	public void setMonthInformation(ArrayList<Event> events, int width, LocalDateTime start) {
		getChildren().clear();
		setMaxSize(width, 50);
		setMinSize(width, 50);
		text = new Text();
		text.setFont(Font.font("Times New Roman", 25));
		text.setText(start.getYear()+": "+events.size()+" Events");
		getChildren().add(text);
	}
	
	/**
	 * sets information for the day view at format "(month name): (number of events) Events"
	 * @param events - ArrayList<Event>, events belonging to the specific month
	 * @param width - int, width of the dayBox
	 * @param start - LocalDateTime, start date of the dayview
	 */
	public void setDayInformation(ArrayList<Event> events, int width, LocalDateTime start) {
		getChildren().clear();
		setMaxSize(width, 50);
		setMinSize(width, 50);
		text = new Text();
		text.setFont(Font.font("Times New Roman", 25));
		text.setText(start.getYear()+" "+start.getMonth()+": "+events.size()+" Events");
		getChildren().add(text);
	}
	
	
}
