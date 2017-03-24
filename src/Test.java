import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        stage.setResizable(false);

        StackPane stackPane = new StackPane();
        scene = new Scene(stackPane,1025,561);

        scene2 = new Scene(new StackPane(), 300, 300);

        stage.setScene(scene);
        Button button = new Button("",new ImageView(new Image(getClass().getResourceAsStream("back.jpg"))));
        Stage finalStage = stage;
        button.setOnAction((ActionEvent e) ->{
            finalStage.setScene(scene2);
        });
        ((StackPane)scene.getRoot()).getChildren().add(button);

        stage.show();
    }
}
