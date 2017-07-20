package Application.stages.MainStage.gameScene.UIprefabs.universeStatusBar;

import Application.stages.MainStage.gameScene.GameScene;
import Application.system.SystemStatus;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import models.UIComponents.MenuButtonModel;

/**
 * Created by lzx on 2017/7/14.
 * status bar in universe
 */
public class UniverseStatusBar {

    private SystemStatus systemStatus;

    private GameScene gameScene;
    private double width;
    private double height;
    private double x;
    private double y;

    private MenuButtonModel starAmountStatus;

    private UniverseStatusBar(GameScene scene, Group group){

        systemStatus = scene.getGameStage().getLauncher().getSystemStatus();

        //initialize game scene reference
        gameScene = scene;
        width = scene.getWidth();
        height = 20;
        x = 0;
        y = scene.getHeight() - height;

        //add buttons in it
        starAmountStatus = new MenuButtonModel("star amount :");
        starAmountStatus.setTranslateX(x + 0);
        starAmountStatus.setTranslateY(y);
        starAmountStatus.setMinWidth(width * 0.2);
        starAmountStatus.setMinHeight(height);
        starAmountStatus.setVisible(true);
        starAmountStatus.setAlignment(Pos.CENTER_LEFT);
        starAmountStatus.setFont(Font.font(10));
        group.getChildren().add(starAmountStatus);

    }

    public static UniverseStatusBar createUniverseStatusBar(GameScene scene, Group group) {
        return new UniverseStatusBar(scene, group);
    }

    public MenuButtonModel getStarAmountStatus(){
        return starAmountStatus;
    }

    public void update(){
        starAmountStatus.setText("star amount : " + gameScene.getGameStage().getLauncher().getEngine().getUniverse().getStarAmount());

    }

}
