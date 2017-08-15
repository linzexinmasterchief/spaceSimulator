package models.uiComponentModels;

import Application.status.SystemStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Created by lzx on 2017/6/14.
 * a menu slider model
 */
public class BetterSlider extends Button {

    private double value;
    private String title;
    private Color color;

    public BetterSlider(){
        initialize();

        refresh();

    }

    public BetterSlider(String title){
        initialize();
        this.title = title;

        refresh();
    }

    public BetterSlider(String title, Color color){
        initialize();
        this.title = title;
        this.color = color;

        refresh();
    }

    private void initialize(){

        title = "";
        color = Color.grayRgb(33);

        setWidth(0);
        setVisible(false);
        setTextFill(Color.WHITE);
        setValue(0);

        setBackground(new Background(
                new BackgroundFill(color,
                        new CornerRadii(0),
                        null))
        );

        setOnMousePressed(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                value = (int) me.getX();
                if (value < 0) {
                    value = 0;
                } else if (value > getMinWidth()) {
                    value = (int) getMinWidth();
                }
            }
            refresh();
        });

        setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY){
                value = (int) me.getX();
                if (value < 0){
                    value = 0;
                }else if (value > getMinWidth()){
                    value = (int) getMinWidth();
                }
            }
            refresh();
        });

        refresh();

    }

    public void refresh(){
        setText(title + "   " + value);
        setBackground(new Background(
                new BackgroundFill(
                        color,
                        new CornerRadii(0),
                        new Insets(0,getMinWidth() - value, 0,0)
                )
        ));
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        refresh();
    }


}
