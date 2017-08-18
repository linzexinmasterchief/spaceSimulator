package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.uiComponentModels.BetterButton;
import models.uiComponentModels.BetterSlider;

/**
 * Created by lzx on 2017/7/14.
 * status bar in universe
 */
public class UniverseStatusBar {

    private final GameScene gameScene;

    private double width;
    private double height;
    private double x;
    private double y;

    private int starAmount;

    private BetterButton starAmountStatus;
    private BetterSlider timeSpeedAdjuster;

    public UniverseStatusBar(GameScene scene){

        //initialize game scene reference
        gameScene = scene;
        width = 0;
        height = 0;
        x = 0;
        y = 0;

        starAmount = 0;

    }

    public void join(Group group){

        //add buttons in it
        starAmountStatus = new BetterButton("star amount :" + starAmount);
        starAmountStatus.setTranslateX(x + 0);
        starAmountStatus.setTranslateY(y);
        starAmountStatus.setButtonWidth(width * 0.1);
        starAmountStatus.setButtonHeight(height);
        starAmountStatus.setVisible(true);
        starAmountStatus.setFont(Font.font(10));
        group.getChildren().add(starAmountStatus);

        //add time speed slider
        timeSpeedAdjuster = new BetterSlider("timeSpeed", Color.grayRgb(50), -1000, 1000);
        timeSpeedAdjuster.setTranslateX(x + starAmountStatus.getTranslateX() + starAmountStatus.getMinWidth());
        timeSpeedAdjuster.setTranslateY(y);
        timeSpeedAdjuster.setSliderWidth(width * 0.2);
        timeSpeedAdjuster.setSliderHeight(height);
        timeSpeedAdjuster.setVisible(true);
        timeSpeedAdjuster.setFont(Font.font(10));
        timeSpeedAdjuster.setValue(100);
        timeSpeedAdjuster.refresh();
        group.getChildren().add(timeSpeedAdjuster);

    }

    //setters and getters
    public void setX(double value){
        x = value;
    }

    public double getX(){
        return x;
    }

    public void setY(double value){
        y = value;
    }

    public double getY(){
        return y;
    }

    public void setHeight(double value){
        height = value;
    }

    public double getHeight(){
        return height;
    }

    public void setWidth(double value){
        width = value;
    }

    public double getWidth(){
        return width;
    }

    public BetterButton getStarAmountStatus(){
        return starAmountStatus;
    }

    public void update(){
        starAmountStatus.setText("star amount : " + starAmount);

    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }

    public double getTimeSpeed(){
        return timeSpeedAdjuster.getValue() / 20;
    }
}
