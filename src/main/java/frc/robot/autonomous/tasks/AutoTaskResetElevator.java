package frc.robot.autonomous.tasks;

import frc.robot.Elevator;
import frc.robot.autonomous.AutonomousTaskBase;

// Dummy task to start off the task list that won't have any early initialization issues
public class AutoTaskResetElevator extends AutonomousTaskBase {

    private Elevator m_Elevator;


    public void setElevator(Elevator elevator){
        m_Elevator = elevator;
    }


    public AutoTaskResetElevator(){      
    }

    @Override
    public void TaskInitialize() {
        m_Elevator.resetElevator();
    }

    @Override
    public boolean CheckTask() {
        if (m_Elevator.inPosition()) {
            return true;
        }else{
            return false;
        }
    }
    
}
