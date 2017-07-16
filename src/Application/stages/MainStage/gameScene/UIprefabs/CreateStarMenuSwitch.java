package Application.stages.MainStage.gameScene.UIprefabs;

import Application.stages.MainStage.gameScene.GameScene;
import Application.system.SystemStatus;
import models.UIComponents.CircularButton;

/**
 * Created by lzx on 2017/7/15.
 * create star menu switch
 */
public class CreateStarMenuSwitch extends CircularButton {

    private boolean clicked;
    private SystemStatus systemStatus;

    public CreateStarMenuSwitch(String text, GameScene gameScene) {
        super(text);
        systemStatus = gameScene.getGameStage().getLauncher().getSystemStatus();

        clicked = false;

        setTranslateX(5);
        setTranslateY(5);
        setMinWidth(24);
        setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //CreateStarMenuSwitch change
                setTranslateX(170);
                setText("-");
                //pop out
                systemStatus.setCreateStarMenuOut(true);
            } else {
                //menuButton change
                setTranslateX(5);
                setText("+");
                //goes back
                systemStatus.setCreateStarMenuOut(false);
            }
        });
    }


    private boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
