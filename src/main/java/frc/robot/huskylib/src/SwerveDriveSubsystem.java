package frc.robot.huskylib.src;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.WiringConnections;

public class SwerveDriveSubsystem extends RoboDevice {
    // Constants
    private static final double MAX_SPEED = 1.0; // meters per second
    private static final double WHEEL_BASE = 0.5; // meters
    private static final double TRACK_WIDTH = 0.5; // meters

    private double pitch;
    private double roll;

    // Point where modules will counteract tipping (degrees)
    private static final double TIP_POINT = 2;
    private static final double TIP_SPEED = .1;

    // Swerve Modules
    private SwerveModule frontLeft;
    private SwerveModule frontRight;
    private SwerveModule backLeft;
    private SwerveModule backRight;
    //x private final SwerveModule frontLeft;
    //x private final SwerveModule frontRight;
    //x private final SwerveModule backLeft;
    //x private final SwerveModule backRight;

    // Pigeon IMU
    private  Pigeon2 pigeon;

    // Kinematics and Odometry
    private  SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;
    //x private final SwerveDriveOdometry odometry;

    public SwerveDriveSubsystem() {
        super("Swerve Drive Subsystem");
    }

    public void Initialize(){
        // Initialize swerve modules
        frontLeft = new SwerveModule(WiringConnections.FRONT_LEFT_DRIVE_MOTOR_ID,
            WiringConnections.FRONT_LEFT_STEER_MOTOR_ID, true,
            "Front Left");
        frontLeft.Initialize();

        frontRight = new SwerveModule(WiringConnections.FRONT_RIGHT_DRIVE_MOTOR_ID,
            WiringConnections.FRONT_RIGHT_STEER_MOTOR_ID, false,
            "Front Right");
        frontRight.Initialize();

        backLeft = new SwerveModule(WiringConnections.BACK_LEFT_DRIVE_MOTOR_ID,
            WiringConnections.BACK_LEFT_STEER_MOTOR_ID, true,
            "Back Left");
        backLeft.Initialize();

        backRight = new SwerveModule(WiringConnections.BACK_RIGHT_DRIVE_MOTOR_ID,
            WiringConnections.BACK_RIGHT_STEER_MOTOR_ID, false,
            "Back Right");
        backRight.Initialize();

        // Initialize Pigeon IMU
        pigeon = new Pigeon2(WiringConnections.PIGEON_CAN_ID);
        pigeon.setYaw(0); // Reset yaw to 0

        // Define module locations relative to center of robot
        kinematics = new SwerveDriveKinematics(
            new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2),  // Front Left
            new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2), // Front Right
            new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2), // Back Left
            new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2) // Back Right
        );

        // Initialize odometry
         odometry = new SwerveDriveOdometry(
             kinematics,
             Rotation2d.fromDegrees(pigeon.getYaw().getValue().magnitude()),
             new SwerveModulePosition[] {
                 frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
             }
         );

        // Set modules to brake mode
        configureBrakeMode(true);
    }

    public void drive(double xSpeed, double ySpeed, double rotationSpeed, boolean fieldRelative) {
        // Get current module states
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotationSpeed,
                    Rotation2d.fromDegrees(pigeon.getYaw().getValue().magnitude()))
                : new ChassisSpeeds(xSpeed, ySpeed, rotationSpeed)
        );

        // Normalize wheel speeds
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_SPEED);

        // Set states to each module
        frontLeft.setDesiredState(states[0]);
        frontRight.setDesiredState(states[1]);
        backLeft.setDesiredState(states[2]);
        backRight.setDesiredState(states[3]);
    }

    public void stabilize(boolean fieldRelative){
       pitch = pigeon.getPitch().getValueAsDouble();
       roll = pigeon.getRoll().getValueAsDouble();

        if (pitch < -TIP_POINT) {
            drive(0, -TIP_SPEED, 0, false);
        }if (pitch > TIP_POINT) {
            drive(0, TIP_SPEED, 0, false);
        }if (roll < -TIP_POINT) {
            drive(-TIP_SPEED, 0, 0, false);
        }if (roll > TIP_POINT) {
            drive(TIP_SPEED, 0, 0, false);
        }
    }
    public double getDrivePos(){
       return frontLeft.getDrivePos();
    }

    @Override
    public void doGatherInfo() {
        super.doGatherInfo();
        // Update odometry
        odometry.update(
            Rotation2d.fromDegrees(pigeon.getYaw().getValue().magnitude()),
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
            }
        );

        // Update dashboard
        updateSmartDashboard();
    }

    public void resetPigeon(){
        pigeon.reset();
    }

    public void switchRelativity(){
        boolean driverOriented = true;
        if(driverOriented){} else {}
    }

    private void updateSmartDashboard() {
        Pose2d pose = odometry.getPoseMeters();
        SmartDashboard.putNumber("Robot X", pose.getX());
        SmartDashboard.putNumber("Robot Y", pose.getY());
        SmartDashboard.putNumber("Robot Rotation", pose.getRotation().getDegrees());
        SmartDashboard.putNumber("Pigeon Yaw", pigeon.getYaw().getValue().magnitude());
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(
            Rotation2d.fromDegrees(pigeon.getYaw().getValue().magnitude()),
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
            },
            pose
        );
    }

    private void configureBrakeMode(boolean brake) {
        frontLeft.setBrakeMode(brake);
        frontRight.setBrakeMode(brake);
        backLeft.setBrakeMode(brake);
        backRight.setBrakeMode(brake);
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }
}