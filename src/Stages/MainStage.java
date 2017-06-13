package Stages;

import MyEngine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/3/21.
 * this is the root class, start stage
 */

public class MainStage extends Application {

    //launch the application
    public static void main(String[] args) {
        launch(args);
    }

    //override the default constructor
    @Override
    //initialize the application
    public void start(Stage stage) {
        GameEngine gameEngine = new GameEngine();

        //create an object of the stage
        stage = new Stage();
        //give the stage an start scene
        stage.setScene(gameEngine.gameScene);
        //set non-resizable to avoid problems caused by different resolutions
        //this may be removed if I find out how to deal with it
        stage.setResizable(false);
        //give the application a title
        stage.setTitle("SpaceSimulator");
        //exit on close
        stage.setOnCloseRequest(event -> {
            gameEngine.setExit(true);
            gameEngine.gameScene.getGameCanvas().setExit(true);
            System.exit(0);
        });
        //make sure the stage is completely filled with game scene
        stage.sizeToScene();
        //show the window
        stage.show();
    }
}
