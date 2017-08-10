package models.uiComponentModels;

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
public class MenuSlider extends Button {

    private double value;
    private String title;

    public MenuSlider(){
        initialize();
    }

    public MenuSlider(String text){
        title = text;
        initialize();
    }

    private void initialize(){
        setMinWidth(150);
        setWidth(150);
        setVisible(false);
        setTextFill(Color.WHITE);
        setValue(0);

        setBackground(new Background(
                new BackgroundFill(Color.grayRgb(33),
                        new CornerRadii(0),
                        null))
        );

        setOnMousePressed(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                value = me.getX();
                if (value < 0) {
                    value = 0;
                } else if (value > getWidth()) {
                    value = getWidth();
                }
            }
            refresh();
        });

        setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY){
                value = me.getX();
                if (value < 0){
                    value = 0;
                }else if (value > getWidth()){
                    value = getWidth();
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
                        Color.grayRgb(33),
                        new CornerRadii(0),
                        new Insets(0,150 - value, 0,0)
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
