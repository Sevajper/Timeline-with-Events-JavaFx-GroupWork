package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import controls.TimelineListener;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

public class TimelineView {
	private final Tooltip addTo = new Tooltip();
	private final Tooltip delTo = new Tooltip();

	private JFXButton addTimeline = new JFXButton("", new Label("",AwesomeDude.createIconLabel(AwesomeIcon.PLUS_SIGN, "20")));
	private JFXButton deleteTimeline = new JFXButton("", new Label("",AwesomeDude.createIconLabel(AwesomeIcon.TRASH, "20")));
	private JFXButton confirmTimeline = new JFXButton("Finish");
	// HBox for "Add Timeline" button
	private HBox addTimelineButton = new HBox();
	// Stage for new window where user inputs information about timeline
	private Stage addTimelineWindow = new Stage();
	private final JFXTextField timelineName = new JFXTextField();
	private JFXDatePicker timelineStart = new JFXDatePicker();
	private JFXDatePicker timelineEnd = new JFXDatePicker();
	Converter converter = new Converter();
	private TimelineListener timelineListener;
	private boolean gotFilePath = false;
	private final int nameMAX_CHARS = 20;
	
	private BorderPane mainView;

	/**
	 * Sets listener to be able to implement functions for certain UI actions
	 * (such as button click)
	 *
	 * @param timelineListener
	 */
	public void addListener(TimelineListener timelineListener) {
		this.timelineListener = timelineListener;
	}

	/**
	 * HBox with a button, that when pressed opens new window that contains text
	 * fields for user to input information about timeline (name, start and end
	 * dates) and a "Save" button
	 *
	 * @return An HBox with a button "Add Timeline"
	 */
	public JFXButton getAddTimelineButton() {
		addTimelineWindow();
		return addTimeline;
	}

	public JFXButton getDeleteTimelineButton() {
		delTo.setText("Delete Timeline");
		delTo.setFont(Font.font("Arial", FontWeight.BOLD, 12));

		deleteTimeline.setTooltip(delTo);
		deleteTimeline.setMinSize(40, 40);
		deleteTimeline.setMaxSize(40, 40);
		deleteTimeline.setPadding(new Insets(0,0,0,-3));
		deleteTimeline.setRipplerFill(Color.web("rgb(87,56,97)"));
		deleteTimeline.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		deleteTimeline.setOnAction(new DeleteTimelineHandler());
		return deleteTimeline;
	}

	/**
	 * Sets EventHandler class for when "Add Timeline" is clicked, puts the
	 * button in HBox
	 */
	private void addTimelineWindow() {
		addTo.setText("Add Timeline");
		addTo.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		addTimelineButton.getChildren().add(addTimeline);
		addTimeline.setTooltip(addTo);
		addTimeline.setMinSize(40, 40);
		addTimeline.setMaxSize(40, 40);
		addTimeline.setRipplerFill(Color.web("rgb(87,56,97)"));
		addTimeline.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		
		addTimeline.setPadding(new Insets(0,0,0,-3));

		addTimeline.setOnAction(new TimelineHandler());
	}

