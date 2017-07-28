package models.uiComponentModels;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by lzx on 2017/6/14.
 * a pre-made model of button, used in menu
 */
public class MenuButton extends Button {
    public MenuButton(String Text){
        setText(Text);
        setTextFill(Color.WHITE);
        setVisible(false);
        setBackground(new Background(new BackgroundFill(Color.grayRgb(33),new CornerRadii(0),null)));
        setOnMouseEntered(me -> setBackground(new Background(new BackgroundFill(Color.grayRgb(24), new CornerRadii(0), null))));
        setOnMouseExited(me -> setBackground(new Background(new BackgroundFill(Color.grayRgb(33), new CornerRadii(0), null))));
    }
}
