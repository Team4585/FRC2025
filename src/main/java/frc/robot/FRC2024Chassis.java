package frc.robot;

//import frc.robot.huskylib.src.SwerveDriveTrain;
import frc.robot.huskylib.src.SwerveDriveSubsystem;
import frc.robot.huskylib.src.RoboDevice;
//MIGHT NEED
//TO INITIALIZE
//THE SWERVE
//SUBSYSTEM
//MANUALLY
public class FRC2024Chassis extends RoboDevice{

  private SwerveDriveSubsystem m_driveTrain = new SwerveDriveSubsystem();

  // private HuskyVector2D m_currentLocation;
  // private double m_currentHeading = 0.0;
  // private HuskyVector2D m_currentVelocity;

  public FRC2024Chassis(){
    super("FRC2024Chassis");

    AddChildDevice(m_driveTrain);

    // m_currentLocation = new HuskyVector2D();
    // m_currentVelocity = new HuskyVector2D();
  
  }

  public void Initialize(){
  }
/* 
  public void setTargForwardBack(double targFB){
    m_driveTrain.setTargSpeed(targFB);
  }

  public void setTargRotation(double targRot){
    m_driveTrain.setTargRotationSpeed(targRot);
  }
*/

  public void setTargSpeed(double targFB, double targSS,double targRot, boolean fieldRelative){
    m_driveTrain.drive(targFB, targSS, targRot, fieldRelative);
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

    //m_currentVelocity.setVals(0.0, m_driveTrain.getCurrentSpeed());
   // m_currentVelocity.rotate(m_currentHeading);

   // m_currentLocation.addVec(m_currentVelocity);
  }
/* 
  public HuskyVector2D getCurrentLocation(){
    return m_currentLocation;
  }

  public HuskyVector2D getCurrentVelocity(){
    return m_currentVelocity;
  }

  public double getCurrentHeading(){
    return m_currentHeading;
  }

  public double getLeftPosition() {
    return m_driveTrain.getCurrentLeftPosition();
  }

  public double getRightPosition() {
    return m_driveTrain.getCurrentRightPosition();
  }
*/

  @Override
  public void doActions() {
    super.doActions();
  }

}