	/**
	 * Opens new window for user to add new timeline.
	 */
	private void openAddTimeline() {
		GridPane addTimelineRoot = initAddTimeline();
		addTimelineWindow = new Stage();
		// Sets what happens when "Save" button is clicked
		confirmTimeline.setOnAction(new ConfirmTimelineHandler());
		confirmTimeline.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		addTimelineWindow.setTitle("Add timeline");
		addTimelineWindow.setScene(new Scene(addTimelineRoot, 600, 300));
		addTimelineWindow.setResizable(false);
		addTimelineWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				timelineName.setText(null);
				timelineStart.setValue(null);
				timelineEnd.setValue(null);
			}
			
		});
		addTimelineWindow.initModality(Modality.APPLICATION_MODAL);
		addTimelineWindow.showAndWait();
		addTimelineRoot.requestFocus();
	}

	/**
	 * Skeleton for add timeline window
	 *
	 * @return GridPane containing three text fields and a button.
	 */
	private GridPane initAddTimeline() {


		GridPane addTimelineRoot = new GridPane();
		
		timelineName.setTextFormatter(new TextFormatter<String>(
				change -> change.getControlNewText().length() <= nameMAX_CHARS ? change : null));
		
		confirmTimeline.setFont(new Font("Times new Roman", 20));
		timelineName.setPromptText("Timeline Name");
		timelineName.setFont(new Font("Times new Roman", 20));
		timelineName.setUnFocusColor(Color.web("rgb(87,56,97)"));
		timelineName.setFocusColor(Color.web("rgb(111, 83, 119)"));
		timelineStart.setPromptText("Start Year");
		timelineStart.setDefaultColor(Color.web("rgb(87,56,97)"));
		timelineStart.setConverter(converter);
		timelineEnd.setPromptText("End Year");
		timelineEnd.setDefaultColor(Color.web("rgb(87,56,97)"));
		timelineEnd.setConverter(converter);

		confirmTimeline.setMinSize(100, 30);

		HBox nameBox = new HBox(timelineName);
		HBox startBox = new HBox(timelineStart);
		HBox endBox = new HBox(timelineEnd);
		HBox dates = new HBox();
		HBox confirmTimelineButton = new HBox(confirmTimeline);

		startBox.setPadding(new Insets(0, 25, 0, 0));
		confirmTimelineButton.setPadding(new Insets(10));
		dates.setPadding(new Insets(30));

		confirmTimelineButton.setAlignment(Pos.CENTER);
		dates.setAlignment(Pos.CENTER);
		nameBox.setAlignment(Pos.CENTER);

		dates.getChildren().addAll(startBox, endBox);
		addTimelineRoot.add(nameBox, 0, 1);
		addTimelineRoot.add(dates, 0, 2);
		addTimelineRoot.add(confirmTimelineButton, 0, 3);
		addTimelineRoot.setAlignment(Pos.CENTER);

		return addTimelineRoot;
	}

	/**
	 * Opens an alert window of type confirmation, asks user if they really want
	 * to delete selected timeline. If ok is pressed, timeline is deleted and
	 * alert window closes. If cancel is pressed, window closes without deleting
	 * current timeline.
	 *
	 * @author Indre Kvedaraite
	 *
	 */
	private class DeleteTimelineHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			mainView.requestFocus();
			Stage stage = new Stage();
			HBox buttony = new HBox();
			JFXButton timeline = new JFXButton("Delete Timeline");
			CheckBox checkBox = new CheckBox("-> Delete file too");
			//Button timelineAndFile = new Button("Delete Timeline and File");
			JFXButton cancel = new JFXButton("Cancel");
			
			if (!gotFilePath) {
				checkBox.setDisable(true);
			}

			timeline.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
					confirmation.setTitle("Deleting Timeline");
					confirmation.setContentText("Are you sure you wish to delete the current timeline (and file is checked)? ");
					Optional<ButtonType> result = confirmation.showAndWait();
					if (result.get() == ButtonType.OK && checkBox.isSelected()) {
						timelineListener.onDeleteFile();
						timelineListener.onDeleteTimeline();
						confirmation.close();
						stage.close();
					}
					else if(result.get() == ButtonType.OK && !checkBox.isSelected()) {
						timelineListener.onDeleteTimeline();
						confirmation.close();
						stage.close();
					}
					else {
						confirmation.close();
					}
				}
			});

			cancel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					stage.close();
				}
			});
			HBox checkbox = new HBox();
			checkbox.getChildren().add(checkBox);
			buttony.getChildren().clear();
			buttony.setAlignment(Pos.CENTER);
			buttony.setSpacing(20.0);
			buttony.getChildren().addAll(timeline, cancel);
			VBox box = new VBox();
			box.setSpacing(20);
			box.setPadding(new Insets(30));
			box.getChildren().addAll(checkbox, buttony);

			Scene scenery = new Scene(box);

			stage.setTitle("Delete Options");
			stage.setScene(scenery);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		}

	}

	/**
	 * Private class of EventHandler that runs a method to open add timeline
	 * window when "Add Timeline" button is pressed
	 *
	 * @author Indre Kvedaraite
	 *
	 */
	private class TimelineHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			mainView.requestFocus();
			openAddTimeline();
		}
	}

	/**
	 * Private class of EventHandler that runs a method to save timeline in
	 * application if input is correct and close add timeline window. Is ran
	 * when "Save" button is clicked. Also checks if LocalDateTime format is
	 * correct.
	 *
	 * @author Indre Kvedaraite, Stefanos Bampovits
	 *
	 */
	private class ConfirmTimelineHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// Checks if all fields contain input
			if (timelineName.getText().length() == 0) {
				createAlertError("Error in timeline name", "Please choose a name for your timeline");
			}

			else if (timelineStart.getValue() == null) {
				createAlertError("Error in timeline start date", "Please choose a start date for your timeline");
			}

			else if (timelineEnd.getValue() == null) {
				createAlertError("Error in timeline end date", "Please choose an end date for your timeline.");
			}
			else if (timelineStart.getValue().compareTo(timelineEnd.getValue()) > 0) {
				createAlertError("Error in timeline end date", "Start date cannot be earlier than end date.");
			}
			else{
				String name = timelineName.getText();
				LocalDateTime start = LocalDateTime.parse(timelineStart.getValue() + "T00:00");
				LocalDateTime end = LocalDateTime.parse(timelineEnd.getValue()+ "T00:00");
				
				// If timeline was added successfully, closes the window
				if (timelineListener.onAddTimeline(name, start, end)) {
					timelineName.clear();
					timelineStart.setValue(null);
					timelineEnd.setValue(null);
					addTimelineWindow.close();
				}

			}
		}
	}
	
	/**
	 * Help method to create alert of type error
	 * @param name Name of alert
	 * @param message Message displayed in alert
	 */
	private void createAlertError(String name, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(name);
		alert.setHeaderText(message);
		alert.show();
	}

	private class Converter extends StringConverter<LocalDate> {

		String pattern = "GGyyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		
		@Override
		public String toString(LocalDate date) {
			if (date != null) {
				return formatter.format(date);
			}
			else {
				return "";
			}
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string, formatter);
			}
			else {
				return null; 
			}
		}
		
	}
	
	public void setTimelineSaved(boolean b) {
		gotFilePath = b;
	}
	
	public void setRoot(BorderPane bp) {
		mainView = bp;
	}

}
