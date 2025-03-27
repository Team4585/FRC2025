package frc.robot.huskylib.src;

public class ReallyBasicPID {
    private double prevError;

    private final double kp;
    private final double ki;
    private final double kd;
    private final double dt;

    public ReallyBasicPID(double kp, double ki, double kd, double dt) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
    }

    public double calculatePID(double target, double measuredOutput) {
        double error;
        double integral;
        double derivative;

        error = target - measuredOutput;
        integral = error * dt;
        derivative = (error - prevError) / dt;
        prevError = error;

        return (kp * error) + (ki * integral) + (kd * derivative);
    }
}
