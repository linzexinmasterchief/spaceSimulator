package Application.logicUnit.worldComponents.graphics;

import Application.graphicUnit.mainStageComponents.GameScene;
import Application.graphicUnit.mainStageComponents.gameSceneComponents.GameCanvas;
import Application.logicUnit.World;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.systemComponentModels.ThreadModel;

/**
 * Created by lzx on 2017/7/6.
 * graphics processor module
 */
public class GraphicsThread extends ThreadModel {

    private GameScene gameScene;
    private GameCanvas gameCanvas;
    private GraphicsContext gc;

    //the scale between graphics and physics
    //original is 1:1
    private double scaleX = 1.0;
    private double scaleY = 1.0;

    private double starDisplayWidth;
    private double starDisplayHeight;

    public GraphicsThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = world.getLauncher().getGameStage().getGameScene();
        gameCanvas = gameScene.getGameCanvas();
        gc = gameCanvas.getGraphicsContext2D();

        //initialize program properties
        setExit(false);
        setPause(false);

    }

    //screen painting function
    public void drawShapes() {

        Platform.runLater(() -> {
            //fill the back ground with black color
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        });

        Platform.runLater(() -> {
            //draw drag line
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.strokeLine(
                    world.getDragLine()[0],
                    world.getDragLine()[1],
                    world.getDragLine()[2],
                    world.getDragLine()[3]
            );
        });


        //iterate the star list to draw all the exist stars in the universe
        for (Star star : world.getUniverse().getStars()) {

            Platform.runLater(() -> {
                int r, g, b = 0;

                //change the color of pen according to the mass of the star to paint the stars
                if (star.mass > 500){
                    r = 0;
                    g = 0;
                    b = 255;
                }else if (star.mass < 0){
                    r = 255;
                    g = 0;
                    b = 0;
                }else {
                    if (star.mass > 255){
                        r = (int) (500 - star.mass);
                        g = (int) (500 - star.mass);
                        b = 255;
                    }else {
                        r = 255;
                        g = (int) star.mass;
                        b = (int) star.mass;
                    }
                }
                gc.setFill(Color.rgb(r, g, b));
            });

            //only display the star if it is on screen
            //in the camera range
            if (star.onScreen) {


                //noinspection OverlyLongLambda
                Platform.runLater(() -> {

                    //create and initialize star display width and height
                    starDisplayWidth = 1;
                    starDisplayHeight = 1;

                    //calculate the actual display size according to the scale
                    starDisplayWidth = (star.r * 2) / scaleX;
                    starDisplayHeight = (star.r * 2) / scaleY;

                    //make sure there is always a small point displaying for every star, even the scale is crazily large
                    if (starDisplayWidth < 1) {
                        starDisplayWidth = 1;
                    }
                    if (starDisplayHeight < 1) {
                        starDisplayHeight = 1;
                    }

                    gc.fillOval(

                            //a little math here, should be reliable after 6 changes
                            //also, this is related to the math above when the user
                            //new a star, they are reverse functions to each other

                            //this is convert the universe coordinates to display coordinates
                            ((
                                    star.centerX
                                            - (world.getCamera().getCenterX() - (world.getUniverse().getWidth() / 2))
                                            - ((world.getUniverse().getWidth() - world.getCamera().getWidth()) / 2))
                                    / scaleX)
                                    - (starDisplayWidth / 2),
                            ((
                                    star.centerY - (world.getCamera().getCenterY() - (world.getUniverse().getHeight() / 2))
                                            - ((world.getUniverse().getHeight() - world.getCamera().getHeight()) / 2)
                            )
                                    / scaleY)
                                    - (starDisplayHeight / 2),
                            this.starDisplayWidth,
                            this.starDisplayHeight
                    );
                });

            }
        }

    }


    //getters and setters

    public double getScaleX(){
        return scaleX;
    }

    public double getScaleY(){
        return scaleY;
    }

    public void setScaleX(double new_scaleX){
        scaleX = new_scaleX;
    }

    public void setScaleY(double new_scaleY){
        scaleY = new_scaleY;
    }

    //the graphic thread operation function
    @Override
    public void run() {
        while (!isExit()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPause()){
                continue;
            }
            //send the rendering job to a background thread
            drawShapes();
        }
    }
}
