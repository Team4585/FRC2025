package frc.robot.autonomous.tasks;

import frc.robot.FRC2024Chassis;
import frc.robot.autonomous.AutonomousTaskBase;

// Dummy task to start off the task list that won't have any early initialization issues
public class AutoTaskDriveForward extends AutonomousTaskBase {

    private FRC2024Chassis m_Chassis;
    //TODO find proper distance
    private final double DISTANCE_NEEDED = 0;


    public void setChassis(FRC2024Chassis chassis){
        m_Chassis = chassis;
    }

    public AutoTaskDriveForward(){      
    }

    @Override
    public void TaskInitialize() {
        m_Chassis.setTargSpeed(1, 0, 0, true);
    }

    @Override
    public boolean CheckTask() {
        if (m_Chassis.getDrivePos() == DISTANCE_NEEDED) {
            m_Chassis.setTargSpeed(0, 0, 0, true);
            return true;
        }else{
            return false;
        }
    }
    
}
