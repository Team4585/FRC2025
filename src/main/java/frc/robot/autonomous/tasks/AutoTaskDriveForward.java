package frc.robot.autonomous.tasks;

import frc.robot.FRC2024Chassis;
import frc.robot.autonomous.AutonomousTaskBase;

// Dummy task to start off the task list that won't have any early initialization issues
public class AutoTaskDriveForward extends AutonomousTaskBase {

    private FRC2024Chassis m_Chassis;
    //TODO find proper distance
    private double distanceNeeded;
    private double chassisSpeed;


    public void setChassis(FRC2024Chassis chassis){
        m_Chassis = chassis;
    }

    public AutoTaskDriveForward(double distance, double speed){      
        chassisSpeed = speed;
        distanceNeeded = distance;
    }

    @Override
    public void TaskInitialize() {
        m_Chassis.setTargSpeed(chassisSpeed, 0, 0, true);
    }

    @Override
    public boolean CheckTask() {
        System.out.println(m_Chassis.getDrivePos());
        if (m_Chassis.getDrivePos() <= distanceNeeded) {
            m_Chassis.setTargSpeed(0, 0, 0, true);
            return true;
        }else{
            return false;
        }
    }
    
}
