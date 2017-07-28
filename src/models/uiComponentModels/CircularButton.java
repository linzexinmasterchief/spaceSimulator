package models.uiComponentModels;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by lzx on 2017/7/14.
 */
public class CircularButton extends Button{

    public CircularButton(String text){

        setText(text);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(20), null)));
        setOnMouseEntered(me -> setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(20), null))));
        setOnMouseExited(me -> setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(20), null))));

    }

}
