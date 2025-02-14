package frc.robot.autonomous.tasks;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.CoralHandler;
import frc.robot.Elevator;
import frc.robot.autonomous.AutonomousTaskBase;

// Dummy task to start off the task list that won't have any early initialization issues
public class AutoTaskHighCoral extends AutonomousTaskBase {

    private Elevator m_Elevator;
    private CoralHandler m_CoralHandler;

    private Timer m_Timer = new Timer();

    public void setElevator(Elevator elevator){
        m_Elevator = elevator;
    }

    public void setCoralHandler(CoralHandler handler){
        m_CoralHandler = handler;
    }

    public AutoTaskHighCoral(){      
    }

    @Override
    public void TaskInitialize() {
        m_Elevator.highCoral();
    }

    @Override
    public boolean CheckTask() {
        if (m_Elevator.inPosition()) {
            m_Timer.start();
            m_CoralHandler.moveCoral();
            if (m_Timer.hasElapsed(.5)) {
                m_Timer.reset();
                m_CoralHandler.stop();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
}
