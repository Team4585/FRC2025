package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.huskylib.src.*;
import frc.robot.huskylib.src.RoboDevice;


public class Elevator extends RoboDevice{

  private SparkMax elevatorMotor;
  private RelativeEncoder elevatorEncoder;
  private SparkClosedLoopController elevatorPID;
  private SparkMaxConfig elevatorConfig;

  private double targPos;
  

  public Elevator(){
    super("Elevator");
  }

  public void Initialize(){
    elevatorMotor = new SparkMax(12, MotorType.kBrushless);
    elevatorEncoder = elevatorMotor.getEncoder();
    elevatorPID = elevatorMotor.getClosedLoopController();
    elevatorConfig = new SparkMaxConfig();

    targPos =  elevatorEncoder.getPosition();
    //targPos += 50;
    
    elevatorConfig.closedLoop.pid(0.01, 0, 0);
    elevatorConfig.closedLoop.maxOutput(1);
    elevatorConfig.closedLoop.minOutput(-1);

    elevatorMotor.configure(elevatorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void elevate(double moveSpeed){
    targPos += moveSpeed * 50;

    elevatorPID.setReference(targPos, ControlType.kPosition);
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
