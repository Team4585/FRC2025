package frc.robot.huskylib.devices;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.huskylib.src.RoboDevice;

public class HuskyXbox extends RoboDevice {
  private static final double DEFAULT_DEADZONE_VALUE = 0.15;
  private static final int DEFAULT_BUTTON_COUNT = 16;
  private static final int DEFAULT_AXIS_COUNT = 6;

  protected static final int AXIS_LEFT_X = 0;
  protected static final int AXIS_LEFT_Y = 1;
  protected static final int AXIS_RIGHT_X = 4;
  protected static final int AXIS_RIGHT_Y = 5;

  protected static final int AXIS_RIGHT_TRIGGER = 3;
  protected static final int AXIS_LEFT_TRIGGER = 2;

  protected static final double DEFAULT_STICK_DEADZONE = 0.1;
  protected static final double DEFAULT_TRIGGER_DEADZONE = 0.1;

  // ===========================================

  private class ButtonInfo {
    private int m_buttonNumber = 0;
    private Boolean m_isPushed = false;
    private Boolean m_justPressed = false;
    private Boolean m_justReleased = false;

    public ButtonInfo(int buttonIndex) {
      m_buttonNumber = buttonIndex + 1;
    }

    public void ReadValue() {
      Boolean prevValue = m_isPushed;
      m_isPushed = m_joystick.getRawButton(m_buttonNumber);
      if (m_isPushed != prevValue) {
        if (m_isPushed) {
          m_justPressed = true;
        } else {
          m_justReleased = true;
        }
      } else {
        m_justPressed = false;
        m_justReleased = false;
      }
    }

    public Boolean isPushed() {
      return m_isPushed;
    }

    public Boolean justPressed() {
      return m_justPressed;
    }

    public Boolean justReleased() {
      return m_justReleased;
    }
  }

  // ===========================================

  private class AxisInfo {
    private int m_axisIndex = 0;
    private double m_deadZone = DEFAULT_DEADZONE_VALUE;
    private double m_currentValue = 0.0;

    AxisInfo(int axisIndex, double deadZoneValue) {
      m_axisIndex = axisIndex;
      m_deadZone = deadZoneValue;
    }

    public void ReadValue() {
      double rawValue = m_joystick.getRawAxis(m_axisIndex);
      m_currentValue = (Math.abs(rawValue) > m_deadZone) ? rawValue : 0.0;
    }

    public void setDeadZoneValue(double newDeadZone) {
      m_deadZone = newDeadZone;
    }

    public double getValue() {
      return m_currentValue;
    }
  }

  // ===========================================

  private final Joystick m_joystick;

  private final int m_buttonCount = DEFAULT_BUTTON_COUNT;
  private final ButtonInfo[] m_buttons;

  private final int m_axisCount = DEFAULT_AXIS_COUNT;
  private final AxisInfo[] m_axes;

  private int m_povValue;

  public HuskyXbox(int port) {
    super("HuskyXbox" + port);

    m_joystick = new Joystick(port);

    m_buttons = new ButtonInfo[m_buttonCount];
    for (int buttonIndex = 0; buttonIndex < m_buttonCount; buttonIndex++) {
      m_buttons[buttonIndex] = new ButtonInfo(buttonIndex);
    }

    m_axes = new AxisInfo[m_axisCount];
    m_axes[AXIS_LEFT_TRIGGER] = new AxisInfo(AXIS_LEFT_TRIGGER, DEFAULT_TRIGGER_DEADZONE);
    m_axes[AXIS_RIGHT_TRIGGER] = new AxisInfo(AXIS_RIGHT_TRIGGER, DEFAULT_TRIGGER_DEADZONE);
    m_axes[AXIS_LEFT_X] = new AxisInfo(AXIS_LEFT_X, DEFAULT_STICK_DEADZONE);
    m_axes[AXIS_LEFT_Y] = new AxisInfo(AXIS_LEFT_Y, DEFAULT_STICK_DEADZONE);
    m_axes[AXIS_RIGHT_X] = new AxisInfo(AXIS_RIGHT_X, DEFAULT_STICK_DEADZONE);
    m_axes[AXIS_RIGHT_Y] = new AxisInfo(AXIS_RIGHT_Y, DEFAULT_STICK_DEADZONE);
  }

  @Override
  public void doGatherInfo() {
    for (int buttonIndex = 0; buttonIndex < m_buttonCount; buttonIndex++) {
      m_buttons[buttonIndex].ReadValue();
    }

    for (int axisIndex = 0; axisIndex < m_axisCount; axisIndex++) {
      m_axes[axisIndex].ReadValue();
    }

    m_povValue = m_joystick.getPOV();
  }

  protected Boolean isButtonPushed(int checkButton) {
    return m_buttons[checkButton].isPushed();
  }

  protected Boolean buttonPressEvent(int checkButton) {
    return m_buttons[checkButton].justPressed();
  }

  protected Boolean buttonReleaseEvent(int checkButton) {
    return m_buttons[checkButton].justReleased();
  }

  public int getButtonCount() {
    return m_buttonCount;
  }

  public int getPOV() {
    return m_povValue;
  }

  public void setDeadZoneValue(int axisIndex, double deadZoneValue) {
    m_axes[axisIndex].setDeadZoneValue(deadZoneValue);
  }

  protected double getAxisValue(int axisIndex) {
    return m_axes[axisIndex].getValue();
  }
}