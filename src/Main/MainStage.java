package Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scenes.GameScene;
import scenes.TitleScene;
import sounds.Music;

/**
 * Created by lzx on 2017/3/21.
 * this is the root class, start stage
 */

public class MainStage extends Application implements Runnable {

    public static Group group;
    public static StackPane stackPane;

    public static Stage stage;
    public static TitleScene titleScene;
    public static GameScene gameScene;

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
        stage.setResizable(false);
        stage.setTitle("SpaceSimulator");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("..\\pictures\\icon.png")));
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.sizeToScene();
        stage.show();

        Thread thread = new Thread(this);
        thread.start();

        Music music = new Music();
        Thread thread2 = new Thread(music);
        thread2.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TitleScene.TitleThread();
        }
    }
}
