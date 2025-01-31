package frc.robot;

import com.ctre.phoenix.motorcontrol.IFollower;


public class FRC2024TeleopDecisionMaker {
  private FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  private FRC2024WeaponsJoystick m_TheWeaponsJoystick = new FRC2024WeaponsJoystick();
  private AlgaeHandler m_AlgaeHandler;
  private CoralHandler m_CoralHandler;
  private Elevator m_Elevator;
  private FRC2024Chassis m_Chassis;

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


      if (m_TheWeaponsJoystick.button2Pushed()) {
        m_AlgaeHandler.moveAlgae();
      }
      if (m_TheWeaponsJoystick.button2ReleaseEvent()) {
        m_AlgaeHandler.idle();
      }
      
      if (m_TheWeaponsJoystick.triggerPushed()) {
        m_CoralHandler.moveCoral();
      }
      if (m_TheWeaponsJoystick.triggerReleaseEvent()) {
        m_CoralHandler.stop();
      }

      // If two button algae handling

      // if (m_TheWeaponsJoystick.button3Pushed()) {
      //   m_AlgaeHandler.suck();
      //   m_AlgaeHandler.stop();
      // }

      // if(m_TheWeaponsJoystick.button5Pushed()){
      //   m_AlgaeHandler.shoot();
      //   m_AlgaeHandler.stop();
      // }
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

}
