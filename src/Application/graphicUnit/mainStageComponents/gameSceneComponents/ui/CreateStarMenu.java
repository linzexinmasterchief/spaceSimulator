package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import models.uiComponentModels.BetterButton;
import models.uiComponentModels.BetterSlider;

/**
 * Created by lzx on 2017/7/15.
 * menu used to create stars
 */
public class CreateStarMenu {

    //reference to root scene
    private GameScene gameScene;

    //menu components
    private BetterButton settingBtn;
    private BetterSlider massSlider;
    private BetterSlider raiusSlider;

    public CreateStarMenu(GameScene scene, Group group){

        gameScene = scene;

        settingBtn = new BetterButton("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(5);
        settingBtn.setMaxHeight(25);
        settingBtn.setMinHeight(25);
        settingBtn.setMinWidth(150);
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
        group.getChildren().add(massSlider);

        raiusSlider = new BetterSlider("radius", Color.grayRgb(33), 0, 300);
        raiusSlider.setTranslateX(5);
        raiusSlider.setTranslateY(65);
        raiusSlider.setSliderWidth(150);
        raiusSlider.setSliderHeight(25);
        raiusSlider.setValue(5);
        group.getChildren().add(raiusSlider);
    }

    public void setVisible(boolean visibility){
        settingBtn.setVisible(visibility);
        massSlider.setVisible(visibility);
        raiusSlider.setVisible(visibility);
    }

//getters
    public BetterButton getSettingBtn(){
        return settingBtn;
    }

    public BetterSlider getMassSlider(){
        return massSlider;
    }

    public BetterSlider getRaiusSlider(){
        return raiusSlider;
    }

}
