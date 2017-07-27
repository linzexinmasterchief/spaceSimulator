package Application.stages.MainStage.gameScene.UIprefabs.createStarMenu;

import Application.stages.MainStage.gameScene.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import models.UIComponents.MenuButtonModel;
import models.UIComponents.MenuSliderModel;

/**
 * Created by lzx on 2017/7/15.
 * menu used to create stars
 */
public class CreateStarMenu {
    private SystemStatus systemStatus;

    //reference to root scene
    private GameScene gameScene;

    //menu components
    private MenuButtonModel settingBtn;
    private MenuSliderModel massSlider;
    private MenuSliderModel raiusSlider;

    public CreateStarMenu(GameScene scene, Group group){
        systemStatus = scene.getGameStage().getLauncher().getSystemStatus();

        gameScene = scene;

        settingBtn = new MenuButtonModel("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(5);
        settingBtn.setMaxHeight(25);
        settingBtn.setMinHeight(25);
        settingBtn.setMinWidth(150);
        settingBtn.setOnAction(ae -> {
            systemStatus.setSettingStageOut(!systemStatus.isSettingStageOut());
        });
        group.getChildren().add(settingBtn);

        massSlider = new MenuSliderModel();
        massSlider.setTranslateX(5);
        massSlider.setTranslateY(35);
        massSlider.setMaxHeight(25);
        massSlider.setMinHeight(25);
        massSlider.setValue(10);
        group.getChildren().add(massSlider);

        raiusSlider = new MenuSliderModel();
        raiusSlider.setTranslateX(5);
        raiusSlider.setTranslateY(65);
        raiusSlider.setMaxHeight(25);
        raiusSlider.setMinHeight(25);
        raiusSlider.setValue(5);
        group.getChildren().add(raiusSlider);
    }

    public MenuButtonModel getSettingBtn(){
        return settingBtn;
    }

    public MenuSliderModel getMassSlider(){
        return massSlider;
    }

    public MenuSliderModel getRaiusSlider(){
        return raiusSlider;
    }

}
