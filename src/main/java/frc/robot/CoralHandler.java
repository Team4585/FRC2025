package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.huskylib.src.RoboDevice;

public class CoralHandler extends RoboDevice{

  private SparkMax coralMotor;
  private DigitalInput coralSensor;

  private boolean holdingCoral;

  public CoralHandler(){
    super("Coral Handler");

  }
  
  public void Initialize(){
    coralMotor = new SparkMax(WiringConnections.CORAL_MOTOR, MotorType.kBrushless);
    coralSensor = new DigitalInput(0);
    holdingCoral = false;
  }

  public void moveCoral(){
    if (holdingCoral) {
      outtake();
    }else{
      intake();
    }
  }

  public void switchStop() {
    if (!coralSensor.get()) {
      if (!holdingCoral) {
      stop();
      holdingCoral = true;
      }
    }
  }

  
  public void stop(){
    stopMotor();
    holdingCoral = false;
  }

  private void intake(){
    coralMotor.set(1);
  }

  private void outtake(){
    coralMotor.set(-1);
  }
  
  private void stopMotor(){
    coralMotor.set(0);
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
