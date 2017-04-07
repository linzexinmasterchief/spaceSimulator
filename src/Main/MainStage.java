package Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scenes.GameScene;
import scenes.TitleScene;
import scenes.canvas.GameCanvas;

/**
 * Created by lzx on 2017/3/21.
 */

public class MainStage extends Application implements Runnable {

    public static Group group;
    public static StackPane stackPane;

    public static Stage stage;
    public static TitleScene titleScene;
    public static GameScene gameScene;

    public static Thread thread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage HAHAstage) {

        group = new Group();
        gameScene = new GameScene(group, 1000, 560);

        stackPane = new StackPane();
        titleScene = new TitleScene(stackPane, 1000, 560);

        stage = new Stage();
        stage.setScene(titleScene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle("SpaceSimulator");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("..\\pictures\\icon.png")));
        stage.setOnCloseRequest(event -> thread.stop());

        stage.show();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stage.getScene() == gameScene) {
                GameCanvas.GameThread();
            } else if (stage.getScene() == titleScene) {
                TitleScene.TitleThread();
            }
        }
    }
}
