package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.WiringConnections;
import frc.robot.huskylib.src.*;
import frc.robot.huskylib.src.RoboDevice;

public class CoralHandler extends RoboDevice{

  private SparkMax coralMotor;

  private boolean holdingCoral;

  public CoralHandler(){
    super("Coral Handler");

  }
  
  public void Initialize(){
    coralMotor = new SparkMax(WiringConnections.CORAL_MOTOR, MotorType.kBrushless);
    holdingCoral = false;
  }

  public void moveCoral(){
    if (holdingCoral) {
      outtake();
    }else{
      intake();
    }
  }

  public void stop(){
    stopMotor();
    holdingCoral = !holdingCoral;
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
