package scenes;

import Main.MainStage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * this class is designed to put the control panel and the game canvas
 */
public class GameScene extends Scene {

    private static GameCanvas gameCanvas;
    private boolean pause;
    private TextField AccelerationXTextField;
    private TextField AccelerationYTextField;
    private TextField YTextField;
    private TextField massTextField;
    private TextField XTextField;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);
        pause = false;

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
        backBtn.setOnAction((ActionEvent e) -> MainStage.stage.setScene(MainStage.titleScene));

        massTextField = new TextField();
        massTextField.setTranslateX(70);
        massTextField.setTranslateY(40);
        massTextField.setPrefWidth(120);
        massTextField.setPromptText("Enter the mass");
        massTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        massTextField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                int mass = Integer.parseInt(massTextField.getText());
                if (mass > 5000) {
                    mass = 5000;
                } else if (mass < -5000) {
                    mass = -5000;
                }
                GameCanvas.InputMass = mass;
                System.out.println(massTextField.getText());
            } catch (Exception ignored) {

            }
            if (massTextField.getText().equals("")) {
                GameCanvas.InputMass = 0;
            }
        });

        Label massPromt = new Label("Mass:");
        massPromt.setTranslateX(20);
        massPromt.setTranslateY(40);

        XTextField = new TextField();
        XTextField.setTranslateX(70);
        XTextField.setTranslateY(70);
        XTextField.setPrefWidth(120);
        XTextField.setPromptText("Enter the VectorX");
        XTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        XTextField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                double vex = Double.parseDouble(XTextField.getText());
                if (vex > 1000) {
                    vex = 1000;
                } else if (vex < -1000) {
                    vex = -1000;
                }
                GameCanvas.InputVectorX = vex;
            } catch (Exception ignored) {
            }
            if (XTextField.getText().equals("")) {
                GameCanvas.InputVectorX = 0;
            }
        });

        Label XPromt = new Label("VecX:");
        XPromt.setTranslateX(20);
        XPromt.setTranslateY(70);

        YTextField = new TextField();
        YTextField.setTranslateX(70);
        YTextField.setTranslateY(100);
        YTextField.setPrefWidth(120);
        YTextField.setPromptText("Enter the VectorY");
        YTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        YTextField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                double vey = Double.parseDouble(YTextField.getText());
                if (vey > 1000) {
                    vey = 1000;
                } else if (vey < -1000) {
                    vey = -1000;
                }
                GameCanvas.InputVectorY = vey;
            } catch (Exception ignored) {

            }
            if (YTextField.getText().equals("")) {
                GameCanvas.InputVectorY = 0;
            }
        });

        Label YPromt = new Label("VecY:");
        YPromt.setTranslateX(20);
        YPromt.setTranslateY(100);

        AccelerationXTextField = new TextField();
        AccelerationXTextField.setTranslateX(70);
        AccelerationXTextField.setTranslateY(130);
        AccelerationXTextField.setPrefWidth(120);
        AccelerationXTextField.setPromptText("Enter AccelerationX");
        AccelerationXTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        AccelerationXTextField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                int accx = Integer.parseInt(AccelerationXTextField.getText());
                if (accx > 500) {
                    accx = 500;
                } else if (accx < -500) {
                    accx = -500;
                }
                GameCanvas.InputAccelerationX = accx;
            } catch (Exception ignored) {

            }
            if (AccelerationXTextField.getText().equals("")) {
                GameCanvas.InputAccelerationX = 0;
            }
        });

        Label AccelerationXPromt = new Label("AccX:");
        AccelerationXPromt.setTranslateX(20);
        AccelerationXPromt.setTranslateY(130);

        AccelerationYTextField = new TextField();
        AccelerationYTextField.setTranslateX(70);
        AccelerationYTextField.setTranslateY(160);
        AccelerationYTextField.setPrefWidth(120);
        AccelerationYTextField.setPromptText("Enter AccelerationY");
        AccelerationYTextField.setBackground(new Background(new BackgroundFill(getFill(), new CornerRadii(10), null)));
        AccelerationYTextField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                gameCanvas.requestFocus();
            }
            try {
                int accy = Integer.parseInt(AccelerationYTextField.getText());
                if (accy > 500) {
                    accy = 500;
                } else if (accy < -500) {
                    accy = -500;
                }
                GameCanvas.InputAccelerationY = accy;
            } catch (Exception ignored) {

            }
            if (AccelerationYTextField.getText().equals("")) {
                GameCanvas.InputAccelerationY = 0;
            }
        });

        Label AccelerationYPromt = new Label("AccY:");
        AccelerationYPromt.setTranslateX(20);
        AccelerationYPromt.setTranslateY(160);

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
            massTextField.clear();
            XTextField.clear();
            YTextField.clear();
            AccelerationXTextField.clear();
            AccelerationYTextField.clear();
        });

        gameCanvas = new GameCanvas(800, 560);

        setFill(DARKGREY);

        MainStage.group.getChildren().add(backBtn);
        MainStage.group.getChildren().add(massTextField);
        MainStage.group.getChildren().add(massPromt);
        MainStage.group.getChildren().add(XTextField);
        MainStage.group.getChildren().add(XPromt);
        MainStage.group.getChildren().add(YTextField);
        MainStage.group.getChildren().add(YPromt);
        MainStage.group.getChildren().add(AccelerationXTextField);
        MainStage.group.getChildren().add(AccelerationXPromt);
        MainStage.group.getChildren().add(AccelerationYTextField);
        MainStage.group.getChildren().add(AccelerationYPromt);
        MainStage.group.getChildren().add(gameCanvas);
        MainStage.group.getChildren().add(clearBtn);
    }

}
