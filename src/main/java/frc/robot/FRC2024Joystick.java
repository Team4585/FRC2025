package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class FRC2024Joystick {
  private static final int MAIN_JOYSTICK_PORT = 0;
  private final Joystick MainJoystick;

  private static final double GLOBAL_DEADZONE = 0.15;
  private static final int AXIS_SIDE_TO_SIDE = 0;
  private static final int AXIS_FORWARD_BACKWARD = 1;
  private static final int AXIS_TWIST = 2;

  private static final double FB_DEAD_ZONE = 0.2;
  private static final double FB_LIVE_ZONE = 1.0 - FB_DEAD_ZONE;

  private static final double SS_DEAD_ZONE = 0.2;
  private static final double SS_LIVE_ZONE = 1.0 - SS_DEAD_ZONE;

  private static final double ROT_DEAD_ZONE = 0.3;
  private static final double ROT_LIVE_ZONE = 1.0 - ROT_DEAD_ZONE;

  public FRC2024Joystick() {
    MainJoystick = new Joystick(MAIN_JOYSTICK_PORT);
  }

  private double getRawVal(int axis) {
    double RawValue = MainJoystick.getRawAxis(axis);
    return Math.abs((RawValue > GLOBAL_DEADZONE) ? RawValue : 0.0);
  }

  public double getForwardBackwardValue() {
    double RetVal = 0.0;
    double RawVal = getRawVal(AXIS_FORWARD_BACKWARD);

    if (RawVal > FB_DEAD_ZONE) {
      RetVal = RawVal - FB_DEAD_ZONE; // distance past dead zone
      RetVal /= FB_LIVE_ZONE; // scale to full range of live zone
      RetVal *= RetVal; // square it to make the line a curve rather than straight
      RetVal = Math.abs(RetVal); // Fix the sign
    }

    return RetVal;
  }

  public double getSideToSideValue() {
    double RetVal = 0.0;
    double RawVal = getRawVal(AXIS_SIDE_TO_SIDE);

    if (RawVal > SS_DEAD_ZONE) {
      RetVal = RawVal - SS_DEAD_ZONE; // distance past dead zone
      RetVal /= SS_LIVE_ZONE; // scale to full range of live zone
      RetVal *= RetVal; // square it to make the line a curve rather than straight
      RetVal = Math.abs(RetVal); // Fix the sign
    }

    return RetVal;
  }

  public double getTwistValue() {
    double RetVal = 0.0;
    double RawVal = getRawVal(AXIS_TWIST);

    if (RawVal > ROT_DEAD_ZONE) {
      RetVal = RawVal - ROT_DEAD_ZONE; // distance past dead zone
      RetVal /= ROT_LIVE_ZONE; // scale to full range of live zone
      RetVal = Math.abs(RetVal); // Fix the sign
    }

    return RetVal;
  }

  public Boolean triggerPushed() {
    return MainJoystick.getTrigger();
  }

  public Boolean triggerPressEvent() {
    return MainJoystick.getTriggerPressed();
  }

  public Boolean triggerReleaseEvent() {
    return MainJoystick.getTriggerReleased();
  }

  public boolean button7PressEvent() {
    return MainJoystick.getRawButtonPressed(6);
  }

  public boolean button8PressEvent() {
    return MainJoystick.getRawButtonPressed(7);
  }

  public boolean button9PressEvent() {
    return MainJoystick.getRawButtonPressed(8);
  }
}
