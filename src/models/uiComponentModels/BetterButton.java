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
public class BetterButton extends Button {

    public BetterButton(String Text){
        setText(Text);
        setTextFill(Color.WHITE);
        setVisible(false);
        setBackground(
                new Background(
                        new BackgroundFill(Color.grayRgb(33),new CornerRadii(0),null)
                )
        );

        setOnMouseEntered(me ->
                setBackground(
                        new Background(
                                new BackgroundFill(Color.grayRgb(24), new CornerRadii(0), null)
                        )
                )
        );

        setOnMouseExited(me ->
                setBackground(
                        new Background(
                                new BackgroundFill(Color.grayRgb(33), new CornerRadii(0), null)
                        )
                )
        );
    }

    public void setButtonWidth(double value){
        setMaxWidth(value);
        setMinWidth(value);
        setWidth(value);
    }

    public void setButtonHeight(double value){
        setMaxHeight(value);
        setMinHeight(value);
        setHeight(value);
    }

}
