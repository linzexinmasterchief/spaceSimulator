package models;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Created by lzx on 2017/6/14.
 * a pre-made model of button, used in menu
 */
public class MenuButtonPrefab extends Button {
    public MenuButtonPrefab(String Text){
        setText(Text);
        setTranslateX(5);
        setMinWidth(150);
        setVisible(false);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
        setOnMouseEntered(me -> setBackground(new Background(new BackgroundFill(Color.GRAY, null, null))));
        setOnMouseExited(me -> setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null))));
    }
}
