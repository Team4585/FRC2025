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

public class AlgaeLifter extends RoboDevice{
  private SparkMax algaeLifterMotor;
  private SparkMaxConfig algaeLifterConfig;
  private SparkClosedLoopController algaeLifterPID;
  private RelativeEncoder algaeLifterEncoder;

  private final double DEPLOYED_POS = 5;
  private boolean isUp;

  public AlgaeLifter(){
    super("Algae Handler");
  }

  public void Initialize(){
    algaeLifterMotor = new SparkMax(14, MotorType.kBrushless);
    algaeLifterConfig = new SparkMaxConfig();
    algaeLifterPID = algaeLifterMotor.getClosedLoopController();
    algaeLifterEncoder = algaeLifterMotor.getEncoder();

    isUp = true;

    algaeLifterConfig.closedLoop.pid(.01,0,0)
    .maxOutput(1)
    .minOutput(-1);

    algaeLifterMotor.configure(algaeLifterConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void reelIn(){
    algaeLifterPID.setReference(0, ControlType.kPosition);
  }

  public void checkIfUp(){
    if (algaeLifterEncoder.getPosition() == 0) {
      isUp = true;
    }
    if (algaeLifterEncoder.getPosition() == DEPLOYED_POS) {
      isUp = false;
    }
  }

  public void deploy(){
    algaeLifterPID.setReference(DEPLOYED_POS, ControlType.kPosition);
  }

  public void move(){
    if (isUp) {
      deploy();
    }
    else{
      reelIn();
    }
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
