package space1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by lzx on 2017/3/21.
 */
public class Frame extends Application{

    Scene scene;
    Scene scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage = new Stage();
        Stage finalStage = stage;

        StackPane stackPane = new StackPane();

        scene = new Scene(stackPane,1025,561);
        scene2 = new Scene(new Group(), 1025, 561);

        stage.setScene(scene);

        Button button = new Button("",new ImageView(new Image(getClass().getResourceAsStream("..\\back.jpg"))));
        button.setOnAction((ActionEvent e) ->{
            finalStage.setScene(scene2);
        });
        button.setDefaultButton(true);

        Label label = new Label("Click any where to start");
        label.setScaleX(1.5);
        label.setScaleY(1.5);
        label.setTranslateY(200);
        label.setTextFill(Color.WHITE);

        ((StackPane)scene.getRoot()).getChildren().add(button);
        ((StackPane)scene.getRoot()).getChildren().add(label);


        Button button1 = new Button("Print Hello");
        button1.setTranslateX(100);
        button1.setDefaultButton(true);
        button1.setOnAction((ActionEvent e) ->{
            finalStage.setScene(scene);
        });

        Star star = new Star();
        star.setFill(Color.rgb(60, 130, 255, 0.5));
        star.setCenterX(50);
        star.setCenterY(50);
        star.setRadius(25);

        ((Group)scene2.getRoot()).getChildren().add(button1);
        ((Group)scene2.getRoot()).getChildren().add(star);

        stage.show();
    }
}
