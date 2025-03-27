package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.huskylib.src.RoboDevice;

public class AlgaeHandler extends RoboDevice {

  private VictorSPX algaeMotor;
  private DigitalInput algaeSensor;

  private boolean holdingAlgae;

  public AlgaeHandler() {
    super("Algae Handler");
  }

  public void Initialize() {
    algaeMotor = new VictorSPX(WiringConnections.ALGAE_CIM);
    algaeSensor = new DigitalInput(WiringConnections.ALGAE_SWITCH_DIO);
    holdingAlgae = false;
  }

  public void moveAlgae() {
    if (holdingAlgae) {
      unSuck();
    } else {
      suck();
    }
  }

  public void switchStop() {
    if (!algaeSensor.get()) {
      if (!holdingAlgae) {
        stop();
        holdingAlgae = true;
      }
    }
  }

  public void idle() {
    if (holdingAlgae) {
      holdingAlgae = false;
      stop();
    } else {
      holdingAlgae = true;
      stop();
    }
  }

  public void suck() {
    algaeMotor.set(ControlMode.PercentOutput, 1);
  }

  public void unSuck() {
    algaeMotor.set(ControlMode.PercentOutput, -1);
  }

  public void stop() {
    algaeMotor.set(ControlMode.PercentOutput, 0);
    holdingAlgae = false;
  }

  public void keepAlgea() {
    algaeMotor.set(ControlMode.PercentOutput, .05);
  }
}