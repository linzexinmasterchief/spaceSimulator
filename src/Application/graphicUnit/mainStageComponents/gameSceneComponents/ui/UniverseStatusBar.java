package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import models.uiComponentModels.MenuButton;

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

    private MenuButton starAmountStatus;

    private UniverseStatusBar(GameScene scene, Group group){

        systemStatus = scene.getGameStage().getLauncher().getSystemStatus();

        //initialize game scene reference
        gameScene = scene;
        width = scene.getWidth();
        height = 20;
        x = 0;
        y = scene.getHeight() - height;

        //add buttons in it
        starAmountStatus = new MenuButton("star amount :");
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

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public MenuButton getStarAmountStatus(){
        return starAmountStatus;
    }

    public void update(){
        starAmountStatus.setText("star amount : " + gameScene.getGameStage().getLauncher().getWorld().getUniverse().getStarAmount());

    }

}
