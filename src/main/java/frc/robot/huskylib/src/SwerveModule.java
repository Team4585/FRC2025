package frc.robot.huskylib.src;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class SwerveModule extends RoboDevice{
    // Constants
    private static final double WHEEL_DIAMETER = 0.0762; // 3 inches in meters
    private static final double DRIVE_GEAR_RATIO = 4.71;
    private static final double STEER_GEAR_RATIO = 41.25;
    private static final double MAX_SPEED = 3.0; // meters per second

    // Hardware
    private final SparkMax driveMotor;
    private final SparkMax steerMotor;
    private SparkMaxConfig driveConfig;
    private SparkMaxConfig steerConfig;
    private final RelativeEncoder driveEncoder;
    private final RelativeEncoder steerEncoder;
    private final SparkClosedLoopController steerPid;
    private final String moduleName;

    public SwerveModule(int driveMotorId, int steerMotorId, String moduleName) {
        super("Swerve Module");
        this.moduleName = moduleName;

        // Configure drive motor
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        driveConfig = new SparkMaxConfig();
        driveEncoder = driveMotor.getEncoder();

        // Configure steering motor
        steerMotor = new SparkMax(steerMotorId, MotorType.kBrushless);
        steerConfig = new SparkMaxConfig();
        steerEncoder = steerMotor.getEncoder();
        steerPid = steerMotor.getClosedLoopController();

        // Configure PID
        steerConfig.closedLoop.pid(0.5, 0.0, 0.0);
        steerConfig.closedLoop.maxOutput(1);
        steerConfig.closedLoop.minOutput(-1);
        //steerPIDController.setOutputRange(-1, 1);

        // Set conversion factors
        double driveConversionFactor = (Math.PI * WHEEL_DIAMETER) / DRIVE_GEAR_RATIO;
        //driveEncoder.setPositionConversionFactor(driveConversionFactor);
        //driveEncoder.setVelocityConversionFactor(driveConversionFactor / 60.0);
        driveConfig.encoder.positionConversionFactor(driveConversionFactor);
        driveConfig.encoder.velocityConversionFactor(driveConversionFactor/60.0);

        double steerConversionFactor = 360.0 / STEER_GEAR_RATIO;
        //steerEncoder.setPositionConversionFactor(steerConversionFactor);
        //steerEncoder.setVelocityConversionFactor(steerConversionFactor / 60.0);
        steerConfig.encoder.positionConversionFactor(steerConversionFactor);
        steerConfig.encoder.velocityConversionFactor(steerConversionFactor/60.0);

        // Save configurations
        //driveMotor.burnFlash();
        //steerMotor.burnFlash();
        driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        steerMotor.configure(steerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Log configuration
        SmartDashboard.putString(moduleName + " Status", "Initialized");
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
                driveEncoder.getVelocity(), // in meters per second
                Rotation2d.fromDegrees(steerEncoder.getPosition()) // Use fromDegrees since our position is in degrees
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // Create current state
        SwerveModuleState state = new SwerveModuleState();
        state.speedMetersPerSecond = desiredState.speedMetersPerSecond;
        state.angle = desiredState.angle;
        
        // Call optimize on the instance
        state.optimize(Rotation2d.fromDegrees(steerEncoder.getPosition()));
    
        // Set drive speed
        driveMotor.set(state.speedMetersPerSecond / MAX_SPEED);
        
        // Set steering angle
        steerPid.setReference(
                state.angle.getDegrees(),
                SparkMax.ControlType.kPosition
        );
    
        // Optional: Add telemetry
        SmartDashboard.putNumber(moduleName + " Target Angle", state.angle.getDegrees());
        SmartDashboard.putNumber(moduleName + " Current Angle", steerEncoder.getPosition());
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                driveEncoder.getPosition(), // Already in meters
                Rotation2d.fromDegrees(steerEncoder.getPosition()) // Use fromDegrees since our position is in degrees
        );
    }

    public void setBrakeMode(boolean brake) {
        IdleMode mode = brake ? IdleMode.kBrake : IdleMode.kCoast;
        driveConfig.idleMode(mode);
        steerConfig.idleMode(mode);
    }

    public void stop() {
        driveMotor.set(0);
        steerMotor.set(0);
    }
}