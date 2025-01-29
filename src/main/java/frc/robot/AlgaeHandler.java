package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.WiringConnections;
import frc.robot.huskylib.src.*;
import frc.robot.huskylib.src.RoboDevice;

public class AlgaeHandler extends RoboDevice{

  private VictorSPX algaeMotor;

  public AlgaeHandler(){
    super("Algae Handler");

  }
  
  public void Initialize(){
    algaeMotor = new VictorSPX(WiringConnections.ALGAE_CIM);
  }

  public void suck(){
    algaeMotor.set(ControlMode.PercentOutput, 1);
  }

  public void unSuck(){
    algaeMotor.set(ControlMode.PercentOutput, -1);
  }
  public void stop(){
    algaeMotor.set(ControlMode.PercentOutput, 0);
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
