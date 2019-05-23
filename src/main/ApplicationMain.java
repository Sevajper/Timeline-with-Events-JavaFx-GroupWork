package main;
import javafx.application.*;

import controls.ApplicationControl;
import functions.App;
import io.FileHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import ui.ApplicationView;

public class ApplicationMain extends Application {
	
	public static Stage pS;

	public static void main(String[] args) {


		launch(args);


	}

	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		this.pS = primaryStage;
		//Create a App, FileHandler, ApplicationView and ApplicationControl
		App app = new App();
		FileHandler fileHandler = new FileHandler();
		ApplicationView appView = new ApplicationView();
		appView.getEventView().getAddEventButton().setDisable(true);
		ApplicationControl appControl = new ApplicationControl(appView, app, fileHandler);
		appControl.setUpListeners();
		
		//Collect Root from ApplicationView and build
		Scene scene = new Scene(appView.getRoot(), 1100, 600);
		pS.getIcons().add(new Image("/Timeline.png"));
		pS.setTitle("Timeline Manager");
		pS.setScene(scene);
		pS.show();


	}

}