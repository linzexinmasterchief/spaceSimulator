package models.systemComponentModels;

import Application.logicUnit.World;
import Application.status.SystemStatus;

/**
 * Created by lzx on 2017/7/14.
 */
public class ThreadModel implements Runnable{

    protected World world;
    protected SystemStatus systemStatus;

    //some properties of the program
    private boolean isExit;
    private boolean isPause;

    public ThreadModel(World root_world){
        world = root_world;

        //initialize reference to system PhysicsStatus
        systemStatus = world.getLauncher().getSystemStatus();

        //initialize program properties
        setExit(false);
        setPause(false);

        initialize();
    }

    public void initialize(){
        //default initialize block
    }

    public boolean isPause(){
        return isPause;
    }

    public void setPause(boolean pause){
        isPause = pause;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    @Override
    public void run() {

    }
}
