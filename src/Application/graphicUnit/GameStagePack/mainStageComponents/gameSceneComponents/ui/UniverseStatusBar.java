package Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.GameStagePack.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.uiComponentModels.UiBase;
import models.uiComponentModels.UiComponent;
import models.uiComponentModels.betterUI.BetterButton;
import models.uiComponentModels.betterUI.BetterSlider;

/**
 * Created by lzx on 2017/7/14.
 * status bar in universe
 */
public class UniverseStatusBar extends UiBase {

    private double width;
    private double height;
    private double x;
    private double y;

    private int starAmount;

    private BetterButton starAmountStatus;
    private BetterSlider timeSpeedAdjuster;

    public UniverseStatusBar() {

        //initialize game scene reference
        width = SystemStatus.getScreenwidth();
        height = 25;
        x = 0;
        y = SystemStatus.getScreenHeight() - height;

        starAmount = 0;

        //putIn buttons in it
        starAmountStatus = new BetterButton("star amount :" + starAmount);
        starAmountStatus.setTranslateX(x + 0);
        starAmountStatus.setTranslateY(y);
        starAmountStatus.setButtonWidth(width * 0.1);
        starAmountStatus.setButtonHeight(height);
        starAmountStatus.setVisible(true);
        starAmountStatus.setFont(Font.font(10));
        getChildren().add(starAmountStatus);

        //putIn time speed slider
        timeSpeedAdjuster = new BetterSlider("timeSpeed", Color.grayRgb(50), -10, 10);
        timeSpeedAdjuster.setTranslateX(x + starAmountStatus.getTranslateX() + starAmountStatus.getMinWidth());
        timeSpeedAdjuster.setTranslateY(y);
        timeSpeedAdjuster.setSliderWidth(width * 0.2);
        timeSpeedAdjuster.setSliderHeight(height);
        timeSpeedAdjuster.setVisible(true);
        timeSpeedAdjuster.setFont(Font.font(10));
        timeSpeedAdjuster.setValue(1);
        timeSpeedAdjuster.refresh();
        getChildren().add(timeSpeedAdjuster);

    }

    public void update(){
        starAmountStatus.setText("star amount : " + starAmount);

    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }

    public float getTimeSpeed(){
        return (float) (timeSpeedAdjuster.getValue() / 20);
    }
}
