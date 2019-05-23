package controls;

import java.io.File;
import java.util.ArrayList;

import functions.App;
import functions.Event;
import functions.Timeline;
import io.FileHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import ui.ApplicationView;

public class ApplicationControl implements ApplicationListener {

	private ApplicationView appView;
	private App app;
	private FileHandler fileHandler;
	private TimelineControl timelineControl;
	private EventControl eventControl;

	/**
	 * Constructor, Creates an ApplicationControl and sets variables for
	 * ApplicationView, App, FileHandler. Also creates a new TimelineControl and
	 * an EventControl.
	 * 
	 * @param av
	 *            , ApplicationView
	 * @param app
	 *            , App
	 * @param fh
	 *            , FileHandler
	 */
	public ApplicationControl(ApplicationView av, App app, FileHandler fh) {
		appView = av;
		this.app = app;
		fileHandler = fh;
		timelineControl = new TimelineControl();
		eventControl = new EventControl();
		eventControl.setApp(app);
		timelineControl.setApp(app);
	}

	/**
	 * Connects the View and Control through the Listener Also connects
	 * ApplicationView to App through the Listener
	 */
	public void setUpListeners() {
		appView.getTimelineView().addListener(timelineControl);
		appView.getEventView().addListener(eventControl);
		appView.addListener(this);
		app.addListener(appView);
	}

	@Override
	public void onTimelineSelected(Timeline t) {
		app.setCurrentTimeline(t);

	}

	@Override
	public void onNewEventSelected(Event e) {
		app.setCurrentEvent(e);

	}

	@Override
	public void onTimelineSaved() {
		if (app.getCurrentTimeline().getFile().toString().length() > 2 || app.getCurrentTimeline().getFile().exists()) {
			try {
				fileHandler.saveTimeline(app.getCurrentTimeline(), app.getCurrentTimeline().getFile());
				appView.onTimelineSaved(app.getCurrentTimeline());
				Alert success = new Alert(Alert.AlertType.INFORMATION);
				success.setTitle("Saving complete");
				success.setHeaderText("Success!");
				success.setContentText("Your file has been successfully saved!");
				success.showAndWait();
			} catch (Exception e) {
			}
		}

		else {

			FileChooser chooseFile = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
			chooseFile.getExtensionFilters().add(extFilter);

			// Show save file dialog
			File file = chooseFile.showSaveDialog(appView.getRoot().getScene().getWindow());
			app.getCurrentTimeline().setFilePath(file);

			try {
				fileHandler.saveTimeline(app.getCurrentTimeline(), file);
				appView.onTimelineSaved(app.getCurrentTimeline());
			} catch (Exception saver) {
			}
		}
	}

	@Override
	public void onTimelineLoaded() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(appView.getRoot().getScene().getWindow());

		try {
			Timeline t = fileHandler.loadTimeline(file);
			app.addTimelineToList(t);
			t.setFilePath(file);
			appView.getTimelineView().setTimelineSaved(true);
		} catch (Exception loader) {
		}
	}

	@Override
	public ArrayList<Timeline> getTimelines() {
		return app.getTimelines();
	}

}