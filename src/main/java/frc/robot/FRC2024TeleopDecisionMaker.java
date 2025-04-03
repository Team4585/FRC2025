package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FRC2024TeleopDecisionMaker {
  private final FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  // private FRC2024WeaponsJoystick m_TheWeaponsJoystick = new
  // FRC2024WeaponsJoystick();
  // private AlgaeHandler m_AlgaeHandler;
  private final WeaponsXboxController m_weaponsController = new WeaponsXboxController(1);
  private CoralHandler m_CoralHandler;
  private Elevator m_Elevator;
  private FRC2024Chassis m_Chassis;
  // private AlgaeLifter m_AlgaeLifter;

  private Pose3d llTranslationData;
  private double[] llRotationData;

  boolean slowDriving = false;
  boolean isFieldOriented = true;

  FRC2024TeleopDecisionMaker() {
  }

  public void initialize() {
  }

  public void doGatherInfo() {
    llTranslationData = LimelightHelpers.getTargetPose3d_CameraSpace("limelight");
    llRotationData = LimelightHelpers.getCameraPose_TargetSpace("limelight");
  }

  public void doDecisions() {

    if (m_TheJoystick.button8PressEvent()) {
      slowDriving = !slowDriving;
    }

    m_Chassis.stabilize(isFieldOriented);

    m_Elevator.elevate(1, -m_weaponsController.getRightFB());

    // Activate autoalign when POV pushed right (90°)
    if (m_TheJoystick.getPOV() == 90) {
      if (LimelightHelpers.getTV("limelight")) {
        double rotate = 0.03 * llRotationData[4];
        if (Math.abs(rotate) < 0.1) rotate = 0;
        double velocityY = (Math.abs(llRotationData[4]) > 1) ? 0 : -llTranslationData.getX();
        double velocityX = (Math.abs(llTranslationData.getX()) > 1) ? 0 : llTranslationData.getY();
        m_Chassis.setTargSpeed(velocityX, velocityY, rotate, isFieldOriented);
      }
    } else {

    if (slowDriving) {
      m_Chassis.setTargSpeed(
          (m_TheJoystick.getForwardBackwardValue() / 4),
          -(m_TheJoystick.getSideToSideValue() / 4),
          -m_TheJoystick.getTwistValue(),
          isFieldOriented);
    }

    if (!slowDriving) {
      m_Chassis.setTargSpeed(
          m_TheJoystick.getForwardBackwardValue(),
          -m_TheJoystick.getSideToSideValue(),
          -m_TheJoystick.getTwistValue(),
          isFieldOriented);
    }
    }

      /*
    if (m_TheJoystick.button7PressEvent()) {
      if (isFieldOriented) {
        isFieldOriented = false;
      } else {
        isFieldOriented = true;
      }
    }*/

    if (m_TheJoystick.button9PressEvent()) {
      m_Chassis.resetPigeon();
    }

    if (m_weaponsController.rightBumperPressEvent()) {
      m_Elevator.resetElevator();
    }

    if (m_weaponsController.buttonAPressEvent()) {
      m_Elevator.trayCoral();
    }

    if (m_weaponsController.buttonBPressEvent()) {
      m_Elevator.lowCoral();
    }

    if (m_weaponsController.buttonXPressEvent()) {
      m_Elevator.midCoral();
    }

    if (m_weaponsController.buttonYPressEvent()) {
      m_Elevator.highCoral();
    }

    m_CoralHandler.moveCoral(m_weaponsController.getLeftFB() / 20 + m_weaponsController.getRightTrigger() / -5);

    SmartDashboard.putNumber("ElevEncoder: ", m_Elevator.targPos);

    // m_AlgaeHandler.switchStop();

    // if (m_TheWeaponsJoystick.button2PressEvent()) {
    // m_AlgaeHandler.moveAlgae();
    // }
    // if (m_TheWeaponsJoystick.button2ReleaseEvent()) {
    // m_AlgaeHandler.stop();
    // }

    // m_CoralHandler.switchStop();

    // if (m_TheWeaponsJoystick.triggerPressEvent()) {
    // if (m_Elevator.inPosition()) {
    // m_CoralHandler.moveCoral();
    // }
    // }

    // if (m_TheWeaponsJoystick.triggerReleaseEvent()) {
    // m_CoralHandler.stop();
    // }

    // if (m_TheWeaponsJoystick.button3PressEvent()) {
    // m_Elevator.fixedElevate();
    // }

    // m_AlgaeLifter.checkIfUp();
    // if (m_TheWeaponsJoystick.button5PressEvent()) {
    // m_AlgaeLifter.move();
    // }
  }

  public void setChassis(FRC2024Chassis theChassis) {
    m_Chassis = theChassis;
  }

  // public void setAlgaeHandler(AlgaeHandler theHandler){
  // m_AlgaeHandler = theHandler;
  // }

  public void setCoralHandler(CoralHandler coralHandler) {
    m_CoralHandler = coralHandler;
  }

  public void setElevator(Elevator elevator) {
    m_Elevator = elevator;
  }

  // public void setAlgaeLifter(AlgaeLifter lifter){
  // m_AlgaeLifter = lifter;
  // }
}
