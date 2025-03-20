package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.huskylib.src.RoboDevice;

public class Lifter extends RoboDevice{
  private RelativeEncoder lifterEncoder;
  private SparkClosedLoopController lifterPID;
  private SparkMaxConfig lifterConfig;
  private SparkMax lifterMotor;

  private double targPos;

  public Lifter(){
    super("Lifter");
  }

  public void Initialize(){
    lifterMotor = null; //new SparkMax(13, MotorType.kBrushless);

    //lifterEncoder = lifterMotor.getEncoder();
    //lifterPID = lifterMotor.getClosedLoopController();
    lifterConfig = new SparkMaxConfig();

    targPos =  lifterEncoder.getPosition();
    //targPos += 50;

    lifterConfig.closedLoop.pid(0.01, 0, 0);
    lifterConfig.closedLoop.maxOutput(1);
    lifterConfig.closedLoop.minOutput(-1);

    //lifterMotor.configure(lifterConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void lift(){
    targPos += 50;
    lifterPID.setReference(targPos, ControlType.kPosition);
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
