import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.lang.annotation.Target;

/**
 * Created by lzx on 2017/3/21.
 */
public class Test extends Application{

    Scene scene;
    Scene scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage = new Stage();
        Stage stage2 = new Stage();

        StackPane stackPane = new StackPane();
        scene = new Scene(stackPane,1025,561);
        stage.setResizable(false);

        scene2 = new Scene(new Group(), 300, 300);

        stage.setScene(scene);
        Button button = new Button("",new ImageView(new Image(getClass().getResourceAsStream("back.jpg"))));
        Stage finalStage = stage;
        button.setOnAction((ActionEvent e) ->{
            finalStage.setScene(scene2);
        });
        ((StackPane)scene.getRoot()).getChildren().add(button);
//
//        //一个带文本和图标的Label
//        Image image = new Image(getClass().getResourceAsStream("back.jpg"));
//        Label label3 = new Label("",new ImageView(image));
//        ((Group) scene.getRoot()).getChildren().add(label3);

        stage.show();
    }
}
