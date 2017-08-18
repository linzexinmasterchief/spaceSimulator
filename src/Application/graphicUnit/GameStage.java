package Application.graphicUnit;

import Application.Launcher;
import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.SystemStatus;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCharacterCombination;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by lzx on 2017/7/6.
 * game stage
 */
public class GameStage extends Stage{

    private final Launcher launcher;

    //create an object of the game scene
    private final GameScene gameScene;

    public GameStage(Launcher starter){

        setFullScreen(true);
        setFullScreenExitHint("");
        setFullScreenExitKeyCombination(new KeyCharacterCombination("lalalala"));

        setOnCloseRequest(we -> {
            System.out.println("exit");
        });

        launcher = starter;

        //initialize components
        gameScene = new GameScene(new Group(), SystemStatus.getScreenwidth(), SystemStatus.getScreenHeight(), this);

        //give the stage an start scene
        setScene(gameScene);

        //set non-resizable to avoid problems caused by different resolutions
        //this may be removed if I find out how to deal with it
        //give the application a title
        setTitle("SpaceSimulator");

        //exit on close
        setOnCloseRequest(event -> {
            System.exit(0);
        });
        //make sure the stage is completely filled with game scene
        sizeToScene();

        //icon
        getIcons().add(new Image("/resources/icon.png"));

        sizeToScene();

    }

    public GameScene getGameScene(){
        return gameScene;
    }

    public Launcher getLauncher(){
        return launcher;
    }

}
