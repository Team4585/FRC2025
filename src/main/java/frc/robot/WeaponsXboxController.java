package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class WeaponsXboxController {
    private static final int XBOX_CONTROLLER_PORT = 0;
    private final XboxController Xbox;

    public WeaponsXboxController() {
       Xbox = new XboxController(XBOX_CONTROLLER_PORT);
    }

    public double getLeftFB() {
        return Xbox.getLeftY();
    }

    public double getRightTrigger() {
        return Xbox.getRightTriggerAxis();
    }

    public double getRightFB() {
        return Xbox.getRightY();
    }

    public boolean buttonYPressEvent() {
        return Xbox.getYButtonPressed();
    }

    public boolean buttonXPressEvent() {
        return Xbox.getXButtonPressed();
    }

    public boolean buttonBPressEvent() {
        return Xbox.getBButtonPressed();
    }

    public boolean buttonAPressEvent() {
        return Xbox.getAButtonPressed();
    }

    public boolean leftBumperPressEvent() {
        return Xbox.getLeftBumperButtonPressed();
    }

    public boolean rightBumperPressEvent() {
        return Xbox.getRightBumperButtonPressed();
    }

    public boolean viewButtonPressEvent() {
        return Xbox.getBackButtonPressed();
    }

    public boolean menuPressEvent() {
        return Xbox.getStartButtonPressed();
    }
}
