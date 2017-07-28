package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import models.UIComponentModels.MenuButton;
import models.UIComponentModels.MenuSlider;

/**
 * Created by lzx on 2017/7/15.
 * menu used to create stars
 */
public class CreateStarMenu {
    private SystemStatus systemStatus;

    //reference to root scene
    private GameScene gameScene;

    //menu components
    private MenuButton settingBtn;
    private MenuSlider massSlider;
    private MenuSlider raiusSlider;

    public CreateStarMenu(GameScene scene, Group group){
        systemStatus = scene.getGameStage().getLauncher().getSystemStatus();

        gameScene = scene;

        settingBtn = new MenuButton("setting");
        settingBtn.setTranslateY(5);
        settingBtn.setTranslateX(5);
        settingBtn.setMaxHeight(25);
        settingBtn.setMinHeight(25);
        settingBtn.setMinWidth(150);
        settingBtn.setOnAction(ae -> {
            systemStatus.setSettingStageOut(!systemStatus.isSettingStageOut());
        });
        group.getChildren().add(settingBtn);

        massSlider = new MenuSlider();
        massSlider.setTranslateX(5);
        massSlider.setTranslateY(35);
        massSlider.setMaxHeight(25);
        massSlider.setMinHeight(25);
        massSlider.setValue(10);
        group.getChildren().add(massSlider);

        raiusSlider = new MenuSlider();
        raiusSlider.setTranslateX(5);
        raiusSlider.setTranslateY(65);
        raiusSlider.setMaxHeight(25);
        raiusSlider.setMinHeight(25);
        raiusSlider.setValue(5);
        group.getChildren().add(raiusSlider);
    }

    public MenuButton getSettingBtn(){
        return settingBtn;
    }

    public MenuSlider getMassSlider(){
        return massSlider;
    }

    public MenuSlider getRaiusSlider(){
        return raiusSlider;
    }

}
