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
    private double uiValue;

    private String title;
    private Color color;

    private double sliderWidth;
    private double sliderHeight;

    private double maxValue;
    private double minValue;
    private double valueRange;
    private double valueScale;

    public BetterSlider(){
        initialize();

        updateValueScale();
        updateValueRange();

        refresh();
    }

    public BetterSlider(String title){
        initialize();
        this.title = title;

        updateValueScale();
        updateValueRange();

        refresh();
    }

    public BetterSlider(String title, Color color){
        initialize();
        this.title = title;
        this.color = color;

        updateValueScale();
        updateValueRange();

        refresh();
    }

    public BetterSlider(String title, Color color, double minValue, double maxValue){
        initialize();
        this.title = title;
        this.color = color;
        this.maxValue = maxValue;
        this.minValue = minValue;

        updateValueScale();
        updateValueRange();

        refresh();
    }

    private void initialize(){

        setVisible(false);
        setTextFill(Color.WHITE);

        value = 0;
        uiValue = 0;

        title = "";
        color = Color.grayRgb(33);
        maxValue = 0;
        minValue = 0;
        valueRange = maxValue - minValue;
        valueScale = valueRange / getWidth();

        setBackground(
                new Background(
                        new BackgroundFill(
                                color,
                                new CornerRadii(0),
                                null
                        )
                )
        );

        setOnMousePressed(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                uiValue = me.getX();
                value = minValue + (uiValue * valueScale);
            }
            refresh();
        });

        setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY){
                uiValue = me.getX();
                if (uiValue < 0){
                    uiValue = 0;
                }else if (uiValue > getSliderWidth()){
                    uiValue = getSliderWidth();
                }
                value = minValue + (uiValue * valueScale);
            }
            refresh();
        });

    }

    public void refresh(){
        setText(title + "   " + value);
        updateValueScale();
        setBackground(new Background(
                new BackgroundFill(
                        color,
                        new CornerRadii(0),
                        new Insets(0,getSliderWidth() - uiValue, 0,0)
                )
        ));
    }

    private void updateValueRange(){
        valueRange = maxValue - minValue;
    }

    private void updateValueScale(){
        if (getWidth() == 0){
            valueScale = 0;
            uiValue = 0;
            return;
        }
        valueScale = valueRange / getWidth();
        uiValue = (value - minValue) / valueScale;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        uiValue = (value - minValue) / valueScale;
        refresh();
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        updateValueRange();
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
        updateValueRange();
    }

    public double getSliderWidth() {
        return sliderWidth;
    }

    public void setSliderWidth(double sliderWidth) {
        this.sliderWidth = sliderWidth;
        setWidth(sliderWidth);
        setMaxWidth(sliderWidth);
        setMinWidth(sliderWidth);

        updateValueScale();
    }

    public double getSliderHeight() {
        return sliderHeight;
    }

    public void setSliderHeight(double sliderHeight) {
        setHeight(sliderHeight);
        setMaxHeight(sliderHeight);
        setMinHeight(sliderHeight);

        updateValueScale();
    }
}
