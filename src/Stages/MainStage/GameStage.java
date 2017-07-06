package Stages.MainStage;

import MyEngine.GameEngine;
import Stages.MainStage.gameScene.GameScene;
import Stages.MainStarter;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/7/6.
 * game stage
 */
public class GameStage extends Stage{

    //gameEngine is at the same level as ganeStage
    private GameEngine gameEngine;

    //create an object of the game scene
    private GameScene gameScene;

    public GameStage(MainStarter starter){

        gameEngine = starter.getGameEngine();

        //initialize components
        gameScene = new GameScene(new Group(), 1000, 560, gameEngine);

        //give the stage an start scene
        setScene(gameScene);
        //set non-resizable to avoid problems caused by different resolutions
        //this may be removed if I find out how to deal with it
        setResizable(false);
        //give the application a title
        setTitle("SpaceSimulator");
        //exit on close
        setOnCloseRequest(event -> {
            gameEngine.EXIT();
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

}
