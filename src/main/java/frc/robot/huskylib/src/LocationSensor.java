package frc.robot.huskylib.src;

public interface LocationSensor {
  public void Initialize();

  public HuskyVector2D getCurrentLocation();
  public double getCurrentX();
  public double getCurrentY();
}
