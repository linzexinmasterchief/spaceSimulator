package Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.GameStagePack.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import models.uiComponentModels.UiBase;
import models.uiComponentModels.UiComponent;
import models.uiComponentModels.betterUI.BetterButton;
import models.uiComponentModels.betterUI.BetterSlider;

/**
 * Created by lzx on 2017/7/15.
 * menu used to create stars
 */
public class CreateStarMenu extends UiBase{
    //menu components
    private final BetterButton settingBtn;
    private final BetterSlider massSlider;
    private final BetterSlider radiusSlider;

    public CreateStarMenu(){

        settingBtn = new BetterButton("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(-150);
        settingBtn.setButtonHeight(30);
        settingBtn.setButtonWidth(150);
        settingBtn.setOnAction(ae -> {
            SystemStatus.setSettingStageOut(!SystemStatus.isSettingStageOut());
        });
        getChildren().add(settingBtn);

        massSlider = new BetterSlider("mass", Color.grayRgb(33), 0, 150);
        massSlider.setTranslateX(-150);
        massSlider.setTranslateY(35);
        massSlider.setSliderWidth(150);
        massSlider.setSliderHeight(30);
        massSlider.setValue(10);
        massSlider.refresh();
        getChildren().add(massSlider);

        radiusSlider = new BetterSlider("radius", Color.grayRgb(33), 0, 300);
        radiusSlider.setTranslateX(-150);
        radiusSlider.setTranslateY(65);
        radiusSlider.setSliderWidth(150);
        radiusSlider.setSliderHeight(30);
        radiusSlider.setValue(5);
        radiusSlider.refresh();
        getChildren().add(radiusSlider);
    }

//getters
    public BetterButton getSettingBtn(){
        return settingBtn;
    }

    public BetterSlider getMassSlider(){
        return massSlider;
    }

    public BetterSlider getRadiusSlider(){
        return radiusSlider;
    }

    @Override
    public void setX(double value) {
        settingBtn.setTranslateX(value);
        massSlider.setTranslateX(value);
        radiusSlider.setTranslateX(value);
    }

    @Override
    public void setY(double value) {

    }

    @Override
    public void setCenterX(double value) {
        settingBtn.setTranslateX(value);
        massSlider.setTranslateX(value);
        radiusSlider.setTranslateX(value);
    }

    @Override
    public void setCenterY(double value) {

    }

    @Override
    public void setWidth(double value) {

    }

    @Override
    public void setHeight(double value) {

    }

}
