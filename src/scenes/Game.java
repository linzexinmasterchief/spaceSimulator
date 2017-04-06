package scenes;

import Main.MainStage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import scenes.canvas.GameCanvas;

import static javafx.scene.paint.Color.BLACK;

/**
 * Created by lzx on 2017/4/5.
 */
public class Game extends Scene {

    public static GameCanvas gameCanvas;

    public Game(Parent root, double width, double height) {
        super(root, width, height);

        MenuBar menuBar = new MenuBar();
        menuBar.setTranslateX(0);
        menuBar.setTranslateY(0);
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Options");
        Menu menu3 = new Menu("Help");
        menuBar.setPrefWidth(1030);
        menuBar.getMenus().addAll(menu1, menu2, menu3);
        menuBar.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(0), null)));

        Button backBtn = new Button("              Main menu              ");
        backBtn.setTranslateX(10);
        backBtn.setTranslateY(30);
        backBtn.setDefaultButton(true);
        backBtn.setOnMouseEntered(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(10), null))));
        backBtn.setOnMouseExited(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null))));
        backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null)));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction((ActionEvent e) -> {
            MainStage.stage.setScene(MainStage.start);
        });

        gameCanvas = new GameCanvas(825, 561);

        setFill(BLACK);

        MainStage.group.getChildren().add(backBtn);
        MainStage.group.getChildren().add(gameCanvas);
        MainStage.group.getChildren().add(menuBar);

    }

}
