package space1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.BLACK;

/**
 * Created by lzx on 2017/3/21.
 */
public class Frame extends Application implements Runnable {

    private static Stage stage;
    private static Scene scene;
    private static Label startTitle;
    private static Scene scene2;
    private static Thread thread;
    private static GraphicsContext gc;

    double opacity = 1;
    double speedOpacity = 0.02;

    int x = 250;
    int y = 280;

    double speedX = 0;
    double speedY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pstage) {

        StackPane stackPane = new StackPane();

        scene = new Scene(stackPane,1025,561);

        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("SpaceSimulator");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("..\\icon.png")));
        stage.setOnCloseRequest(event -> thread.stop());

        Image image = new Image(getClass().getResourceAsStream("..\\back.jpg"));
        ImageView imageView = new ImageView(image);
        Button screenBtn = new Button("", new ImageView(image));
        screenBtn.setOnAction((ActionEvent e) -> {
            stage.setScene(scene2);
        });
        screenBtn.setDefaultButton(true);

        startTitle = new Label("Click any where to start");
        startTitle.setScaleX(1.5);
        startTitle.setScaleY(1.5);
        startTitle.setTranslateY(230);
        startTitle.setTextFill(Color.gray(1));

        ((StackPane) scene.getRoot()).getChildren().add(screenBtn);
        ((StackPane) scene.getRoot()).getChildren().add(startTitle);


        scene2 = new Scene(new Group(), 1025, 561);
        scene2.setFill(new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.REFLECT,
                new Stop(1, Color.DARKBLUE),
                new Stop(0, Color.BLACK)));

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
            stage.setScene(scene);
        });

        Canvas canvas = new Canvas(825, 561);
        canvas.setTranslateX(200);
        canvas.setTranslateY(0);
        canvas.setOnMouseDragged(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - x) / 20;
                speedY = (me.getY() - 20 - y) / 20;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                x = (int) (me.getX() - 20);
                y = (int) (me.getY() - 20);
                speedX = speedY = 0;
            }
        });
        canvas.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                speedX = (me.getX() - 20 - x) / 100;
                speedY = (me.getY() - 20 - y) / 100;
            } else if (me.getButton() == MouseButton.SECONDARY) {
                x = (int) (me.getX() - 20);
                y = (int) (me.getY() - 20);
                speedX = speedY = 0;
            }
        });

        gc = canvas.getGraphicsContext2D();
        gc.setFill(BLACK);
        gc.fillRect(0, 0, 825, 561);
        drawShapes(gc);

        ((Group) scene2.getRoot()).getChildren().add(backBtn);
        ((Group) scene2.getRoot()).getChildren().add(canvas);
        ((Group) scene2.getRoot()).getChildren().add(menuBar);

        stage.show();
        thread = new Thread(this);
        thread.start();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 825, 561);
        gc.setFill(Color.BLUE);
        gc.fillOval(x, y, 40, 40);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stage.getScene() == scene2) {
                x += speedX;
                y += speedY;
                drawShapes(gc);
            } else if (stage.getScene() == scene) {
                if (opacity >= 1) {
                    speedOpacity = -0.02;
                } else if (opacity <= 0.1) {
                    speedOpacity = 0.02;
                }
                opacity += speedOpacity;
                startTitle.setTextFill(Color.gray(opacity));
            }
        }
    }
}
