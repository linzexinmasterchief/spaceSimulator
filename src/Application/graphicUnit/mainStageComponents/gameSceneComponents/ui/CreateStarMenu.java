package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import models.uiComponentModels.betterUI.BetterButton;
import models.uiComponentModels.betterUI.BetterSlider;

/**
 * Created by lzx on 2017/7/15.
 * menu used to create stars
 */
public class CreateStarMenu {

    //menu components
    private final BetterButton settingBtn;
    private final BetterSlider massSlider;
    private final BetterSlider radiusSlider;

    public CreateStarMenu(GameScene scene, Group group){

        settingBtn = new BetterButton("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(5);
        settingBtn.setButtonHeight(25);
        settingBtn.setButtonWidth(150);
        settingBtn.setOnAction(ae -> {
            SystemStatus.setSettingStageOut(!SystemStatus.isSettingStageOut());
        });
        group.getChildren().add(settingBtn);

        massSlider = new BetterSlider("mass", Color.grayRgb(33), 0, 150);
        massSlider.setTranslateX(5);
        massSlider.setTranslateY(35);
        massSlider.setSliderWidth(150);
        massSlider.setSliderHeight(25);
        massSlider.setValue(10);
        massSlider.refresh();
        group.getChildren().add(massSlider);

        radiusSlider = new BetterSlider("radius", Color.grayRgb(33), 0, 300);
        radiusSlider.setTranslateX(5);
        radiusSlider.setTranslateY(65);
        radiusSlider.setSliderWidth(150);
        radiusSlider.setSliderHeight(25);
        radiusSlider.setValue(5);
        radiusSlider.refresh();
        group.getChildren().add(radiusSlider);
    }

    public void setVisible(boolean visibility){
        settingBtn.setVisible(visibility);
        massSlider.setVisible(visibility);
        radiusSlider.setVisible(visibility);
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

}
