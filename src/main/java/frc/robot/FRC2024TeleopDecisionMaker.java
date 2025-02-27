package frc.robot;



public class FRC2024TeleopDecisionMaker {
  private FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  private FRC2024WeaponsJoystick m_TheWeaponsJoystick = new FRC2024WeaponsJoystick();
  private AlgaeHandler m_AlgaeHandler;
  private CoralHandler m_CoralHandler;
  private Elevator m_Elevator;
  private FRC2024Chassis m_Chassis;
  private Lifter m_Lifter;

  boolean isFieldOriented = false;

  FRC2024TeleopDecisionMaker(){

  }

  public void initialize(){
  }

  public void doDecisions(){

    m_Chassis.setTargSpeed(m_TheJoystick.getForwardBackwardValue(),
      -m_TheJoystick.getSideToSideValue(),
      -m_TheJoystick.getTwistValue(),
      isFieldOriented
      );

      m_Chassis.stabilize(isFieldOriented);

      m_Elevator.elevate(
        m_TheWeaponsJoystick.getForwardBackwardValue());
      
      if (m_TheJoystick.button7PressEvent()) {
        if (isFieldOriented) {
          isFieldOriented = false;
        }else{
          isFieldOriented = true;
        }
      }

      if (m_TheJoystick.button9PressEvent()) {
        m_Chassis.resetPigeon();
      }

// if one button

      m_AlgaeHandler.switchStop();

      if (m_TheWeaponsJoystick.button2PressEvent()) {
        m_AlgaeHandler.moveAlgae();
      }
      if (m_TheWeaponsJoystick.button2ReleaseEvent()) {
        m_AlgaeHandler.stop();
      }
      
      m_CoralHandler.switchStop();

      if (m_TheWeaponsJoystick.triggerPressEvent()) {
        if (m_Elevator.inPosition()) {
          m_CoralHandler.moveCoral();
        }
      }

      if (m_TheWeaponsJoystick.triggerReleaseEvent()) {
        m_CoralHandler.stop();
      }

      if (m_TheWeaponsJoystick.button3PressEvent()) {
        m_Elevator.fixedElevate();
      }

      if (m_TheWeaponsJoystick.button5Pushed()) {
        m_Lifter.lift();
      }

  }

  public void setChassis(FRC2024Chassis theChassis){
    m_Chassis = theChassis;
  }

  public void setAlgaeHandler(AlgaeHandler theHandler){
    m_AlgaeHandler = theHandler;
  }

  public void setCoralHandler(CoralHandler coralHandler){
    m_CoralHandler = coralHandler;
  }

  public void setElevator(Elevator elevator){
    m_Elevator = elevator;
  }

  public void setLifter(Lifter lifter){
    m_Lifter = lifter;
  }

  
}
