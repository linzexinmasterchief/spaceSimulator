package Application.logicUnit.worldComponents.operation;

import Application.logicUnit.World;
import Application.logicUnit.worldComponents.worldSettings.Speed;
import Application.graphicUnit.mainStageComponents.GameScene;
import Application.status.CanvasStatus;
import Application.status.Mouse;
import Application.status.SystemStatus;
import javafx.application.Platform;
import Application.logicUnit.worldComponents.physics.physicsComponents.universeComponents.Star;
import javafx.scene.input.MouseButton;
import models.systemComponentModels.ThreadModel;

/**
 * Created by lzx on 2017/7/13.
 * thread which controls every operation from mouse and keyboard
 */
public class OperationThread extends ThreadModel {

    private GameScene gameScene;
    private CanvasStatus canvasStatus;

    public OperationThread(World root_world){
        super(root_world);
    }

    @Override
    public void initialize(){
        //override default initialize block
        gameScene = world.getLauncher().getGameStage().getGameScene();

        canvasStatus = world.getLauncher().getCanvasStatus();
    }

    //determine if a new star should be created
    //left click to new a star
    private void addNewStar(){
        //open the new star lock
        SystemStatus.setNewStarExist(true);

        //give the buffer star speed based on the distance mouse dragged
        world.getBufferStar().velocityX = (float) ((((world.getDragLine()[2] - world.getDragLine()[0])
                        / Speed.getDragSpeedConstant())
                        //times camera enlarge scale
                        * world.getCamera().getWidth()) / world.getCamera().getOriginalWidth());

        world.getBufferStar().velocityY = (float) ((((world.getDragLine()[3] - world.getDragLine()[1])
                        / Speed.getDragSpeedConstant())
                        //times camera enlarge scale
                        * world.getCamera().getHeight()) / world.getCamera().getOriginalHeight());

        for (int i = 0; i < world.getUniverse().getStars().length; i++) {
            //check if the new star lock is opened to avoid unnecessary star list iterations
            //not on screen means it is safe to clear
            if (!SystemStatus.isNewStarExist()){
                break;
            }
            if (!world.getUniverse().getStars()[i].inUniverse) {

                //prepare the empty slot for new star
                world.getUniverse().getStars()[i].remove();
                world.getUniverse().getStars()[i].initialize();

                //give buffer star properties
                world.getBufferStar().mass = gameScene.getMass();
                world.getBufferStar().r = gameScene.getRadius();

                //give the properties of buffer star to the empty star slot
                world.getUniverse().getStars()[i] = new Star(world.getBufferStar());
                //remove the buffer star (clear the values to default)
                world.getBufferStar().remove();

                //add the star according to the size of window(camera)
                //and the enlarge scales
                world.getUniverse().getStars()[i].add(
                        //convert the coordinate on screen to coordinate in the universe
                        //it's hard to explain the math, but it will be easy to understand
                        //once you draw it out on the paper, be careful changing it anyway

                        (float) ((world.getCamera().getCenterX() - (world.getUniverse().getWidth() / 2))
                                + ((world.getUniverse().getWidth() - world.getCamera().getWidth()) / 2)
                                + (world.getDragLine()[0] * world.getGraphicsThread().getScaleX())),

                        (float) ((world.getCamera().getCenterY() - (world.getUniverse().getHeight() / 2))
                                + ((world.getUniverse().getHeight() - world.getCamera().getHeight()) / 2)
                                + (world.getDragLine()[1] * world.getGraphicsThread().getScaleY()))
                );

                //change the slot property from empty to full
                world.getUniverse().getStars()[i].inUniverse = true;

                //close the new star lock
                SystemStatus.setNewStarExist(false);

                //refresh the screen
                world.getGraphicsThread().drawShapes();

                world.getUniverse().setStarAmount(world.getUniverse().getStarAmount() + 1);
                world.getLauncher().getGameStage().getGameScene().getStatusBar().setStarAmount(world.getUniverse().getStarAmount());

                world.getUniverse().reFitStarListSize();
            }
        }

        //check if the the new star is created
        if (SystemStatus.isNewStarExist()) {
            //if not, expand the star list and create the star
            addNewStar();
        }

    }


    @Override
    public void run() {
        while (!isExit()) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Mouse.isMousePressed()){
                if (Mouse.getActivatedMouseButton() == MouseButton.PRIMARY){
                    world.getDragLine()[0] = Mouse.getMouse_coordinate()[0];
                    world.getDragLine()[1] = Mouse.getMouse_coordinate()[1];
                    world.getDragLine()[2] = Mouse.getMouse_coordinate()[0];
                    world.getDragLine()[3] = Mouse.getMouse_coordinate()[1];
                }

                //because mouse pressed is a short time period event, the status must be set to false after execute
                Mouse.setMousePressed(false);
            }

