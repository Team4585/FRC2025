package frc.robot;

import frc.robot.huskylib.src.SwerveDriveSubsystem;
import frc.robot.huskylib.src.RoboDevice;


public class FRC2024Chassis extends RoboDevice{

  private SwerveDriveSubsystem m_driveTrain = new SwerveDriveSubsystem();


  public FRC2024Chassis(){
    super("FRC2024Chassis");

    AddChildDevice(m_driveTrain);
  }

  public void Initialize(){
    m_driveTrain.Initialize();
  }
  

  public void setTargSpeed(double targFB, double targSS,double targRot, boolean fieldRelative){
    m_driveTrain.drive(targFB, targSS, targRot, fieldRelative);
  }

  public void stabilize(boolean fieldRelative){
    m_driveTrain.stabilize(fieldRelative);
  }

  public double getDrivePos(){
    return m_driveTrain.getDrivePos();
  }

  public void resetPigeon(){
    m_driveTrain.resetPigeon();
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
