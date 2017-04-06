package scenes;

import Main.MainStage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import scenes.canvas.GameCanvas;

import static javafx.scene.paint.Color.DARKGREY;

/**
 * Created by lzx on 2017/4/5.
 */
public class GameScene extends Scene {

    public static GameCanvas gameCanvas;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);

        Button backBtn = new Button("              Main menu              ");
        backBtn.setTranslateX(10);
        backBtn.setTranslateY(10);
        backBtn.setDefaultButton(true);
        backBtn.setOnMouseEntered(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(10), null))));
        backBtn.setOnMouseExited(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null))));
        backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null)));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction((ActionEvent e) -> {
            MainStage.stage.setScene(MainStage.titleScene);
        });

        gameCanvas = new GameCanvas(800, 560);

        setFill(DARKGREY);

        MainStage.group.getChildren().add(backBtn);
        MainStage.group.getChildren().add(gameCanvas);
    }

}
