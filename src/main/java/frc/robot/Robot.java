package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.huskylib.src.HuskyRobot;

public class Robot extends HuskyRobot {
  private static final String K_DEFAULT_AUTO = "Default";
  private static final String K_CUSTOM_AUTO = "My Auto";
  private String autoSelected;
  private final SendableChooser<String> chooser = new SendableChooser<>();

  private final FRC2024TeleopDecisionMaker TeleopDecider = new FRC2024TeleopDecisionMaker();
  private final FRC2024AutonomousDecisionMaker AutoDecider = new FRC2024AutonomousDecisionMaker();

  private final FRC2024Chassis TheChassis = new FRC2024Chassis();

  // private AlgaeHandler AlgaeHandler = new AlgaeHandler();
  private final CoralHandler CoralHandler = new CoralHandler();
  private final Elevator Elevator = new Elevator();
  // private AlgaeLifter AlgaeLifter = new AlgaeLifter();

  @Override
  public void robotInit() {
    chooser.setDefaultOption("Default Auto", K_DEFAULT_AUTO);
    chooser.addOption("My Auto", K_CUSTOM_AUTO);
    SmartDashboard.putData("Auto choices", chooser);

    TeleopDecider.initialize();
    AutoDecider.initialize();

    TheChassis.Initialize();
    Elevator.Initialize();
    CoralHandler.Initialize();
    // AlgaeHandler.Initialize();
    // AlgaeLifter.Initialize();

    TeleopDecider.setChassis(TheChassis);
    // TeleopDecider.setAlgaeHandler(AlgaeHandler);
    TeleopDecider.setCoralHandler(CoralHandler);
    TeleopDecider.setElevator(Elevator);
    // TeleopDecider.setAlgaeLifter(AlgaeLifter);

    AutoDecider.setChassis(TheChassis);
    AutoDecider.setCoralHandler(CoralHandler);
    AutoDecider.setElevator(Elevator);
  }

  @Override
  public void autonomousInit() {
    autoSelected = chooser.getSelected();
    // ? Replace with Dashboard Output
    System.out.println("Auto selected: " + autoSelected);
  }

  @Override
  public void doAutonomousDecisions() {
    AutoDecider.doDecisions();
  }

  @Override
  public void doTeleopDecisions() {
     TeleopDecider.doDecisions();
  }
}
