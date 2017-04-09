package scenes;

import Main.MainStage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    public static double mass;
    public static double VectorX;
    public static double VectorY;

    public boolean pause;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);
        pause = false;
        mass = 1;
        VectorX = 0;
        VectorY = 0;

        setOnKeyTyped(ke -> {
            if (ke.getCharacter().equals("e")) {
                pause = !pause;
            }
            System.out.println(pause);
        });

        Button backBtn = new Button("Main menu");
        backBtn.setTranslateX(10);
        backBtn.setTranslateY(10);
        backBtn.setPrefWidth(180);
        backBtn.setDefaultButton(true);
        backBtn.setOnMouseEntered(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(10), null))));
        backBtn.setOnMouseExited(me -> backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null))));
        backBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null)));
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction((ActionEvent e) -> {
            MainStage.stage.setScene(MainStage.titleScene);
        });

        TextField massTextField = new TextField();
        massTextField.setTranslateX(10);
        massTextField.setTranslateY(40);
        massTextField.setPrefWidth(180);
        massTextField.setPromptText("Enter the mass");
        massTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        massTextField.setOnKeyReleased(ke -> {
            System.out.println(ke.getCode());
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                GameCanvas.InputMass = Double.parseDouble(massTextField.getText());
            } catch (Exception e) {
            }
        });

        TextField XTextField = new TextField();
        XTextField.setTranslateX(10);
        XTextField.setTranslateY(70);
        XTextField.setPrefWidth(180);
        XTextField.setPromptText("Enter the VectorX");
        XTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        XTextField.setOnKeyReleased(ke -> {
            System.out.println(ke.getCode());
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                GameCanvas.InputVectorX = Double.parseDouble(XTextField.getText()) * 100;
            } catch (Exception e) {
            }
        });

        TextField YTextField = new TextField();
        YTextField.setTranslateX(10);
        YTextField.setTranslateY(100);
        YTextField.setPrefWidth(180);
        YTextField.setPromptText("Enter the VectorY");
        YTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        YTextField.setOnKeyReleased(ke -> {
            System.out.println(ke.getCode());
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                GameCanvas.InputVectorY = Double.parseDouble(YTextField.getText()) * 100;
            } catch (Exception e) {
            }
        });

        Button clearBtn = new Button("clear all");
        clearBtn.setTranslateX(10);
        clearBtn.setTranslateY(530);
        clearBtn.setPrefWidth(180);
        clearBtn.setDefaultButton(true);
        clearBtn.setOnMouseEntered(me -> clearBtn.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), null))));
        clearBtn.setOnMouseExited(me -> clearBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null))));
        clearBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(10), null)));
        clearBtn.setTextFill(Color.WHITE);
        clearBtn.setOnAction((ActionEvent e) -> {
            gameCanvas.clear();
        });

        gameCanvas = new GameCanvas(800, 560);

        setFill(DARKGREY);

        MainStage.group.getChildren().add(backBtn);
        MainStage.group.getChildren().add(massTextField);
        MainStage.group.getChildren().add(XTextField);
        MainStage.group.getChildren().add(YTextField);
        MainStage.group.getChildren().add(gameCanvas);
        MainStage.group.getChildren().add(clearBtn);
    }

}
