package ui.timelineVisuals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import controls.ApplicationListener;
import functions.Event;
import functions.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import main.ApplicationMain;
import ui.EventView;

public class ShowEvents extends HBox {
	
	private ApplicationListener appListener;
	private EventView eventView;
	private TimelineInformationBox infoBox;
	
	private EventShape eventShape;
	private Popup eventName = new Popup();
	private final ArrayList<EventShape> shapeList = new ArrayList<EventShape>();
	
	
	public ShowEvents() {
		this.setPrefHeight(50);
	}
	
	public void setListenerAndView(ApplicationListener listener, EventView view) {
		appListener = listener;
		eventView = view;
	}
	public void setInformationBox(TimelineInformationBox tib) {
		infoBox = tib;
	}
	
	
	/**
	 * creates eventshapes and add them to the eventBox for the year view
	 * @param eventList - ArrayList<Event>
	 * @param boxWidth - int
	 * @param current - Timeline
	 */
	public void addYearEvents(ArrayList<Event> eventList, int boxWidth, Timeline timeline, int years) {
		getChildren().clear();
		infoBox.setYearInformation(eventList, timeline, boxWidth);
		createEventBox(boxWidth, years);
		shapeList.clear();
		for (Event event : eventList) {				
			eventShape = new EventShape(event, timeline.getStart(), boxWidth);
			eventShape.createYearEventShape();
			setOnMouseClick();
			setOnMouseHover();
			shapeList.add(eventShape);
			
			if (!(event.getEventEnd() == null)) {
				setOnMouseHover();
				getChildren().add(eventShape.getBar());
			}
		}
		addEvents();
		
	}
	/**
	 * creates eventshapes and add them to the eventBox for the month view
	 * @param eventList - ArrayList<Event>
	 * @param boxWidth - int
	 * @param start - LocalDateTime
	 */
	public void addMonthEvents(ArrayList<Event> eventList, int boxWidth, LocalDateTime start, int months) {
		getChildren().clear();
		
		infoBox.setMonthInformation(eventList, boxWidth, start);
		createEventBox(boxWidth, months);
		shapeList.clear();
		for (Event event : eventList) {				
			eventShape = new EventShape(event, start, boxWidth);
			eventShape.createMonthEventShape();
			setOnMouseClick();
			setOnMouseHover();
			shapeList.add(eventShape);
			
			if (!(event.getEventEnd() == null)) {
				setOnMouseHover();
				getChildren().add(eventShape.getBar());
			}
		}
		addEvents();
		
	}
	/**
	 * creates eventshapes and add them to the eventBox for the day view
	 * @param eventList - ArrayList<Event>
	 * @param boxWidth - int
	 * @param start - LocalDateTime
	 */
	public void addDayEvents(ArrayList<Event> eventList, int boxWidth, LocalDateTime start, int days) {
		getChildren().clear();
		
		infoBox.setDayInformation(eventList, boxWidth, start);
		createEventBox(boxWidth, days);
		shapeList.clear();
		for (Event event : eventList) {
			eventShape = new EventShape(event, start, boxWidth);
			eventShape.createDayEventShape();
			setOnMouseClick();
			setOnMouseHover();
			shapeList.add(eventShape);
			setBarOnMouseClick(eventShape);
			setBarOnMouseHover(eventShape);
			
		}
		addDayEvent();
		
	}

