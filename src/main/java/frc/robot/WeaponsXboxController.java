package frc.robot;

import frc.robot.huskylib.devices.HuskyXbox;

public class WeaponsXboxController extends HuskyXbox{
    public WeaponsXboxController(int port){
        super(port);
    }

    public double getLeftFB(){
        return getAxisValue(AXIS_LEFT_Y);
    }

    public double getRightTrigger(){
        return getAxisValue(AXIS_RIGHT_TRIGGER);
    }

    public double getRightFB(){
        return getAxisValue(AXIS_RIGHT_Y);
    }

    public boolean buttonYPressEvent(){
        return buttonPressEvent(3);
    }

    public boolean buttonXPressEvent(){
        return buttonPressEvent(2);
    }

    public boolean buttonBPressEvent(){
        return buttonPressEvent(1);
    }

    public boolean buttonAPressEvent(){
        return buttonPressEvent(0);
    }

    public boolean leftBumperPressEvent(){
        return buttonPressEvent(4);
    }

    public boolean rightBumperPressEvent(){
        return buttonPressEvent(5);
    }

    public boolean viewButtonPressEvent(){
        return buttonPressEvent(6);
    }

    public boolean menuPressEvent(){
        return buttonPressEvent(7);
    }
}
