package ui.timelineVisuals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import functions.Event;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
/**
 * creates an circle (EventShape) that holds variables to the duration bar
 * and the Event it represents, also holds variable to the Timeline the
 * Event belongs to
 * @author carolinenilsson
 *
 */
public class EventShape extends Circle {

	private final Event event;

	private final Line bar;
	private double startX;
	private double endX;
	
	private final double BOX_WIDTH;
	private LocalDateTime timelineStart;


	public EventShape(Event e, LocalDateTime start, int width) {
		timelineStart = start;
		BOX_WIDTH = width;
		event = e;
		bar = new Line();
	}
	
	
	public void createYearEventShape() {
		setValueXYear();
		if (event.getEventEnd() == null) {
			setRadius(8);
			createEventShape();
		}
		else {
			setRadius(8);
			createEventDurationShape();
		}
	}
	
	public void createMonthEventShape() {
		setValueXMonth();
		if (event.getEventEnd() == null) {
			setRadius(12.5);
			createEventShape();
		}
		else {
			setRadius(12.5);
			createEventDurationShape();
		}
	}
	
	public void createDayEventShape() {
		setValueXDay();
		if (event.getEventEnd() == null) {
			setRadius(12.5);
			createEventShape();
		}
		else {
			setRadius(12.5);
			createEventDayBar();
		}
	}
	
	

