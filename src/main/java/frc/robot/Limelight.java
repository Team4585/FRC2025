package frc.robot;

import frc.robot.huskylib.src.RoboDevice;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends RoboDevice{

  private NetworkTable networkTable;
  private boolean limelightIsConnected = false;

  public Limelight(){
    super("Limelight Sub System");
    networkTable = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public void Initialize(){
    //Change the modes if needed (look at limelight API for de)
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<ledMode>").setNumber(3);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<camMode>").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<stream>").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<snapshot>").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<pipeline>").setNumber(0);

    
    System.out.println("Initialized!!!!!");
    networkTable = NetworkTableInstance.getDefault().getTable("limelight");
    if (networkTable != null){
      System.out.println("Not null!");
    } else {
      System.out.println("Table is null!");
    }
  }

  @Override
  public void doGatherInfo() {
    super.doGatherInfo();

  }

  @Override
  public void doActions() {
    super.doActions();
  }

  public boolean isConnected() {
    return limelightIsConnected;
  }

  //make sure to set the target
  public boolean isTargetFound() {
      NetworkTableEntry tv = networkTable.getEntry("tv");
      double v = tv.getDouble(0);
      if (v == 0.0f) {
        return false;
      } else {
        return true;
      }
  }

  //tx horizontal offset from crosshair to target (-27 to 27 degrees)
  public double getDegHorizontalFromTarget() {
    NetworkTableEntry tx = networkTable.getEntry("tx");

    double x = tx.getDouble(0.0);
    
    return x;
  }

  //ty vertical offset from crosshair to target (-20.5 to 20.5 degrees)
  public double getDegVerticalFromTarget() {
    NetworkTableEntry ty = networkTable.getEntry("ty");
    double y = ty.getDouble(0.0);
    return y;
  }
  
  //ta target area (0% to 100% of the of the image)
  public double getTargetArea() {
    NetworkTableEntry ta = networkTable.getEntry("ta");
    double a = ta.getDouble(0.0);
    return a;
  }

  //ts shew or rotation (-90 to 90 degrees)
  public double getSkewOrRotation() {
    NetworkTableEntry ts = networkTable.getEntry("ts");
    double s = ts.getDouble(0.0);
    return s;
  }

  //tl the pipeline's latency contribution (ms); add at least 11 ms for image capture latency
  public double getPipelineLatency() {
    NetworkTableEntry tl = networkTable.getEntry("tl");
    double pl = tl.getDouble(0.0);
    return pl;
  }

  //Set pipeline from 0-9
  public void setPipeline(Integer pipeline) {
    if (pipeline < 0) {
      pipeline = 0;
      throw new IllegalArgumentException("Illegal pipeline: the pipeline can't be less than zero");
    } else if (pipeline > 9) {
      pipeline = 0;
      throw new IllegalArgumentException("Illegal pipeline: the pipeline can't be greater than 9");
    }
    networkTable.getEntry("pipeline").setValue(pipeline);
  }

  //Returns current pipeline index
  public double getPipelineIndex() {
    NetworkTableEntry getpipe = networkTable.getEntry("getpipe");
    double pipeLine = getpipe.getDouble(0.0);
    return pipeLine;
  }

}