package Application.graphicUnit.GameStagePack.mainStageComponents.gameSceneComponents;

import Application.status.CanvasStatus;
import Application.status.KeyBoard;
import Application.status.Mouse;
import Application.status.SystemStatus;
import javafx.scene.canvas.Canvas;
import Application.graphicUnit.GameStagePack.mainStageComponents.GameScene;
import javafx.scene.input.MouseButton;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 * also, this is the only class that have the ability
 * of displaying graphics, which means, all of the
 * displaying command and modules should be called
 * in this class
 */
public class GameCanvas extends Canvas{

    //create reference to canvas status
    private final CanvasStatus canvasStatus;

    //constructor
    public GameCanvas(double width, double height, GameScene rootScene) {
        //width and height is pretty necessary for things like
        //"this.getWidth()" to work properly
        super(width, height);

        SystemStatus.setHeightWidthScale((float) (getHeight() / getWidth()));

        canvasStatus = rootScene.getGameStage().getLauncher().getCanvasStatus();
        canvasStatus.setCanvasHeight(getHeight());
        canvasStatus.setCanvasWidth(getWidth());

    }

}
