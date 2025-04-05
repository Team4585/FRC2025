package frc.robot;

import frc.robot.huskylib.src.RoboDevice;


import com.ctre.phoenix.motorcontrol.ControlMode.*;
import com.revrobotics.*;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import org.opencv.core.Mat;

/**
 * A PID-controlled SPARK MAX that allows rotational target control for motors
 */
public class BasicPID extends RoboDevice{
  
  private SparkMax m_motor;
  private RelativeEncoder m_encoder;
  private SparkClosedLoopController m_pidController;
  private SparkMaxConfig m_pidConfig;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  public double rotations;
  private double m_currentPosition = 0.0;

  /**
   * Constructs a new SPARK MAX motor controller with the specified CAN ID.
   * Confirm that the motor controller's CAN ID is the same as the int passed here using REV Hardware Client
   * @param CANPort the CAN ID of the the SPARK MAX
   */
  private int CANDeviceID;

  public BasicPID(int CANPort){
    super("BasicPID Sub System");
    CANDeviceID = CANPort;

    //Setting up devices
    m_motor = new SparkMax(CANPort, MotorType.kBrushless);
    m_encoder = m_motor.getEncoder();

    m_pidController = m_motor.getClosedLoopController();
    m_pidConfig = new SparkMaxConfig();

    //Setting up PID values
    kP = 0.1;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 0.5;
    kMinOutput = -0.5;
    
    

    m_pidConfig.closedLoop.pid(kP, kI, kD);
    m_pidConfig.closedLoop.maxOutput(kMaxOutput);
    m_pidConfig.closedLoop.minOutput(kMinOutput);
  }

  public void Initialize(){
  }

  @Override
  public void doGatherInfo(){
    super.doGatherInfo();
    m_currentPosition = m_encoder.getPosition();
  }

  @Override
  public void doActions(){
    super.doActions();
    
     
    //Magic line of code that gets it going!
    m_pidController.setReference(rotations, SparkMax.ControlType.kPosition);
  }

  /**
   * Gets the position of the motor, in rotations (I think)
   * @return the current position of the motor
   */
  public double getPosition(){
    return m_currentPosition;
  }

  //Setters
  //Attach a call to this method to an action on the joystick
  /**
   * Sets the target for the motor to rotate to
   * @param percentoutput the position to rotate to and hold
   */
  public void setRotations(double percentoutput){
    rotations = percentoutput;
  }
}