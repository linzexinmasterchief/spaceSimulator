package models.UIComponents;

import Application.stages.MainStage.gameScene.GameScene;
import Application.system.SystemStatus;

import java.security.acl.Group;

/**
 * Created by lzx on 2017/7/14.
 */
public class BottomStatusBar {

    private SystemStatus systemStatus;

    private GameScene gameScene;
    private double width;
    private double height;
    private double x;
    private double y;

    private MenuButtonPrefab[] statusBar;

    public BottomStatusBar(GameScene scene){

        systemStatus = scene.getGameStage().getLauncher().getSystemStatus();

        //initialize game scene reference
        gameScene = scene;
        width = scene.getWidth();
        height = 25;
        x = 0;
        y = scene.getHeight() - height;

        //initialize button group
        statusBar = new MenuButtonPrefab[10];

        //add buttons in it
        MenuButtonPrefab starAmountStatus = new MenuButtonPrefab("star amount:");
        starAmountStatus.setTranslateX(x + 0);
        starAmountStatus.setTranslateY(y);
        starAmountStatus.setMinWidth(width * 0.2);
        starAmountStatus.setMinHeight(height);
        starAmountStatus.setVisible(true);
        statusBar[0] = starAmountStatus;
        System.out.println(height);

    }

    public MenuButtonPrefab[] getStatusElements(){
        return statusBar;
    }

    public void update(){
        getStatusElements()[0].setText("star amount : " + systemStatus.getStarAmount());

    }

}