	/**
	 * create a circle shape that is visuals for the Event without duration
	 */
	private void createEventShape() {
		setStroke(Color.BLACK);
		setFill(Color.web("rgb(108, 107, 153)"));
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-20.0);
		Lighting lighting = new Lighting();
		lighting.setDiffuseConstant(1.2);
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
		setEffect(lighting);
		setCenterX(startX);
		setCenterY(25);
		setManaged(false);
		bar.setVisible(false);
	}
	/**
	 * create a circle shape that is visuals for the Event with duration
	 */
	private void createEventDurationShape() {
		setStroke(Color.BLACK);
		setFill(Color.web("rgb(199, 144, 199)"));
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-20.0);
		Lighting lighting = new Lighting();
		lighting.setDiffuseConstant(1.2);
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
		setEffect(lighting);
		setCenterX(startX);
		setCenterY(25);
		setManaged(false);
		createDurationBar();
	}
	
	private void createEventDayBar() {
		setStroke(Color.BLACK);
		setFill(Color.web("rgb(199, 144, 199)"));
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-20.0);
		Lighting lighting = new Lighting();
		lighting.setDiffuseConstant(1.2);
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
		bar.setEffect(lighting);
		setCenterX(startX);
		setCenterY(25);
		setManaged(false);
		setVisible(false);
		updateBar();
		bar.setLayoutY(25);
		bar.setStrokeWidth(15.0);
		bar.setStrokeLineCap(StrokeLineCap.ROUND);
		bar.setStroke(Color.web("rgb(199, 144, 199)"));
		bar.setManaged(false);
		bar.setVisible(true);
	}
	
	
	/**
	 * Creates an duration bar for the Event
	 */
	private void createDurationBar() {
		updateBar();
		bar.setStrokeWidth(6.0);
		bar.setStrokeLineCap(StrokeLineCap.ROUND);
		bar.setStroke(Color.web("rgb(199, 144, 199)"));
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-20.0);
		Lighting lighting = new Lighting();
		lighting.setDiffuseConstant(1.2);
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
		bar.setEffect(lighting);
		bar.setVisible(false);
		bar.setManaged(false);
	}

	/**
	 * initializes the x-coordinate and y-coordinate value math explained below:
	 * year (into the timeline) multiplied by 12
	 * then add month date (ex. 05 for may) subtracted by 1
	 * (correct amount of months) multiplied by 100 (length of the monthBox)
	 * add (monthBox length divided by 30 (generaly days / month) 100/30 multiplied by days date)
	 * then add 5 multiplied by total number of months (this is due to spacing at 5.0 between months)
	 */
	private void setValueXYear() {
		int start = event.getEventStart().getYear()-timelineStart.getYear();
		if ((event.getEventEnd() == null)) {
			startX = (start*BOX_WIDTH + ((BOX_WIDTH/12)*event.getEventStart().getMonthValue())-((BOX_WIDTH/12)/2) + (2 * start));
		}
		else {
			int end = event.getEventEnd().getYear()-timelineStart.getYear();
			startX = (start*BOX_WIDTH + ((BOX_WIDTH/12)*event.getEventStart().getMonthValue())-((BOX_WIDTH/12)/2) + (2 * start));
			endX = (end*BOX_WIDTH + ((BOX_WIDTH/12)*event.getEventEnd().getMonthValue())-((BOX_WIDTH/12)/2) + (2 * end));

		}

	}
	private void setValueXMonth() {
		LocalDate startDate = event.getEventStart().toLocalDate();
		if (startDate.getYear() != timelineStart.getYear()) {
			startDate = startDate.withYear(timelineStart.getYear()).withMonth(01).withDayOfMonth(01);
		}
		int start = startDate.getMonthValue()-timelineStart.getMonthValue();
		
		
		if ((event.getEventEnd() == null)) {
			startX = (start*BOX_WIDTH + ((BOX_WIDTH/startDate.lengthOfMonth())*startDate.getDayOfMonth())-((BOX_WIDTH/startDate.lengthOfMonth())/2) + (2 * start));
		}
		else {
			LocalDate endDate = event.getEventEnd().toLocalDate();
			if (startDate.getYear() != endDate.getYear()) {
				endDate = startDate.withMonth(12).withDayOfMonth(31);
			}
			int end = endDate.getMonthValue()-timelineStart.getMonthValue();
			startX = (start*BOX_WIDTH + ((BOX_WIDTH/startDate.lengthOfMonth())*startDate.getDayOfMonth())-((BOX_WIDTH/startDate.lengthOfMonth())/2) + (2 * start));
			endX = (end*BOX_WIDTH + ((BOX_WIDTH/endDate.lengthOfMonth())*endDate.getDayOfMonth())-((BOX_WIDTH/endDate.lengthOfMonth())/2) + (2 * end));
			
		}
	}
	private  void setValueXDay() {
		startX = 0;
		endX = 0;
		LocalDate startDate = event.getEventStart().toLocalDate();
		if (startDate.getMonthValue() != timelineStart.getMonthValue() ||
				startDate.getYear() != timelineStart.getYear() ) {
			startDate = startDate.withMonth(timelineStart.getMonthValue()).withDayOfMonth(01);
			startX = (-BOX_WIDTH/2);
		}
		
		int start = startDate.getDayOfMonth()-timelineStart.getDayOfMonth();
		
		
		if ((event.getEventEnd() == null)) {
			startX = (start*BOX_WIDTH +  (2 * start)+ (BOX_WIDTH/2));
		}
		else {
			
			LocalDate endDate = event.getEventEnd().toLocalDate();

			if (timelineStart.getMonthValue()!= endDate.getMonthValue() || 
					timelineStart.getYear() != endDate.getYear()) {
				endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
				endX = (BOX_WIDTH/2);
			}
			
			int end = endDate.getDayOfMonth()-timelineStart.getDayOfMonth();
			startX = startX+ (start*BOX_WIDTH) +(BOX_WIDTH/2) + (2 * start);
			endX = endX+ (end*BOX_WIDTH +(BOX_WIDTH/2) + (2 * end));
		}
	}
	
	/**
	 * Sets new x-corrdiante and y-coordinates for start and end on bar
	 */
	private void updateBar() {
		bar.setStartX(startX);
		bar.setEndX(endX);
		
	}
	/**
	 * sets visibility, Boolean condition on duration bar
	 * (bar only shows when mouse hover the EventShape)
	 * @param b Boolean
	 */
	public void setBarVisibility(boolean b) {
		bar.setVisible(b);
	}
	
	
	/**
	 * Returns the bar of the EventShape
	 * @return Line
	 */
	public Line getBar() {
		return bar;
	}
	/**
	 * return the Event that the EventShape belongs to
	 * @return Event
	 */
	public Event getEvent() {
		return event;
	}
	

}
