package frc.robot;

import com.ctre.phoenix.motorcontrol.IFollower;


public class FRC2024TeleopDecisionMaker {
  private FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  private FRC2024WeaponsJoystick m_TheWeaponsJoystick = new FRC2024WeaponsJoystick();
  private AlgaeHandler m_AlgaeHandler;

  private boolean holdingAlgae = false;

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
        if (holdingAlgae) {
          m_AlgaeHandler.suck();
        }else{
          m_AlgaeHandler.unSuck();
        }
      }else{
        m_AlgaeHandler.stop();
        if (m_TheWeaponsJoystick.button2ReleaseEvent()) {
          if (holdingAlgae) {
            holdingAlgae = false;
          }else{
            holdingAlgae = true;
          }
          
        }
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


}
