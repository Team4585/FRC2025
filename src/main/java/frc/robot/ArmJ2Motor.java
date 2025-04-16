package frc.robot;
import frc.robot.huskylib.src.RoboDevice;

public class ArmJ2Motor extends RoboDevice {
    private double initialPosition;
    private static double positionThreshold = 0.1;

    private boolean isInitialized;
    private boolean isWithinTarget;
    
    private BasicPID m_MasterController;
    
    
    public ArmJ2Motor(){
        super("ArmJ2Motor");
    
        m_MasterController = new BasicPID(WiringConnections.ARMJ2_MOTOR);

        initialPosition = m_MasterController.getPosition();
      }
    
      public void Initialize(){
      }

      public void moveJoint(double targetPosition, double speed){
        if (isInitialized != true){
            initialPosition = m_MasterController.getPosition();
            targetPosition = initialPosition;
            isInitialized = true;
        } else {
          if (posIsCorrect(targetPosition, m_MasterController.getPosition(), positionThreshold)){
            //System.out.println("Within bounds");
          }else{
            m_MasterController.setRotations(targetPosition);
          }
        }
      }

      private boolean posIsCorrect(double targetPos, double currentPos, double maxError){
        if (Math.abs(Math.abs(currentPos) - Math.abs(targetPos)) < maxError){
          isWithinTarget = true;
          return true;
        }else{
          isWithinTarget = false;
          return false;
        }
      }
    
      @Override
      public void doGatherInfo() {
        super.doGatherInfo();
        //System.out.println(m_MasterEncoder.getPosition());
      }
    
      @Override
      public void doActions() {
        super.doActions();
      }
}