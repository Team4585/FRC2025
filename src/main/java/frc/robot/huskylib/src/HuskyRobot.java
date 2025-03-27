package frc.robot.huskylib.src;

import edu.wpi.first.wpilibj.TimedRobot;

public abstract class HuskyRobot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  abstract public void doTeleopDecisions();

  abstract public void doAutonomousDecisions();

  // This function is called periodically during autonomous.
  @Override
  public void autonomousPeriodic() {
    RoboDevice.doGatherInfoAll();
    doAutonomousDecisions();
    RoboDevice.doActionsAll();
  }

  // This function is called once when teleop is enabled.
  @Override
  public void teleopInit() {
  }

  // This function is called periodically during operator control.
  @Override
  public void teleopPeriodic() {
    RoboDevice.doGatherInfoAll();
    doTeleopDecisions();
    RoboDevice.doActionsAll();
  }

  // This function is called once when the robot is disabled.
  @Override
  public void disabledInit() {
  }

  // This function is called periodically when disabled.
  @Override
  public void disabledPeriodic() {
  }

  // This function is called once when test mode is enabled.
  @Override
  public void testInit() {
  }

  // This function is called periodically during test mode.
  @Override
  public void testPeriodic() {
  }
}