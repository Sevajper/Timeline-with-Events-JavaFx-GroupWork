package ui;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import controls.EventListener;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import functions.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.ApplicationMain;

public class EventView {

	private EventListener eventListener;

	// TextArea - Add Event Window
	private JFXTextArea description;

	// TextFields - Add Event Window
	private JFXTextField name;

	// Buttons
	private JFXButton addEvent = new JFXButton("",
			new Label("", AwesomeDude.createIconLabel(AwesomeIcon.CALENDAR, "20")));
	private JFXButton ok = new JFXButton("Finish");
	private JFXButton finishEdit = new JFXButton("Finish");
	private JFXButton cancel = new JFXButton("Cancel");
	private JFXButton editEvent = new JFXButton("Edit Info");
	private JFXButton delete = new JFXButton("Delete event");

	// Texts
	private Text titleText;
	private Text decText;
	private Text dateStartText;
	private Text dateEndText;

	// Combo boxes
	private JFXTimePicker JtimeStart = new JFXTimePicker();
	// private ComboBox<String> timeStart = new ComboBox<String>();
	private JFXTimePicker JtimeEnd = new JFXTimePicker();
	// private ComboBox<String> timeEnd = new ComboBox<String>();

	// Labels - View Event Window
	private Label title = new Label("Title:");
	private Label eventStart = new Label("Event start:");
	private Label eventEnd = new Label("Event end:");
	private Label des = new Label("Description:");

	// Other
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy (GG)  HH:mm");
	private JFXDatePicker checkInDatePickerStart;
	private JFXDatePicker checkInDatePickerEnd;
	private final Converter converter = new Converter();

	private LocalDate timelineStart;
	private LocalDate timelineEnd;
	private final int nameMAX_CHARS = 30;
	private final int desMAX_CHARS = 300;
	private boolean isOpen = false;
	
	private BorderPane mainView;
	/**
	 * Update the EventListener variable with the EventListener given as input
	 *
	 * @param eventList,
	 *            (EventListener)
	 */
	public void addListener(EventListener eventList) {
		eventListener = eventList;
	}

	public void setTimelineStartEnd(LocalDate start, LocalDate end) {
		timelineStart = start;
		timelineEnd = end;
	}
	
	public boolean getIsOpen() {
		return isOpen;
	}

	/**
	 * method to create and return the add Event button,
	 *
	 * @return addEvent button
	 */

