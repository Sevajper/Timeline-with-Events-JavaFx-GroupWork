package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HelpFunction {
	
	private final Tooltip helpTo = new Tooltip();
	private final JFXButton helpButton = new JFXButton("", new Label("",AwesomeDude.createIconLabel(AwesomeIcon.QUESTION_SIGN, "20")) ); 
	
	public JFXButton createHelpButton() {
		helpTo.setText("Help");
		helpTo.setFont(Font.font("Arial", FontWeight.BOLD, 12));

		helpButton.setTooltip(helpTo);
		helpButton.setRipplerFill(Color.web("rgb(87,56,97)"));
		helpButton.setBackground(new Background(new BackgroundFill(Color.web("rgb(223,223,223)"), null, null)));
		helpButton.setMinSize(40, 40);
		helpButton.setMaxSize(40, 40);
		helpButton.setButtonType(ButtonType.FLAT);

		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				VBox HelpButtonPic = new VBox();
				Label addTimeline = new Label(" - Add new timeline",AwesomeDude.createIconLabel(AwesomeIcon.PLUS_SIGN, "20"));
				Label deleteTimeline = new Label(" - Delete existing timeline",AwesomeDude.createIconLabel(AwesomeIcon.TRASH, "20"));
				Label loadTimeline = new Label("- Load timeline from an XML file",AwesomeDude.createIconLabel(AwesomeIcon.FOLDER_OPEN, "20"));
				Label saveTimeline = new Label("- Save timeline into an XML file",AwesomeDude.createIconLabel(AwesomeIcon.SAVE, "20"));
				Label addEvent = new Label("- Add an event",AwesomeDude.createIconLabel(AwesomeIcon.CALENDAR, "20"));
				
				addTimeline.setPadding(new Insets(10));
				deleteTimeline.setPadding(new Insets(10));
				loadTimeline.setPadding(new Insets(10));
				saveTimeline.setPadding(new Insets(10));
				addEvent.setPadding(new Insets(10));
				
				HelpButtonPic.getChildren().addAll(addTimeline, deleteTimeline, loadTimeline, saveTimeline, addEvent);
				
				Stage popup = new Stage();

				VBox firstHelpWindow = new VBox();
				firstHelpWindow.getChildren().add(HelpButtonPic);

				popup.setResizable(false);
				popup.setMinHeight(300);
				popup.setMinWidth(300);

				popup.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
					if (!isNowFocused) {
						popup.close();
					}

				});

				VBox v1 = new VBox();
				GridPane root = new GridPane();

				root.add(firstHelpWindow, 3, 1);
				root.add(v1, 4, 1);
				Scene scene = new Scene(root, 300, 280);
				popup.setScene(scene);
				popup.setTitle("Help");
				popup.show();

			}
		});
		return helpButton;
	}
}
