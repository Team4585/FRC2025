package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.huskylib.src.RoboDevice;

public class SpinnerCollectorMotor extends RoboDevice {
    private double initialPosition;
    private static double positionThreshold = 0.1;

    private boolean isInitialized;
    private boolean isWithinTarget;

    private VictorSPX motorController;
    
    
    public SpinnerCollectorMotor(){
        super("SpinnerCollectorMotor");
    
        motorController = new VictorSPX(WiringConnections.ARMJ2_MOTOR);

      }
    
      public void Initialize(){
      }

      public void spin(double speed) {
        motorController.set(ControlMode.PercentOutput, speed);
      }
    
      @Override
      public void doGatherInfo() {
        super.doGatherInfo();
      }
    
      @Override
      public void doActions() {
        super.doActions();
      }
}