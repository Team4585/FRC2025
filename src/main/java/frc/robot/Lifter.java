package frc.robot;

import frc.robot.huskylib.src.RoboDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Lifter extends RoboDevice {
  private VictorSPX lifterMotor1;
  private VictorSPX lifterMotor2;
  
  private double speed = 0.5;

  public Lifter() {
    super("Lifter");
  }

  public void Initialize() {
    lifterMotor1 = new VictorSPX(WiringConnections.LIFTER_MOTOR_1);
    lifterMotor2 = new VictorSPX(WiringConnections.LIFTER_MOTOR_2);
  }

  public void lift() {
    lifterMotor1.set(ControlMode.PercentOutput, speed);
    lifterMotor2.set(ControlMode.PercentOutput, -speed);
  }

  public void drop() {
    lifterMotor1.set(ControlMode.PercentOutput, -speed);
    lifterMotor2.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void doGatherInfo() {
    super.doGatherInfo();
  }

  @Override
  public void doActions() {
    super.doActions();
  }
}
