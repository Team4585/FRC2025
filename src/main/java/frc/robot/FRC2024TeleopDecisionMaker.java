package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FRC2024TeleopDecisionMaker {
  // private AlgaeLifter m_AlgaeLifter;
  // private FRC2024WeaponsJoystick m_TheWeaponsJoystick = new
  // FRC2024WeaponsJoystick();
  // private AlgaeHandler m_AlgaeHandler;

  private final FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  private final WeaponsXboxController m_weaponsController = new WeaponsXboxController(1);
  private CoralHandler m_CoralHandler;
  private Elevator m_Elevator;
  private FRC2024Chassis m_Chassis;
  private Limelight m_Limelight = new Limelight();

  private Pose3d llTranslationData;
  private double[] llRotationData;

  private double align_rotateSpeed = 0.05; // spin speed
  private double align_translateSpeed = 0.008; // xy robot speed multiplier
  private double align_translateMaxSpeed = 0.15; // xy robot max speed
  private double align_leftReefOffset = -14; // offset for the left reef post
  private double align_rightReefOffset = 14; // offset for the right reef post
  private double align_targetArea = 50; // visual area % of apriltag

  boolean slowDriving = false;
  boolean isFieldOriented = true;

  FRC2024TeleopDecisionMaker() {
  }

  public void initialize() {
  }

  public void doGatherInfo() {
  }

  public void doDecisions() {
    llTranslationData = LimelightHelpers.getTargetPose3d_CameraSpace("limelight");
    llRotationData = LimelightHelpers.getCameraPose_TargetSpace("limelight");

    if (m_TheJoystick.button8PressEvent()) {
      slowDriving = !slowDriving;
    }

    m_Chassis.stabilize(isFieldOriented);

    m_Elevator.elevate(1, -m_weaponsController.getRightFB());

    /* Old limelight
    System.out.println("llrot  "+ llRotationData);
    System.out.println("lltransl X "+ llTranslationData.getX());
    System.out.println("lltransl Y "+ llTranslationData.getY());
    */

    SmartDashboard.putNumber("DegHoriz", m_Limelight.getDegHorizontalFromTarget());
    SmartDashboard.putNumber("degVertic", m_Limelight.getDegVerticalFromTarget());
    SmartDashboard.putNumber("degRot", m_Limelight.getSkewOrRotation());
    SmartDashboard.putBoolean("isTargetFound", m_Limelight.isTargetFound());

    // Debug limelight:
    //System.out.println("isTargetFound: " + m_Limelight.isTargetFound());
    //System.out.println("degHoriz: " + m_Limelight.getDegHorizontalFromTarget());
    //System.out.println("degVertic: " + m_Limelight.getDegVerticalFromTarget());
    //System.out.println("degRot: " + m_Limelight.getSkewOrRotation());
    
    // Activate autoalign when POV pushed right (90°), else normal chassis
    // POV:: The POV angles start at 0 in the up direction, and increase clockwise (e.g. right is 90, upper-left is 315).
    if (m_TheJoystick.getPOV() >= 45 && m_TheJoystick.getPOV() < 180) {
      double offsetHorizontal = m_Limelight.getDegHorizontalFromTarget() + align_rightReefOffset;
      /* Old limelight
      if (LimelightHelpers.getTV("limelight")) {
        double rotate = 0.03 * llRotationData[4];
        if (Math.abs(rotate) < 0.1) rotate = 0;
        double velocityY = (Math.abs(llRotationData[4]) > 1) ? 0 : -llTranslationData.getX();
        double velocityX = (Math.abs(llTranslationData.getX()) > 1) ? 0 : llTranslationData.getY();
        m_Chassis.setTargSpeed(velocityX, velocityY, rotate, isFieldOriented);
        System.out.println("POV 90deg");
      }
        */

        if (m_Limelight.isTargetFound()) {

          double rotate = 0; //align_rotateSpeed * m_Limelight.getSkewOrRotation();
          double velocityY = offsetHorizontal * -align_translateSpeed;
          double velocityX = 0; //(align_targetArea - m_Limelight.getTargetArea()) * align_translateSpeed;

          //if (Math.abs(rotate) < 0.05) rotate = 0;

          velocityY = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, velocityY));
          rotate = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, rotate));

        if (Math.abs(velocityY) < 0.02) velocityY = 0;

          m_Chassis.setTargSpeed(velocityX + m_TheJoystick.getForwardBackwardValue(), velocityY, -rotate -m_TheJoystick.getTwistValue(), isFieldOriented); //post to chassis

          System.out.println("POV 90deg-ish: " + m_Limelight.getSkewOrRotation());
        }
    } else if (m_TheJoystick.getPOV() >= 225 && m_TheJoystick.getPOV() < 335) {
      double offsetHorizontal = m_Limelight.getDegHorizontalFromTarget() + align_leftReefOffset;

      if (m_Limelight.isTargetFound()) {

        double rotate = 0; //align_rotateSpeed * m_Limelight.getSkewOrRotation();
        double velocityY = offsetHorizontal * -align_translateSpeed;
        double velocityX = 0; //(align_targetArea - m_Limelight.getTargetArea()) * align_translateSpeed;

        velocityY = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, velocityY));
        rotate = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, rotate));

        if (Math.abs(velocityY) < 0.02) velocityY = 0;

        m_Chassis.setTargSpeed(velocityX + m_TheJoystick.getForwardBackwardValue(), velocityY, -rotate -m_TheJoystick.getTwistValue(), isFieldOriented); //post to chassis

        System.out.println("POV 270deg-ish: " + m_Limelight.getSkewOrRotation());
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