	/**
	 * help method to setOnMouseEntered & setOnMouseExited for eventshapes
	 */
	private void setOnMouseHover() {
		HBox nameBox = new HBox();
		nameBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(2.0), null)));
		nameBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
		eventShape.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				EventShape source = (EventShape) event.getSource();
				if (source.getEvent().isDuration()) {
					source.getBar().setVisible(true);
				}
				nameBox.getChildren().clear();
				eventName.getContent().clear();
				nameBox.getChildren().add(new Text(source.getEvent().getEventName()));
				eventName.getContent().add(nameBox);
				eventName.setX(source.localToScreen(source.getBoundsInLocal()).getMinX()+30);
				eventName.setY(source.localToScreen(source.getBoundsInLocal()).getMinY()+6);
				if (!eventView.getIsOpen()) {
					eventName.show(ApplicationMain.pS);
				}
			}

		});
		
		eventShape.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				EventShape source = (EventShape) event.getSource();
				source.getBar().setVisible(false);
				eventName.hide();
			}

		});
	}
	
	/**
	 * help method to setOnMouseClicked for eventshapes
	 */
	private void setOnMouseClick() {
		eventShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				eventName.hide();
				appListener.onNewEventSelected(((EventShape)event.getSource()).getEvent());
				eventView.ViewEventInfo(((EventShape)event.getSource()).getEvent());
			}

		});
	}
	
	/**
	 * helpmethod to setOmMouseClicked for eventBar (only for day view)
	 * @param eventShape - EventShape
	 */
	private void setBarOnMouseClick(EventShape eventShape) {
		EventShape shape = eventShape;
		eventShape.getBar().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				eventName.hide();
				appListener.onNewEventSelected(shape.getEvent());
				eventView.ViewEventInfo(shape.getEvent());

			}

		});
	}
	
	private void setBarOnMouseHover(EventShape eventShape) {
		EventShape shape = eventShape;
		eventName = new Popup();
		HBox nameBox = new HBox();
		nameBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(2.0), null)));
		nameBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
		eventShape.getBar().setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				nameBox.getChildren().clear();
				eventName.getContent().clear();
				nameBox.getChildren().add(new Text(shape.getEvent().getEventName()));
				eventName.getContent().add(nameBox);
				eventName.setX(event.getScreenX()+ 15);
				eventName.setY(shape.getBar().localToScreen(shape.getBar().getBoundsInLocal()).getMinY());
				if (!eventView.getIsOpen()) {
					eventName.show(ApplicationMain.pS);
				}
				
			}

		});
		eventShape.getBar().setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				eventName.hide();
			}

		});
	}
	
	/**
	 * help method to add events, event bars and event lines to the eventBox 
	 */
	private void addEvents() {
		sortEventList();
		setAllignmentEvents();
		for (Circle circle : shapeList) {
			Line line = new Line();
			line.setStartX(circle.getCenterX());
			line.setStartY(0);
			line.setEndX(circle.getCenterX());
			line.setEndY(circle.getCenterY() - circle.getRadius());
			line.setManaged(false);
			getChildren().add(line);
		}
		for (EventShape circle : shapeList) {
			getChildren().add(circle);
		}
	}
	
	/**
	 * help method to align the events in the eventBox
	 */
	private void setAllignmentEvents() {
		int alignment;
		int shapeSize;
		if (shapeList.size() > 0) {
			shapeSize = (int)(shapeList.get(0).getRadius()*2+1);
			alignment = (int)(shapeList.get(0).getRadius()*2+1);
			shapeList.get(0).setCenterY(alignment);
		}
		else {
			shapeSize = 26;
			alignment = 25;
		}
		int firstCircle;
		int secondCircle;

		for (int index = 0; index < shapeList.size() - 1; index++) {
			firstCircle = (int) shapeList.get(index).getCenterX();
			secondCircle = (int) shapeList.get(1 + index).getCenterX();
			if (firstCircle > secondCircle || (firstCircle - secondCircle < shapeSize && firstCircle - secondCircle > -shapeSize)) {
				alignment = alignment + shapeSize+2;
				setPrefHeight(alignment+50);
			}
			shapeList.get(index + 1).setCenterY(alignment);
		}
	}
	
	/**
	 * help method to sort eventShapes
	 */
	private void sortEventList() {
		Comparator<EventShape> compare = new Comparator<EventShape>() {

			@Override
			public int compare(EventShape o1, EventShape o2) {
				return (int) (o1.getCenterX() - o2.getCenterX());
			}

		};

		Collections.sort(shapeList, compare);

		int counter;
		int firstShape;
		int secondShape;
		for (int index = 0; index < shapeList.size() - 1; index++) {
			counter = index;
			firstShape = (int) shapeList.get(index).getCenterX();
			secondShape = (int) shapeList.get(1 + index).getCenterX();
			while (firstShape - secondShape < 26 && shapeList.size() - 1 > counter && firstShape - secondShape > -26) {
				counter++;
				EventShape temp = shapeList.get(index + 1);
				shapeList.remove(index + 1);
				shapeList.add(temp);
				secondShape = (int) shapeList.get(1 + index).getCenterX();

			}
		}

	}
	
	/**
	 * help method to add DayEvents (bars for duration and eventShape for non-duration)
	 */
	private void addDayEvent() {
		
		sortEventList();
		alignDayEvents();
		
		for (EventShape shape : shapeList) {
			if (!(shape.getBar().isVisible())) {
				
				getChildren().add(shape);
			}
			else {
				getChildren().add(shape.getBar());
			}
		}
		
	}
	
	/**
	 * help method to align dayEvents
	 */
	private void alignDayEvents() {
		int alignment = 25;
		int startFirst;
		int endFirst;
		int startSecond;
		int endSecond;
		
		for (int index = 0; index < shapeList.size() - 1; index++) {
			EventShape first = shapeList.get(index);
			EventShape second = shapeList.get(index+1);
			if (first.getBar().isVisible()) {
				startFirst = (int)first.getBar().getStartX();
				endFirst = (int) first.getBar().getEndX();
			}
			else {
				startFirst = (int) first.getCenterX()-13;
				endFirst = (int) first.getCenterX()+13;
			}
			if (second.getBar().isVisible()) {
				startSecond = (int) second.getBar().getStartX();
				endSecond = (int) second.getBar().getEndX();
			}
			else {
				startSecond = (int) second.getCenterX()-13;
				endSecond = (int) second.getCenterX()+13;
			}
			
			if (endFirst > startSecond && endSecond > startFirst || startFirst < endSecond && startSecond < endFirst) {
				alignment = alignment +25;
				setPrefHeight(alignment+50);
			}
			
			
			shapeList.get(index + 1).getBar().setLayoutY(alignment);
			shapeList.get(index+1).setCenterY(alignment);
		}
	}
	
	/**
	 * help method to create the eventBox 
	 * @param width - int, width of the year/month/day boxes
	 * @param daysMonthsOrYears - int, number of years/months/days
	 */
	private void createEventBox(int width, int daysMonthsOrYears) {
		getChildren().clear();
		setBackground( new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		long boxLength = ((daysMonthsOrYears) * width) + ((daysMonthsOrYears-1) * 2 );
		setMaxWidth(boxLength);
		setMinWidth(boxLength);
		for (int i = 0; i <= daysMonthsOrYears ; i++) {
			Line line = new Line();
			line.setStroke(Color.WHITESMOKE);
			line.setStrokeWidth(2);
			line.setStartX(width*i +(2*(i-1)) + 1);
			line.setEndX(width*i+(2*(i-1)) + 1);
			line.setStartY(0);
			line.setEndY(2000);
			line.setManaged(false);
			getChildren().add(line);
		}
	}	
}
