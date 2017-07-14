package Application.stages.MainStage;

import Application.Engine.Engine;
import Application.Launcher;
import Application.stages.MainStage.gameScene.GameScene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/7/6.
 * game stage
 */
public class GameStage extends Stage{

    private Launcher launcher;

    //engine is at the same level as ganeStage
    private Engine engine;

    //create an object of the game scene
    private GameScene gameScene;

    public GameStage(Launcher starter){

        launcher = starter;

        engine = starter.getEngine();

        //initialize components
        gameScene = new GameScene(new Group(), 1000, 560, this);

        //give the stage an start scene
        setScene(gameScene);
        //set non-resizable to avoid problems caused by different resolutions
        //this may be removed if I find out how to deal with it
        setResizable(false);
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

    }

    public GameScene getGameScene(){
        return gameScene;
    }

    public Launcher getLauncher(){
        return launcher;
    }

}