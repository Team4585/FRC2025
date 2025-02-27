package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.huskylib.src.RoboDevice;


public class Elevator extends RoboDevice{

  private SparkMax elevatorMotor;
  private RelativeEncoder elevatorEncoder;
  private SparkClosedLoopController elevatorPID;
  private SparkMaxConfig elevatorConfig;

  private double targPos;
//TODO set correct positions
  private final double HIGH_POS = 0;
  private final double MID_POS = 0;
  private final double LOW_POS = 0;
  private final double TRAY_POS = 0;
  private final double RESET_POS = 0;


  private int count = 0;

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

  public void highCoral(){
    elevatorPID.setReference(HIGH_POS, ControlType.kPosition);
    count = 4;
  }

  private void midCoral(){
    elevatorPID.setReference(MID_POS, ControlType.kPosition);
  }

  private void lowCoral(){
    elevatorPID.setReference(LOW_POS, ControlType.kPosition);
  }

  private void trayCoral(){
    elevatorPID.setReference(TRAY_POS, ControlType.kPosition);
  }

  public void resetElevator(){
    elevatorPID.setReference(RESET_POS, ControlType.kPosition);
    count = 0;
  }

  public boolean inPosition(){
    switch (count) {
      case 1:
        return elevatorEncoder.getPosition() == TRAY_POS;
      case 2:
        return elevatorEncoder.getPosition() == LOW_POS;
      case 3:
        return elevatorEncoder.getPosition() == MID_POS;
      case 4:
        return elevatorEncoder.getPosition() == HIGH_POS;
      case 5:
        return elevatorEncoder.getPosition() == RESET_POS;
      default:
        return false;
    }
  }

  public void fixedElevate(){
    count++;

    switch (count) {
      case 1:
        trayCoral();
        break;
      case 2:
        lowCoral();
        break;
      case 3:
        midCoral();
        break;
      case 4:
        highCoral();
        break;
      case 5:
        resetElevator();
        count = 0;
        break;
      default:
        resetElevator();
        count = 0;
        break;
    }
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
