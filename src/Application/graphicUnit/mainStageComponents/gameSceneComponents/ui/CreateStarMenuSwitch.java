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

    public CreateStarMenuSwitch(String text, GameScene gameScene) {
        super(text);

        clicked = false;

        setMinWidth(24);
        setOnMouseClicked(me -> {
            setClicked(!isClicked());
            if (isClicked()) {
                //CreateStarMenuSwitch change
                setTranslateX(160);
                setText("-");
                //pop out
                SystemStatus.setCreateStarMenuOut(true);
            } else {
                //menuButton change
                setTranslateX(5);
                setText("+");
                //goes back
                SystemStatus.setCreateStarMenuOut(false);
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
