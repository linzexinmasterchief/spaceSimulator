package Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import scenes.gameScene.GameScene;

/**
 * Created by lzx on 2017/3/21.
 * this is the root class, start stage
 */

public class MainStage extends Application {

    public static GameScene gameScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        gameScene = new GameScene(new Group(), 1000, 560);

        stage = new Stage();
        stage.setScene(gameScene);
        stage.setResizable(false);
        stage.setTitle("SpaceSimulator");
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.sizeToScene();
        stage.show();
    }
}