	public JFXButton getAddEventButton() {
		addEvent.setMaxSize(40, 40);
		addEvent.setMinSize(40, 40);
		addEvent.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		addEvent.setRipplerFill(Color.web("rgb(87,56,97)"));
		addEvent.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));

		/*
		 * when Add Event button is clicked a popup window is created where the
		 * user can provide information about the Event, User has to provide
		 * Name, Description and Start Date, End Date however is optional.
		 */
		addEvent.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				mainView.requestFocus();
				final Stage eventWindow = new Stage();
				VBox textFieldsStart = createAddEventWindow();
				eventWindow.setTitle("Add event window");
				eventWindow.setResizable(false);
				
				// Reset all fields
				name.clear();
				JtimeStart.setValue(null);
				JtimeEnd.setValue(null);
				datePickerSettings(checkInDatePickerStart);
				checkInDatePickerStart.setValue(timelineStart.minusDays(1));
				datePickerSettings(checkInDatePickerEnd);
				checkInDatePickerEnd.setValue(timelineStart.minusDays(1));

				/*
				 * When "Ok" button is clicked, textfields are fetched and
				 * converted to String and LocalDateTime then application
				 * listener is calling the onAddEvent-method
				 */
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						/*
						 * If Name, Description or Start Date fields are empty
						 * an Error alert shows to the user
						 */
						if (isNeededFieldEmpty()) {
							createAlertError("Empty fields", "Name, description and start Date can't be empty!");
						} // End of alert for empty fields
						else {
							LocalDateTime startTime = LocalDateTime.of(checkInDatePickerStart.getValue(),
									LocalTime.parse(JtimeStart.getValue() + ":00"));
							String eventname = name.getText();
							String eventdescrip = description.getText();
							/*
							 * If the event doesn't have End Date an non
							 * duration Event is created
							 */
							if (isNotDurationEvent()) {
								// Create event
								if (eventListener.onAddEvent(eventname, eventdescrip, startTime)) {
									eventWindow.close();
								} // End for successfully creating non duration
									// event

								// Check if event is out of timeline
								else {
									createAlertError("Error in chosing time",
											"It appears your are trying to create an event outside of timeline!");
								} // End of alert for out of timeline event
							} // End of creating non duration event

							/*
							 * If the Event has End Time an Event with duration
							 * is created
							 */
							else {
								// Event is with duration, end time is created
								LocalDateTime endTime = LocalDateTime.of(checkInDatePickerEnd.getValue(),
										LocalTime.parse(JtimeEnd.getValue() + ":00"));
								// Check if start time is later than end time
								if (startTime.compareTo(endTime) > 0) {
									createAlertError("Error in event dates",
											"Start date has to be earlier than end date!");
								} // End of checking if start date is earlier
									// than end date

								// Event has correct start and end date, create
								// event with duration
								else {
									if (eventListener.onAddEventDuration(eventname, eventdescrip, startTime, endTime)) {
										eventWindow.close();
									} // End of successfully creating event with
										// duration
									else {
										createAlertError("Error in chosing time",
												"It appears your are trying to create an event outside of timeline!");
									} // End of alert for event out of timeline
								} // End of successfully creating event with
									// duration
							} // End of creating event with duration
						} // End of creating event
					} // End of handle() method
				}); // End of setOnAction for addEvent button

				/*
				 * when cancel button is clicked the popup window is closed
				 */
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						eventWindow.close();
					}
				});

				/*
				 * Creates a Scene and builds the Add Event popup window
				 */
				Scene eventScene = new Scene(textFieldsStart);
				eventWindow.setScene(eventScene);
				eventWindow.setAlwaysOnTop(true);
				eventWindow.initModality(Modality.APPLICATION_MODAL);
				eventWindow.showAndWait();
				textFieldsStart.requestFocus();

			}
		});
		return addEvent;

	}

	/**
	 * Method to disable addEvent when no timelines are loaded
	 * 
	 * @param notShown
	 *            true if button should be disabled
	 */
	public void setDisable(boolean notShown) {
		addEvent.setDisable(notShown);
	}

	/**
	 * Creates and returns Edit Event button and it's functionalities
	 * 
	 * @param e
	 *            event to be edited
	 * @return editEvent button
	 */
	public JFXButton EditButton(Event e) {
		// Button parameters
		editEvent.setMinSize(80, 30);
		editEvent.setFont(Font.font("Verdana", 15));
		editEvent.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		editEvent.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		editEvent.setRipplerFill(Color.web("rgb(87,56,97)"));
		editEvent.setTranslateX(10);
		editEvent.setTranslateY(30);
		editEvent.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				setDisableFields(false);

				finishEdit.setOnAction(new EventHandler<ActionEvent>() {
					

					@Override
					public void handle(ActionEvent event) {
						if (checkInDatePickerEnd.getValue() != null && checkInDatePickerEnd.getValue().compareTo(timelineStart)<0) {
							checkInDatePickerEnd.setValue(null);
						}
						if (checkInDatePickerStart.getValue() != null &&checkInDatePickerStart.getValue().compareTo(timelineStart)<0) {
							checkInDatePickerStart.setValue(null);
						}
						/*
						 * Checking for empty fields or unfinished date choosing
						 * example, user picked end time, but not end date.
						 */
						if (isNeededFieldEmpty()) {
							createAlertError("Empty fields", "Name, description and start Date can't be empty!");
						} // End of checking if fields are empty

						/*
						 * If all fields were not empty, event editing is
						 * possible
						 */
						else {
							LocalDateTime startTime = LocalDateTime.of(checkInDatePickerStart.getValue(),
									LocalTime.parse(JtimeStart.getValue() + ":00"));
							String eventname = name.getText();
							String eventdescrip = description.getText();

							// Event is not a duration event, it's not attempted
							// to be converted, then
							// edit old event
							if ((!e.isDuration() && checkInDatePickerEnd.getValue() == null)
									|| (e.isDuration() && checkInDatePickerEnd.getValue() == null)) {
								editEvent(eventname, eventdescrip, startTime);

							} // End of editing of event from non duration to
								// non duration
							
							else if ((!e.isDuration() && checkInDatePickerEnd.getValue() != null)
									|| (e.isDuration() && checkInDatePickerEnd.getValue() != null)) {
								LocalDateTime endTime = LocalDateTime.of(checkInDatePickerEnd.getValue(),
										LocalTime.parse(JtimeEnd.getValue() + ":00"));

								if (startTime.compareTo(endTime) > 0) {
									createAlertError("Error in event dates",
											"Start date has to be earlier than end date!");
								} // End of alert for start date later than end
									// date for event

								else {
									editDurationEvent(eventname, eventdescrip, startTime, endTime);
								} // End of checking end time
							} // End of edit event from non duration to duration

						} // End of editing events
					} // End of event editing
						// End of handle() method
				}); // End of setOnAction method
				/*
				 * when cancel button is clicked the popup window is closed
				 */
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						setDisableFields(true);
					} // End of handle() method for cancel
				}); // End of setOnAction for cancel button
			} // End of handle() for editEvent button
		}); // End of setOnAction for editEvent button

		return editEvent;
	}

	/**
	 * Delete button responsible for delete selected event
	 * 
	 * @param e
	 *            event to be deleted
	 * @param s
	 *            closes event information window of delete event
	 * @return button with set action on it.
	 */
	public JFXButton getDeleteButton(Event e, Stage s) {
		delete.setMinSize(80, 30);
		delete.setFont(Font.font("Verdana", 15));
		delete.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		delete.setRipplerFill(Color.web("rgb(87,56,97)"));
		delete.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));

		delete.setTranslateX(110);
		delete.setTranslateY(-20);
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Opens alert to ask if user really wants to delete
				// the event
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("Deleting event");
				confirmation.setContentText("Are you sure you want to delete this event?");
				Optional<ButtonType> result = confirmation.showAndWait();
				// if user chose ok, event is deleted, information
				// window is closed as it is not needed
				if (result.get() == ButtonType.OK) {
					if (eventListener.onDeleteEvent()) {
						confirmation.close();
						s.close();
					}
				}
				// user chose cancel, so alert window is closed
				else {
					confirmation.close();
				}

			}

		});
		return delete;
	}

	/**
	 * Help method to create popup window, initializes all TextFields, Labels
	 * and Buttons for the window, and then add it all to the GridPane
	 *
	 * @return GridPane
	 */

	private VBox createAddEventWindow() {
		initializeFields();
		initializeButtons();
		VBox addEventWindow = new VBox();
		GridPane dateTime = new GridPane();
		VBox nameDescription = new VBox();
		HBox okCancelButtons = new HBox();

		// In case fields are disabled
		setDisableFields(false);

		/* Limit the number of characters */
		
		name.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= nameMAX_CHARS ? change : null));
		
		description.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= desMAX_CHARS ? change : null));

		checkInDatePickerStart.setPromptText("Event start date");
		checkInDatePickerEnd.setPromptText("Event end date");
		JtimeStart.setPromptText("Event start time");
		JtimeEnd.setPromptText("Event end time");

		ok.setPrefSize(100, 35);
		ok.setFont(Font.font(20));
		cancel.setPrefSize(100, 35);
		cancel.setFont(Font.font(20));

		nameDescription.setPadding(new Insets(10, 0, 20, 0));
		nameDescription.setSpacing(10);
		nameDescription.getChildren().addAll(name, description);

		dateTime.setVgap(20);
		dateTime.setHgap(10);
		dateTime.add(checkInDatePickerStart, 0, 0);
		dateTime.add(JtimeStart, 1, 0);
		dateTime.add(checkInDatePickerEnd, 0, 1);
		dateTime.add(JtimeEnd, 1, 1);

		okCancelButtons.setSpacing(100);
		okCancelButtons.setPadding(new Insets(20, 0, 20, 0));
		okCancelButtons.setAlignment(Pos.CENTER);
		okCancelButtons.getChildren().addAll(ok, cancel);

		addEventWindow.setPadding(new Insets(10));
		addEventWindow.setSpacing(20);
		addEventWindow.setAlignment(Pos.CENTER);
		addEventWindow.getChildren().addAll(nameDescription, dateTime, okCancelButtons);

		return addEventWindow;
	}

	/**
	 * Creates a window that displays information about certain event
	 * 
	 * @param e
	 *            event that information is displayed about
	 */
	public void ViewEventInfo(Event e) {
		final Stage eventWindow = new Stage();
		initializeFields();
		initializeButtons();
		ScrollPane editEventWindow = new ScrollPane();
		String formattedStringS = e.getEventStart().format(format);

		GridPane information = new GridPane();
		information.setPadding(new Insets(10, 10, 20, 30));
		information.setVgap(15);

		VBox window = new VBox();
		window.setSpacing(20);

		Label info = new Label("Information");
		info.setFont(Font.font("Verdana", 25));
		info.setUnderline(true);
		info.setTextFill(Color.BLACK);
		info.setAlignment(Pos.CENTER);

		title.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
		titleText = new Text("  " + e.getEventName());
		titleText.setFont(Font.font("Verdana", 15));
		titleText.setWrappingWidth(250);

		eventStart.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
		dateStartText = new Text(formattedStringS);
		dateStartText.setFont(Font.font("Verdana", 15));
		dateStartText.setWrappingWidth(250);

		des.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
		decText = new Text(e.getEventDescription());
		decText.setWrappingWidth(250);
		decText.setFont(Font.font("Verdana", 15));

		dateEndText = new Text(" ");
		dateEndText.setFont(Font.font("Verdana", 15));
		eventEnd.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

		if (e.getEventEnd() != null) {
			String formattedStringE = e.getEventEnd().format(format);
			dateEndText.setText(formattedStringE);
		}
		information.add(title, 0, 0);
		information.add(titleText, 0, 1);
		information.add(des, 0, 2);
		information.add(decText, 0, 3);
		information.add(eventStart, 0, 4);
		information.add(dateStartText, 0, 5);
		information.add(eventEnd, 0, 6);
		information.add(dateEndText, 0, 7);

		window.getChildren().addAll(info, information, EditButton(e), getDeleteButton(e, eventWindow));

		HBox all = new HBox();
		all.setPrefSize(850, 500);
		all.setPadding(new Insets(10, 10, 10, 10));
		all.getChildren().addAll(window, createEditEventWindow(e));
		editEventWindow.setContent(all);
		Scene eventScene = new Scene(editEventWindow);
		isOpen = true;
		eventWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				isOpen = false;
			}
			
		});
		eventWindow.setTitle("Event");
		eventWindow.initModality(Modality.WINDOW_MODAL);
		eventWindow.initOwner(ApplicationMain.pS);
		eventWindow.setScene(eventScene);
		eventWindow.showAndWait();

	}

	/**
	 * Responsible for getting information about event in edit event window
	 * 
	 * @param e
	 *            event to be edited
	 * @return a window
	 */
	public VBox createEditEventWindow(Event e) {

		Label nameL = new Label("Name");
		Label descriptionL = new Label("Description");

		LocalDateTime startDate = e.getEventStart();
		datePickerSettings(checkInDatePickerStart);
		datePickerSettings(checkInDatePickerEnd);
		checkInDatePickerStart.setValue(startDate.toLocalDate());
		JtimeStart.setValue(startDate.toLocalTime());

		name.setText(e.getEventName());
		description.setText(e.getEventDescription());
		
		name.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= nameMAX_CHARS ? change : null));
		
		description.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= desMAX_CHARS ? change : null));
		
		Label yearL = new Label("Start Date");
		Label hourL = new Label("Start Time");

		setDisableFields(true);

		if (e.getEventEnd() != null) {
			LocalDateTime endDate = e.getEventEnd();
			checkInDatePickerEnd.setValue(endDate.toLocalDate());
			JtimeEnd.setValue(endDate.toLocalTime());
		}
		else {
			checkInDatePickerEnd.setValue(timelineStart.minusDays(1));
			JtimeEnd.setValue(null);
		}

		checkInDatePickerEnd.setDisable(true);
		JtimeEnd.setDisable(true);

		Label yearLE = new Label("End Date");
		yearLE.setFont(Font.font("Verdana", 13));
		Label hourLE = new Label("End Time");
		hourLE.setFont(Font.font("Verdana", 13));

		nameL.setFont(Font.font("Verdana", 17));
		descriptionL.setFont(Font.font("Verdana", 17));
		yearL.setFont(Font.font("Verdana", 13));
		hourL.setFont(Font.font("Verdana", 13));

		GridPane dateTime = new GridPane();
		dateTime.setHgap(10);
		dateTime.setVgap(10);
		dateTime.setPadding(new Insets(10, 0, 5, 0));
		dateTime.add(yearL, 0, 0);
		dateTime.add(checkInDatePickerStart, 0, 1);
		dateTime.add(yearLE, 0, 2);
		dateTime.add(checkInDatePickerEnd, 0, 3);
		dateTime.add(hourL, 1, 0);
		dateTime.add(JtimeStart, 1, 1);
		dateTime.add(hourLE, 1, 2);
		dateTime.add(JtimeEnd, 1, 3);

		HBox okCancleButton = new HBox();
		okCancleButton.setSpacing(10);
		okCancleButton.getChildren().addAll(finishEdit, cancel);

		VBox editFields = new VBox();
		editFields.setPadding(new Insets(5, 0, 10, 25));
		editFields.setSpacing(10);
		editFields.getChildren().addAll(nameL, name, descriptionL, description, dateTime, okCancleButton);

		return editFields;

	}

	/**
	 * help method to check if any fields that are needed to create an Event is
	 * empty (Name, Description, Year, Month, Day and Hour)
	 *
	 * @return boolean, true if needed fields are empty otherwise false
	 */
	private boolean isNeededFieldEmpty() {
		
		// Check if name or description is empty
		if (name.getText().isEmpty() || description.getText().isEmpty()) {
			System.out.println("name and desc");
			return true;
			// check if date picker for start is not selected or if time for
			// start
			// is not selected
		} else if (checkInDatePickerStart.getValue() == null || JtimeStart.getValue() == null) {
			System.out.println("start date / time");
			return true;
		}
		// check if date picker for end value is selected, but time is not
		// selected
		// or if date picker for end is not selected, but time is selected
		else if ((checkInDatePickerEnd.getValue() != null
				&& checkInDatePickerEnd.getValue().compareTo(timelineStart) >= 0 && JtimeEnd.getValue() == null)
				|| (checkInDatePickerEnd.getValue() == null && JtimeEnd.getValue() != null)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * help method to check if the Event to be created is a duration event or
	 * not
	 *
	 * @return boolean, true if it is not a duration event otherwise false
	 */

	private boolean isNotDurationEvent() {
		if (checkInDatePickerEnd.getValue() == null || JtimeEnd.getValue() == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Help method to create an alert of type error
	 * 
	 * @param name
	 *            name of the error
	 * @param message
	 *            messaged displayed, describing the error
	 */
	private void createAlertError(String name, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(name);
		alert.setHeaderText(message);
		alert.show();
	}

	private void setNewTextsDuration(LocalDateTime startTime, LocalDateTime endTime) {
		titleText.setText("  " + name.getText());
		decText.setText(description.getText());
		dateStartText.setText(startTime.format(format));
		if (endTime == null) {
			dateEndText.setText("");
		} else {
			dateEndText.setText(endTime.format(format));
		}
	}

	/**
	 * Help method to disable fields when editing event
	 * 
	 * @param b
	 *            false if fields are active
	 */
	private void setDisableFields(boolean b) {
		name.setDisable(b);
		description.setDisable(b);

		checkInDatePickerStart.setDisable(b);
		checkInDatePickerEnd.setDisable(b);

		JtimeStart.setDisable(b);
		JtimeEnd.setDisable(b);

		finishEdit.setDisable(b);
		ok.setDisable(b);
		cancel.setDisable(b);
	}

	private void datePickerSettings(DatePicker dp) {

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.compareTo(timelineStart) < 0 || item.compareTo(timelineEnd) > 0) {
							setDisable(true);
						}

					}
				};
			}
		};
		dp.setDayCellFactory(dayCellFactory);
		dp.setConverter(converter);
		dp.setShowWeekNumbers(true);
	}

	private void initializeFields() {
		checkInDatePickerStart = new JFXDatePicker();
		checkInDatePickerEnd = new JFXDatePicker();
		checkInDatePickerStart.setDefaultColor(Color.web("rgb(87,56,97)"));
		checkInDatePickerEnd.setDefaultColor(Color.web("rgb(87,56,97)"));

		name = new JFXTextField();
		name.setUnFocusColor(Color.web("rgb(87,56,97)"));
		name.setFocusColor(Color.web("rgb(87,56,97)"));
		name.setPromptText("Event name");
		name.setFont(new Font("Times new Roman", 20));
		name.setPrefWidth(446);

		description = new JFXTextArea();
		description.setUnFocusColor(Color.web("rgb(87,56,97)"));
		description.setFocusColor(Color.web("rgb(87,56,97)"));
		description.setPromptText("Event information");
		description.setFont(Font.font("Times new Roman", 20));
		description.setPrefSize(446, 200);
		description.setWrapText(true);

		// Combo box for start event times
		JtimeStart.setIs24HourView(true);
		JtimeStart.setDefaultColor(Color.web("rgb(87,56,97)"));
		// Combo box for end event times
		JtimeEnd.setIs24HourView(true);
		JtimeEnd.setDefaultColor(Color.web("rgb(87,56,97)"));
		// Date pickers for start event
		checkInDatePickerStart.setStyle("-fx-font: 16 timesnewroman;");
		checkInDatePickerStart.setPromptText("Event start");

		// Date pickers for end event
		checkInDatePickerEnd.setStyle("-fx-font: 16 timesnewroman;");
		checkInDatePickerEnd.setPromptText("Event end");

	}

	private void initializeButtons() {
		ok.setPrefSize(80, 30);
		ok.setFont(Font.font("Verdana", 15));
		ok.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		ok.setRipplerFill(Color.web("rgb(87,56,97)"));
		ok.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		
		finishEdit.setPrefSize(80, 30);
		finishEdit.setFont(Font.font("Verdana", 15));
		finishEdit.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		finishEdit.setRipplerFill(Color.web("rgb(87,56,97)"));
		finishEdit.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));

		cancel.setPrefSize(80, 30);
		cancel.setFont(Font.font("Verdana", 15));
		cancel.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
		cancel.setRipplerFill(Color.web("rgb(87,56,97)"));
		cancel.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));

	}

	private void editEvent(String name, String description, LocalDateTime start) {
		if (eventListener.onEditEvent(name, description, start)) {

			setNewTextsDuration(start, null);
			checkInDatePickerStart.setValue(start.toLocalDate());
			JtimeStart.setValue(start.toLocalTime());
			checkInDatePickerEnd.setValue(timelineStart.minusDays(1));
			JtimeEnd.setValue(null);
			setDisableFields(true);
		} else {
			createAlertError("Error in chosing time",
					"It appears your are trying to create an event outside of timeline!");
		}
	}

	private void editDurationEvent(String name, String description, LocalDateTime start, LocalDateTime end) {

		if (eventListener.onEditEventDuration(name, description, start, end)) {
			setNewTextsDuration(start, end);
			checkInDatePickerStart.setValue(start.toLocalDate());
			JtimeStart.setValue(start.toLocalTime());
			checkInDatePickerEnd.setValue(end.toLocalDate());
			JtimeEnd.setValue(end.toLocalTime());
			setDisableFields(true);
		} else {
			createAlertError("Error in chosing time",
					"It appears your are trying to create an event outside of timeline!");
		}
	}

	private class Converter extends StringConverter<LocalDate> {

		String pattern = "GGyyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

		@Override
		public String toString(LocalDate date) {
			if (date != null && date.compareTo(timelineStart) < 0) {
				return "";
			} else if (date != null) {
				return formatter.format(date);
			} else {
				return "";
			}
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string, formatter);
			} else {
				return null;
			}
		}

	}
	public void setRoot(BorderPane bp) {
		mainView = bp;
	}

}