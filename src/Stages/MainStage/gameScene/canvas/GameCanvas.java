package Stages.MainStage.gameScene.canvas;

import MyEngine.GameEngine;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import models.Star;
import Stages.MainStage.gameScene.GameScene;

/**
 * Created by lzx on 2017/4/6.
 * this is the game canvas that will draw the stars
 * also, this is the only class that have the ability
 * of displaying graphics, which means, all of the
 * displaying command and modules should be called
 * in this class
 */
public class GameCanvas extends Canvas{

    //create reference to rootScene
    private GameScene gameScene;
    //create reference to game engine
    private GameEngine gameEngine;

    //new star lock
    private boolean isNewStarExist;

    //variables used to store mouse operations
    private double mouseEventX;
    private double mouseEventY;
    //an array used to store the coordinate of the mouse
    private double[] mouse_coordinate;

    //a variable used to store the scale between height and width
    private double HeightWidthScale;
    //determine how fast the star moves with the same drag distance
    //larger the value, slower the speed
    private int dragSpeedConstant = 1000;
    //determine how fast the camera enlarge/minify
    private int sizeChangeSpeed = 20;
    //determine how fast the camera moves to the mouse coordinate when scrolling on a point
    private int cameraMoveSpeed = 20;

    //constructor
    public GameCanvas(double width, double height, GameScene rootScene) {
        //width and height is pretty necessary for things like
        //"this.getWidth()" to work properly
        super(width, height);

        gameScene = rootScene;
        gameEngine = gameScene.getGameEngine();

        //initialize the scale between height and width
        HeightWidthScale = this.getHeight() / this.getWidth();

        //initialize the variable used to store mouse operations
        mouseEventX = 0;
        mouseEventY = 0;
        mouse_coordinate = new double[2];

        //close new star lock
        isNewStarExist = false;


        //listen the operations of mouse press
        setOnMousePressed(me -> {
            //get the mouse press coordinate
            mouse_coordinate[0] = me.getX();
            mouse_coordinate[1] = me.getY();

            if (me.getButton() == MouseButton.MIDDLE){
                gameEngine.getPhysicsModule().setPause(!gameEngine.getPhysicsModule().isPause());
                gameEngine.getGraphicsModule().setPause(!gameEngine.getGraphicsModule().isPause());
            }
        });

        //set operations on mouse release
        //new and clear
        setOnMouseReleased(me -> {

            //determine if a new star should be created
            //left click to new a star
            if (me.getButton() == MouseButton.PRIMARY) {
                //open the new star lock
                isNewStarExist = true;

                //get the mouse release coordinate
                mouseEventX = me.getX();
                mouseEventY = me.getY();

                //give the buffer star speed based on the distance mouse dragged
                gameEngine.getBufferStar().vectorX = (mouseEventX - mouse_coordinate[0]) / dragSpeedConstant;
                gameEngine.getBufferStar().vectorY = (mouseEventY - mouse_coordinate[1]) / dragSpeedConstant;

                //check if the new star lock is opened
                //to avoid unnecessary star list iterations
                //check if there is empty star slot for a new star
                if (isNewStarExist) {
                    for (int i = 0; i < gameEngine.getStars().length; i++) {
                        //not on screen means it is safe to clear
                        if (isNewStarExist & !gameEngine.getStars()[i].inUniverse) {

                            //prepare the empty slot for new star
                            gameEngine.getStars()[i].remove();
                            gameEngine.getStars()[i].initialize();

                            //give buffer star properties
                            gameEngine.getBufferStar().mass = gameScene.getMass();
                            gameEngine.getBufferStar().r = gameScene.getRadius();

                            //give the properties of buffer star to the empty star slot
                            gameEngine.getStars()[i] = new Star(gameEngine.getBufferStar());
                            //remove the buffer star (clear the values to default)
                            gameEngine.getBufferStar().remove();

                            //add the star according to the size of window(camera)
                            //and the enlarge scales
                            gameEngine.getStars()[i].add(
                                    //convert the coordinate on screen to coordinate in the universe
                                    //it's hard to explain the math, but it will be easy to understand
                                    //once you draw it out on the paper, be careful changing it anyway
                                    (gameEngine.getCamera().getCenterX() - gameEngine.getUniverse().getWidth() / 2)
                                            + (gameEngine.getUniverse().getWidth() - gameEngine.getCamera().getWidth()) / 2
                                            + mouseEventX * gameEngine.getGraphicsModule().getScaleX(),
                                    (gameScene.getGameEngine().getCamera().getCenterY() - gameScene.getGameEngine().getUniverse().getHeight() / 2)
                                            + (gameScene.getGameEngine().getUniverse().getHeight() - gameScene.getGameEngine().getCamera().getHeight()) / 2
                                            + mouseEventY * gameEngine.getGraphicsModule().getScaleY()
                            );

                            //change the slot property from empty to full
                            gameEngine.getStars()[i].inUniverse = true;

                            //close the new star lock
                            isNewStarExist = false;

                            //refresh the screen
                            gameEngine.getGraphicsModule().drawShapes();
                        }
                    }
                }
            }

            //right click the mouse to clean the screen
            //remove all the stars from the universe
            if (me.getButton() == MouseButton.SECONDARY) {
                gameEngine.getGraphicsModule().clear();
            }
        });

        //set operations on mouse scrolled
        //enlarge and minimize
        setOnScroll(se -> {

            //get the mouse coordinate
            mouseEventX = se.getX();
            mouseEventY = se.getY();

            //on mouse wheel rolling back (minimize)
            if (se.getDeltaY() < 0) {

                //change the size of the camera (+)
                gameEngine.getCamera().setWidth(gameEngine.getCamera().getWidth() + sizeChangeSpeed);
                gameEngine.getCamera().setHeight(gameEngine.getCamera().getHeight() + sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                gameEngine.getCamera().setCenterX(gameEngine.getCamera().getCenterX() - (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                gameEngine.getCamera().setCenterY(gameEngine.getCamera().getCenterY() - (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            } else {
                //on mouse wheel rolling back (enlarge)
                gameEngine.getCamera().setWidth(gameEngine.getCamera().getWidth() - sizeChangeSpeed);
                gameEngine.getCamera().setHeight(gameEngine.getCamera().getHeight() - sizeChangeSpeed * HeightWidthScale);

                //move the camera to the mouse coordinate to create an effect
                gameEngine.getCamera().setCenterX(gameEngine.getCamera().getCenterX() + (mouseEventX - this.getWidth() / 2) / this.getWidth() * cameraMoveSpeed);
                gameEngine.getCamera().setCenterY(gameEngine.getCamera().getCenterY() + (mouseEventY - this.getHeight() / 2) / this.getHeight() * cameraMoveSpeed);
            }

            //calculate the scale between camera and original camera
            gameEngine.getGraphicsModule().setScaleX(gameEngine.getCamera().getWidth() / gameEngine.getCamera().getOriginalWidth());
            gameEngine.getGraphicsModule().setScaleY(gameEngine.getCamera().getHeight() / gameEngine.getCamera().getOriginalHeight());

            //refresh the screen
            gameEngine.getGraphicsModule().drawShapes();
        });

    }

}