            if (Mouse.isMousePressing()) {
                Mouse.setMouseReleasing(false);
                switch (Mouse.getActivatedMouseButton()) {
                    case PRIMARY:
                        world.getDragLine()[2] = Mouse.getMouse_coordinate()[0];
                        world.getDragLine()[3] = Mouse.getMouse_coordinate()[1];
                        break;
                }
            }

            if (Mouse.isMouseReleasing()) {
                Mouse.setMousePressing(false);
                switch (Mouse.getActivatedMouseButton()) {
                    case PRIMARY:
                        addNewStar();
                        world.clearDragLine();
                        break;
                    case SECONDARY:
                        //execute clear command
                        world.getPhysicsThread().clear();

                        break;
                    case MIDDLE:
                        //change pause value if middle button pressed
                        world.getPhysicsThread().setPause(!world.getPhysicsThread().isPause());
                        break;
                }
                Mouse.setMouseReleasing(false);
            }

            if (Mouse.isMouseScrolled()) {

                Mouse.setMouseReleasing(false);
                Mouse.setMousePressing(false);

                double cameraWidthChangingSpeed = world.getCamera().getWidth() / world.getCamera().getOriginalWidth() * 2;
                double cameraHeightChangingSpeed = world.getCamera().getHeight() / world.getCamera().getOriginalHeight() * 2;
                //on mouse wheel rolling back (minimize)
                if (Mouse.getMouseScrollValue() < 0) {
                    if (world.getCamera().getHeight() < world.getUniverse().getHeight()
                            & world.getCamera().getWidth() < world.getUniverse().getWidth()) {

                        //change the size of the camera (+)
                        world.getCamera().setWidth(world.getCamera().getWidth() + Speed.getSizeChangeSpeed() * cameraWidthChangingSpeed);
                        world.getCamera().setHeight(world.getCamera().getHeight()
                                + Speed.getSizeChangeSpeed() * SystemStatus.getHeightWidthScale() * cameraHeightChangingSpeed);

                        //move the camera to the mouse coordinate to create an effect
                        world.getCamera().setCenterX(world.getCamera().getCenterX()
                                - (Mouse.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                                / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed() * cameraWidthChangingSpeed
                        );
                        world.getCamera().setCenterY(world.getCamera().getCenterY()
                                - (Mouse.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                                / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed() * cameraHeightChangingSpeed
                        );
                    }
                } else if (Mouse.getMouseScrollValue() > 0) {
                    //on mouse wheel rolling back (enlarge)
                    world.getCamera().setWidth(world.getCamera().getWidth() - Speed.getSizeChangeSpeed() * cameraWidthChangingSpeed);
                    world.getCamera().setHeight(world.getCamera().getHeight()
                            - Speed.getSizeChangeSpeed() * SystemStatus.getHeightWidthScale() * cameraHeightChangingSpeed
                    );

                    //move the camera to the mouse coordinate to create an effect
                    world.getCamera().setCenterX(world.getCamera().getCenterX()
                            + (Mouse.getMouse_coordinate()[0] - canvasStatus.getCanvasWidth() / 2)
                            / canvasStatus.getCanvasWidth() * Speed.getCameraMoveSpeed() * cameraWidthChangingSpeed
                    );

                    world.getCamera().setCenterY(world.getCamera().getCenterY()
                            + (Mouse.getMouse_coordinate()[1] - canvasStatus.getCanvasHeight() / 2)
                            / canvasStatus.getCanvasHeight() * Speed.getCameraMoveSpeed() * cameraHeightChangingSpeed
                    );
                }

                //calculate the scale between camera and original camera
                world.getGraphicsThread().setScaleX(world.getCamera().getWidth() / world.getCamera().getOriginalWidth());
                world.getGraphicsThread().setScaleY(world.getCamera().getHeight() / world.getCamera().getOriginalHeight());

                Mouse.setMouseScrolled(false);
            }

            gameScene.getCreateStarMenu().setVisible(SystemStatus.isCreateStarMenuOut());

            //toggle setting menu
            if (SystemStatus.isSettingStageOut()) {
                Platform.runLater(() -> world.getLauncher().getSettingStage().show());
            }else {
                Platform.runLater(() -> world.getLauncher().getSettingStage().hide());
            }

            //update status bar information
            Platform.runLater(() -> gameScene.getStatusBar().update());

        }

    }
}
