package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import javafx.geometry.Pos;
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

    private GameScene gameScene;

    private double width;
    private double height;
    private double x;
    private double y;

    private int starAmount;

    private BetterButton starAmountStatus;
    private BetterSlider timeSpeedAjuster;

    private UniverseStatusBar(GameScene scene, Group group){

        //initialize game scene reference
        gameScene = scene;
        width = scene.getWidth();
        height = 20;
        x = 0;
        y = scene.getHeight() - height;

        starAmount = 0;

        //add buttons in it
        starAmountStatus = new BetterButton("star amount :" + starAmount);
        starAmountStatus.setTranslateX(x + 0);
        starAmountStatus.setTranslateY(y);
        starAmountStatus.setMaxWidth(width * 0.1);
        starAmountStatus.setMinWidth(width * 0.1);
        starAmountStatus.setMinHeight(height);
        starAmountStatus.setVisible(true);
        starAmountStatus.setAlignment(Pos.CENTER_LEFT);
        starAmountStatus.setFont(Font.font(10));
        group.getChildren().add(starAmountStatus);

        //add time speed slider
        timeSpeedAjuster = new BetterSlider("timeSpeed", Color.grayRgb(50), -1000, 1000);
        timeSpeedAjuster.setTranslateX(starAmountStatus.getTranslateX() + starAmountStatus.getMinWidth());
        timeSpeedAjuster.setTranslateY(y);
        timeSpeedAjuster.setSliderWidth(width * 0.2);
        timeSpeedAjuster.setSliderHeight(height);
        timeSpeedAjuster.setVisible(true);
        timeSpeedAjuster.setFont(Font.font(10));
        timeSpeedAjuster.setValue(100);
        timeSpeedAjuster.refresh();
        group.getChildren().add(timeSpeedAjuster);

    }

    public static UniverseStatusBar createUniverseStatusBar(GameScene scene, Group group) {
        return new UniverseStatusBar(scene, group);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
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
        return timeSpeedAjuster.getValue() / 20;
    }
}
