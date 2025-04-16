package frc.robot;

import java.security.cert.X509CRL;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FRC2024TeleopDecisionMaker {
  private final FRC2024Joystick m_TheJoystick = new FRC2024Joystick();
  private final WeaponsXboxController m_weaponsController = new WeaponsXboxController();
  private Elevator m_Elevator;
  private FRC2024Chassis m_Chassis;
  private Limelight m_Limelight = new Limelight();

  private ArmJ1Motor armJ1Motor = new ArmJ1Motor();
  private ArmJ2Motor armJ2Motor = new ArmJ2Motor();
  private SpinnerCollectorMotor spinnerCollectorMotor = new SpinnerCollectorMotor();
  private CoralIntakeMotor coralIntakeMotor;

  // Lets do meters here!
  private ArmIK armIK = new ArmIK(0.325, 3.14, 0.2, 1.8);
  private double armSpeed = 0.5;

  private double armJ1GearTune = 5;
  private double armJ2GearTune = 5;

  // Still in meters! (well, then degrees for the last one haha)
  // height, distance forward, end-effector rotation (rads)
  // TODO: PLEASE TUNE!!
  private final double[] GROUND_POSITIONS = {0.1, 0.8, 0};
  private final double[] REEF_1_POSITIONS = {0.4, 0.4, 0};
  private final double[] REEF_2_POSITIONS = {0.4, 0.4, 0};
  private final double[] REEF_3_POSITIONS = {0.4, 0.4, 0};
  private final double[] REEF_4_POSITIONS = {0.4, 0.4, 0};

  private double[] currentPositions = GROUND_POSITIONS;

  private Pose3d llTranslationData;
  private double[] llRotationData;

  private double align_rotateSpeed = 0.05; // Spin speed
  private double align_translateSpeed = 0.008; // XY robot speed multiplier
  private double align_translateMaxSpeed = 0.15; // XY robot max speed
  private double align_leftReefOffset = -14; // Left reef post offset
  private double align_rightReefOffset = 14; // Right reef post offset
  private double align_targetArea = 50; // Target area % of AprilTag

  private boolean slowDriving = false;
  private boolean isFieldOriented = true;

  FRC2024TeleopDecisionMaker() {}

  public void initialize() {}

  public void doGatherInfo() {}

  public void doDecisions() {
    llTranslationData = LimelightHelpers.getTargetPose3d_CameraSpace("limelight");
    llRotationData = LimelightHelpers.getCameraPose_TargetSpace("limelight");

    if (m_TheJoystick.button8PressEvent()) {
      slowDriving = !slowDriving;
    }

    m_Chassis.stabilize(isFieldOriented);
    m_Elevator.elevate(1, -m_weaponsController.getRightFB());

    SmartDashboard.putNumber("DegHoriz", m_Limelight.getDegHorizontalFromTarget());
    SmartDashboard.putNumber("degVertic", m_Limelight.getDegVerticalFromTarget());
    SmartDashboard.putNumber("degRot", m_Limelight.getSkewOrRotation());
    SmartDashboard.putBoolean("isTargetFound", m_Limelight.isTargetFound());

    handleJoystickInput();
    handleElevatorControls(); //disable for arm IK
    handleCoral();
    //handleIK(); //disable for old output
  }

  private void handleJoystickInput() {
    if (m_TheJoystick.getPOV() >= 45 && m_TheJoystick.getPOV() < 180) {
      isFieldOriented = false;
      m_Chassis.stabilize(isFieldOriented);
      autoAlignRightReef();
    } else if (m_TheJoystick.getPOV() >= 225 && m_TheJoystick.getPOV() < 335) {
      isFieldOriented = false;
      m_Chassis.stabilize(isFieldOriented);
      autoAlignLeftReef();
    } else {
      isFieldOriented = true;
      m_Chassis.stabilize(isFieldOriented);
      manualDriving();
    }
  }

  private void autoAlignRightReef() {
    double offsetHorizontal = m_Limelight.getDegHorizontalFromTarget() + align_rightReefOffset;

    if (m_Limelight.isTargetFound()) {
      double rotate = 0;
      double velocityY = offsetHorizontal * -align_translateSpeed;
      velocityY = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, velocityY));

      if (Math.abs(velocityY) < 0.02) velocityY = 0;
      m_Chassis.setTargSpeed(0 + m_TheJoystick.getForwardBackwardValue(), velocityY, -rotate - m_TheJoystick.getTwistValue(), isFieldOriented);
    }
  }

  private void autoAlignLeftReef() {
    double offsetHorizontal = m_Limelight.getDegHorizontalFromTarget() + align_leftReefOffset;

    if (m_Limelight.isTargetFound()) {
      double rotate = 0;
      double velocityY = offsetHorizontal * -align_translateSpeed;
      velocityY = Math.max(-align_translateMaxSpeed, Math.min(align_translateMaxSpeed, velocityY));

      if (Math.abs(velocityY) < 0.02) velocityY = 0;
      m_Chassis.setTargSpeed(0 + m_TheJoystick.getForwardBackwardValue(), velocityY, -rotate - m_TheJoystick.getTwistValue(), isFieldOriented);
    }
  }

  private void manualDriving() {
    double forwardBackwardValue = m_TheJoystick.getForwardBackwardValue();
    double sideToSideValue = m_TheJoystick.getSideToSideValue();
    double twistValue = m_TheJoystick.getTwistValue();

    if (slowDriving) {
      m_Chassis.setTargSpeed(forwardBackwardValue / 4, -sideToSideValue / 4, -twistValue, isFieldOriented);
    } else {
      m_Chassis.setTargSpeed(forwardBackwardValue, -sideToSideValue, -twistValue, isFieldOriented);
    }
  }

  private void handleElevatorControls() {
    if (m_weaponsController.rightBumperPressEvent()) {
      m_Elevator.resetElevator();
    }

    if (m_weaponsController.buttonAPressEvent()) {
      m_Elevator.trayCoral();
    } else if (m_weaponsController.buttonBPressEvent()) {
      m_Elevator.lowCoral();
    } else if (m_weaponsController.buttonXPressEvent()) {
      m_Elevator.midCoral();
    } else if (m_weaponsController.buttonYPressEvent()) {
      m_Elevator.highCoral();
    }
  }

  private void handleIK() {
    if (m_weaponsController.rightBumperPressEvent()) {
      m_Elevator.resetElevator();
      currentPositions = GROUND_POSITIONS;
    }

    if (m_weaponsController.buttonAPressEvent()) {
      currentPositions = REEF_1_POSITIONS;
    } else if (m_weaponsController.buttonBPressEvent()) {
      currentPositions = REEF_2_POSITIONS;
    } else if (m_weaponsController.buttonXPressEvent()) {
      currentPositions = REEF_3_POSITIONS;
    } else if (m_weaponsController.buttonYPressEvent()) {
      currentPositions = REEF_4_POSITIONS;
    }

    double[] values = armIK.calculateIK(currentPositions[0], currentPositions[1], currentPositions[2]);

    m_Elevator.goToHeight(values[0], 0.2);
    armJ1Motor.moveJoint(values[1] * armJ1GearTune, armSpeed);
    armJ2Motor.moveJoint(values[2] * armJ2GearTune, armSpeed);
  }

  private void handleCoral() {
    coralIntakeMotor.moveCoral(m_weaponsController.getLeftFB() / 20 + m_weaponsController.getRightTrigger() / -5);
    spinnerCollectorMotor.spin(m_weaponsController.getLeftFB() / 20 + m_weaponsController.getRightTrigger() / -5);
  }

  public void setChassis(FRC2024Chassis theChassis) {
    m_Chassis = theChassis;
  }

  public void setCoralHandler(CoralIntakeMotor coralHandler) {
    coralIntakeMotor = coralHandler;
  }

  public void setElevator(Elevator elevator) {
    m_Elevator = elevator;
  }
}
