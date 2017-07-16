package models.SystemComponents;

import Application.Engine.Engine;
import Application.system.SystemStatus;

/**
 * Created by lzx on 2017/7/14.
 */
public class ThreadModuleModel implements Runnable{

    protected Engine engine;
    protected SystemStatus systemStatus;

    //some properties of the program
    private boolean isExit;
    private boolean isPause;

    public ThreadModuleModel(Engine root_engine){
        engine = root_engine;

        //initialize reference to system physicsStatus
        systemStatus = engine.getLauncher().getSystemStatus();

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
