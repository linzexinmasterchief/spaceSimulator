package Stages;

import MyEngine.GameEngine;
import Stages.MainStage.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Created by lzx on 2017/3/21.
 * this is the root class, start stage
 */

public class MainStarter extends Application {

    private GameEngine gameEngine;

    private GameStage gameStage;

    //launch the application
    public static void main(String[] args) {
        launch(args);
    }

    //override the default constructor
    @Override
    //initialize the application
    public void start(Stage stage) {
        //the position is critical
        gameEngine = new GameEngine(this);
        //create an object of the stage
        stage = new GameStage(this);


        //I am totally unsure if this piece of weired code would be a problem
        gameStage = (GameStage) stage;

        //initialize the stage reference in game engine
        gameEngine.setGameStage();
        gameEngine.getGraphicsModule().initialize();

        //start the threads after canvas is initialized
        gameEngine.getPhysics().start();
        gameEngine.getGraphics().start();

        //add the window
        stage.show();
    }

    public GameEngine getGameEngine(){
        return gameEngine;
    }
    public GameStage getGameStage(){
        return gameStage;
    }
}
