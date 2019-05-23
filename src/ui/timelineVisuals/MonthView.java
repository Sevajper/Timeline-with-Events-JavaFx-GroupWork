package ui.timelineVisuals;

import javafx.geometry.Pos;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MonthView extends HBox {


	private final int BOX_HEIGHT = 70;
	private final int BOX_LENGTH = 150;
	private final Text text;
	private final CornerRadii radii = new CornerRadii(5);
	
	public MonthView() {
		setMinSize(BOX_LENGTH, BOX_HEIGHT);
		setMinSize(BOX_LENGTH, BOX_HEIGHT);
		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.web("rgb(87,56,97)"), radii, null)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, radii, BorderStroke.THIN)));
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-20.0);

		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);

		lighting.setDiffuseConstant(1.3);
		setEffect(lighting);
		text = new Text();
		getChildren().add(text);
	}
	
	public int getLength() {
		return BOX_LENGTH;
	}
	
	public void setText(String str) {
		text.setFill(Color.WHITESMOKE);
		text.setFont(Font.font ("monospace", 25));
		text.setText(str);
	}
}
