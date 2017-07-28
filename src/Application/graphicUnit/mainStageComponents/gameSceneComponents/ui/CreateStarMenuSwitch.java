package Application.graphicUnit.mainStageComponents.gameSceneComponents.ui;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import models.uiComponentModels.CircularButton;

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

        setMinWidth(24);
        setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //CreateStarMenuSwitch change
                setTranslateX(160);
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
