package space1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/3/21.
 */
public class Frame extends Application implements Runnable {

    private static Scene scene;
    private static Scene scene2;
    private static Thread thread;
    private static GraphicsContext gc;

    int x = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pstage) {

        StackPane stackPane = new StackPane();

        scene = new Scene(stackPane,1025,561);
        scene2 = new Scene(new Group(), 1025, 561);
        scene2.setFill(new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.REFLECT,
                new Stop(1, Color.GRAY),
                new Stop(0, Color.BLACK)));

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> thread.stop());

        Button screenBtn = new Button("", new ImageView(new Image(getClass().getResourceAsStream("..\\back.jpg"))));
        screenBtn.setOnAction((ActionEvent e) -> {
            stage.setScene(scene2);
        });
        screenBtn.setDefaultButton(true);

        Label startTitle = new Label("Click any where to start");
        startTitle.setScaleX(1.5);
        startTitle.setScaleY(1.5);
        startTitle.setTranslateY(200);
        startTitle.setTextFill(Color.WHITE);

        ((StackPane) scene.getRoot()).getChildren().add(screenBtn);
        ((StackPane) scene.getRoot()).getChildren().add(startTitle);


        Button backBtn = new Button("---Main menu---");
        backBtn.setTranslateX(10);
        backBtn.setTranslateY(10);
        backBtn.setDefaultButton(true);
        backBtn.setOnAction((ActionEvent e) -> {
            stage.setScene(scene);
        });

        Canvas canvas = new Canvas(825, 561);
        canvas.setTranslateX(200);
        canvas.setTranslateY(0);
        gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        ((Group) scene2.getRoot()).getChildren().add(backBtn);
        ((Group) scene2.getRoot()).getChildren().add(canvas);

        stage.show();
        thread = new Thread(this);
        thread.start();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.clearRect(x - 5, 5, 40, 50);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 825, 561);
        gc.setLineWidth(5);
        gc.setFill(Color.BLUE);
        gc.fillOval(x, 10, 40, 40);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            drawShapes(gc);
        }
    }
}
