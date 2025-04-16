package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.huskylib.src.RoboDevice;

public class CoralIntakeMotor extends RoboDevice {
  private SparkMax coralMotor;
  private DigitalInput coralSensor;

  private boolean holdingCoral;

  public CoralIntakeMotor() {
    super("Coral Handler");
  }

  public void Initialize() {
    coralMotor = new SparkMax(WiringConnections.CORAL_MOTOR, MotorType.kBrushless);
    coralSensor = new DigitalInput(WiringConnections.CORAL_SWITCH_DIO);
    holdingCoral = false;
  }

  public void moveCoral() {
    move();
  }

  public void switchStop() {
    if (!coralSensor.get()) {
      if (!holdingCoral) {
        stop();
        holdingCoral = true;
      }
    }
  }

  public void moveCoral(double speed) {
    coralMotor.set(speed);
  }

  public void stop() {
    stopMotor();
    holdingCoral = false;
  }

  private void move() {
    coralMotor.set(1);
  }

  private void stopMotor() {
    coralMotor.set(0);
  }
}
