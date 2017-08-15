package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
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

        massSlider = new BetterSlider("mass");
        massSlider.setTranslateX(5);
        massSlider.setTranslateY(35);
        massSlider.setMaxWidth(150);
        massSlider.setMinWidth(150);
        massSlider.setMaxHeight(25);
        massSlider.setMinHeight(25);
        massSlider.setValue(10);
        group.getChildren().add(massSlider);

        raiusSlider = new BetterSlider("radius");
        raiusSlider.setTranslateX(5);
        raiusSlider.setTranslateY(65);
        raiusSlider.setMaxWidth(150);
        raiusSlider.setMinWidth(150);
        raiusSlider.setMaxHeight(25);
        raiusSlider.setMinHeight(25);
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
