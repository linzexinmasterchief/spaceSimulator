package Application.stages.MainStage.gameScene.UIprefabs.createStarMenu;

import Application.stages.MainStage.gameScene.GameScene;
import Application.system.SystemStatus;
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
        settingBtn.setMinWidth(150);
        settingBtn.setOnAction(ae -> {
            systemStatus.setSettingStageOut(!systemStatus.isSettingStageOut());
        });
        group.getChildren().add(settingBtn);

        massSlider = new MenuSliderModel(40, 10);
        group.getChildren().add(massSlider);

        raiusSlider = new MenuSliderModel(80, 5);
        raiusSlider.setMax(20);
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
