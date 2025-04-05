package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.huskylib.src.HuskyRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends HuskyRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final FRC2024TeleopDecisionMaker m_TeleopDecider = new FRC2024TeleopDecisionMaker();
  private final FRC2024AutonomousDecisionMaker m_AutoDecider = new FRC2024AutonomousDecisionMaker();

  private final FRC2024Chassis m_TheChassis = new FRC2024Chassis();

  // private AlgaeHandler m_AlgaeHandler = new AlgaeHandler();
  private final CoralIntakeMotor m_CoralHandler = new CoralIntakeMotor();
  private final Elevator m_Elevator = new Elevator();
  // private AlgaeLifter m_AlgaeLifter = new AlgaeLifter();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    m_TeleopDecider.initialize();
    m_AutoDecider.initialize();

    m_TheChassis.Initialize();
    m_Elevator.Initialize();
    m_CoralHandler.Initialize();
    // m_AlgaeHandler.Initialize();
    // m_AlgaeLifter.Initialize();

    m_TeleopDecider.setChassis(m_TheChassis);
    // m_TeleopDecider.setAlgaeHandler(m_AlgaeHandler);
    m_TeleopDecider.setCoralHandler(m_CoralHandler);
    m_TeleopDecider.setElevator(m_Elevator);
    // m_TeleopDecider.setAlgaeLifter(m_AlgaeLifter);

    m_AutoDecider.setChassis(m_TheChassis);
    m_AutoDecider.setCoralHandler(m_CoralHandler);
    m_AutoDecider.setElevator(m_Elevator);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    super.autonomousInit();
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  public void doAutonomousDecisions() {
    m_AutoDecider.doDecisions();
  }

  // Called periodically during autonomous.
  @Override
  public void autonomousPeriodic() {
    super.autonomousPeriodic();
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }
  }

  // Called once when teleop is enabled.
  @Override
  public void teleopInit() {
    super.teleopInit();
  }

  public void doTeleopDecisions() {
     m_TeleopDecider.doDecisions();
  }

  // Called periodically during operator control.
  @Override
  public void teleopPeriodic() {
    super.teleopPeriodic();
  }

  // Called once when the robot is disabled.
  @Override
  public void disabledInit() {
    super.disabledInit();
  }

  // Called periodically when disabled.
  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();
  }

  // Called once when test mode is enabled.
  @Override
  public void testInit() {
    super.testInit();
  }

  // Called periodically during test mode.
  @Override
  public void testPeriodic() {
    super.testPeriodic();
  }
}
