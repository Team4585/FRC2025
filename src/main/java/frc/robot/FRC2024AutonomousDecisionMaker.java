package frc.robot;

import java.util.List;


import frc.robot.autonomous.AutonomousTaskBase;
import frc.robot.autonomous.AutonomousTaskDispatcher;
import frc.robot.autonomous.tasks.AutoTaskDriveForward;
import frc.robot.autonomous.tasks.AutoTaskHighCoral;
import frc.robot.autonomous.tasks.AutoTaskResetElevator;
import frc.robot.autonomous.tasks.AutoTaskStartSequence;

public class FRC2024AutonomousDecisionMaker {
  private List<AutonomousTaskBase> m_TaskList;
  private AutonomousTaskDispatcher m_autoTaskDispatcher;

  private FRC2024Chassis m_Chassis;
  private Elevator m_Elevator;
  private CoralHandler m_CoralHandler;

  private AutoTaskDriveForward autoStartForward = new AutoTaskDriveForward(2, -0.2);
  //private AutoTaskDriveForward autoMoveOutOfWay = new AutoTaskDriveForward(4.5, -.3);
  private AutoTaskHighCoral autoCoral = new AutoTaskHighCoral();
  private AutoTaskResetElevator autoresetElevator = new AutoTaskResetElevator();

  // Auto initialization
  FRC2024AutonomousDecisionMaker(){
   // m_TaskList = List.of(new AutoTaskTickCount(25), new AutoTaskHalt());

   /**
    * Wheeeeeeeeeee auto logic:
    * 1. Set shooter speed
      2. Wait 2s
      3. Set intake on
      4. Wait 1s
      5. Set intake off
      6. Set shooter speed 0
      7. Set intake on
      8. Drive forwards 1.2m
      9. Set intake off
      10. Drive backwards 1.2m
      11. Set shooter speed
      12. Wait 2s
      13. Set intake on
      14. Wait 1s
      15. Set shooter speed 0
      16. Wait 1s
      17. Set intake off
      18. Drive forwards 1.2m
    */

   //System.out.println("Initializing list");
   AutonomousTaskBase startTask = new AutoTaskStartSequence();

   m_TaskList = List.of(
    startTask,
    autoStartForward
   );

    m_autoTaskDispatcher = new AutonomousTaskDispatcher(m_TaskList);
  }

  public void initialize(){
    m_autoTaskDispatcher.resetAuto();
  }

  public void doDecisions(){
    m_autoTaskDispatcher.RunAutoTask();
    //System.out.println("Entering autonomous decisions");
  }

  public void setChassis(FRC2024Chassis chassis){
    m_Chassis = chassis;
    autoStartForward.setChassis(m_Chassis);
  }


  public void setCoralHandler(CoralHandler handler){
    m_CoralHandler = handler;
    autoCoral.setCoralHandler(m_CoralHandler);
  }

  public void setElevator(Elevator elevator){
    m_Elevator = elevator;
    autoCoral.setElevator(m_Elevator);
    autoresetElevator.setElevator(m_Elevator);
  }
}
