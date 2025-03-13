package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.huskylib.src.RoboDevice;


public class Elevator extends RoboDevice{

  private SparkMax elevatorMotor;
  private RelativeEncoder elevatorEncoder;
  private SparkClosedLoopController elevatorPID;
  private SparkMaxConfig elevatorConfig;

  public double targPos;
  private double initialPos;
  private double encoderDeadzone = 0.1;
//TODO set correct positions
  private final double HIGH_POS = 9.65;
  private final double MID_POS = 4.82;
  private final double LOW_POS = 2.05;
  private final double TRAY_POS = 1;
  private final double RESET_POS = 0.1;


  private int count = 0;

  public Elevator(){
    super("Elevator");
  }

  public void Initialize(){
    elevatorMotor = new SparkMax(WiringConnections.ELEVATOR_MOTOR, MotorType.kBrushless);
    elevatorEncoder = elevatorMotor.getEncoder();
    elevatorPID = elevatorMotor.getClosedLoopController();
    elevatorConfig = new SparkMaxConfig();

    targPos = elevatorEncoder.getPosition();
    initialPos = elevatorEncoder.getPosition();
    //targPos += 50;
    
    elevatorConfig.closedLoop.pid(0.8, 0, 0.45);
    elevatorConfig.closedLoop.maxOutput(0.75);
    elevatorConfig.closedLoop.minOutput(-0.75);

    elevatorMotor.configure(elevatorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void highCoral(){
    targPos = initialPos + HIGH_POS;
    elevate(0.2, 0);
    //elevatorPID.setReference(initialPos + HIGH_POS, ControlType.kPosition);
    count = 4;
  }

  public void midCoral(){
    targPos = initialPos + MID_POS;
    elevate(0.2, 0);
    //elevatorPID.setReference(initialPos + MID_POS, ControlType.kPosition);
    count = 3;
  }

  public void lowCoral(){
    targPos = initialPos + LOW_POS;
    elevate(0.2, 0);
    //elevatorPID.setReference(initialPos + LOW_POS, ControlType.kPosition);
    count = 2;
  }

  public void trayCoral(){
    targPos = initialPos + TRAY_POS;
    elevate(0.2, 0);
    //elevatorPID.setReference(initialPos + TRAY_POS, ControlType.kPosition);
    count = 1;
  }

  public void resetElevator(){
    targPos = initialPos + RESET_POS;
    elevate(0.2, 0);
    //elevatorPID.setReference(initialPos + RESET_POS, ControlType.kPosition);
    count = 0;
  }

  public boolean inPosition(){
    return (elevatorEncoder.getPosition() - targPos) >= encoderDeadzone;
    
    /*
    switch (count) {
      case 1:
        return (elevatorEncoder.getPosition() - initialPos + TRAY_POS) >= encoderDeadzone;
      case 2:
        return (elevatorEncoder.getPosition() - initialPos + LOW_POS) >= encoderDeadzone;
      case 3:
        return (elevatorEncoder.getPosition() - initialPos + MID_POS) >= encoderDeadzone;
      case 4:
        return (elevatorEncoder.getPosition() - initialPos + HIGH_POS) >= encoderDeadzone;
      case 5:
        return (elevatorEncoder.getPosition() - initialPos + RESET_POS) >= encoderDeadzone;
      default:
        return false;
    }*/
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

  public void elevate(double moveSpeed, double joystickInput) {
    if (targPos >= HIGH_POS) {
      joystickInput = Math.min(-1, joystickInput);
    } else if (targPos <= RESET_POS) {
      joystickInput = Math.max(0, joystickInput);
    }

    targPos += joystickInput * 0.05;

    elevatorConfig.closedLoop.maxOutput(moveSpeed);
    elevatorConfig.closedLoop.minOutput(-moveSpeed);
    elevatorMotor.configure(elevatorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    elevatorPID.setReference(targPos, ControlType.kPosition);
    SmartDashboard.putNumber("Elev: ", targPos);
    SmartDashboard.putNumber("ElevPos: ", elevatorEncoder.getPosition());
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
