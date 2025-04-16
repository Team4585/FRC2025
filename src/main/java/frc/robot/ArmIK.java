package frc.robot;
/**
 * Calculates the inverse kinematics for the 2025 arm (arm on elevator)
 * Joint1 attaches the elevator to the first arm segment
 * Joint2 attaches the first arm segment to the end-effector
 */
public class ArmIK {
    private final double l1;
    private final double l2;
    private final double hmin;
    private final double hmax;

    /**
     * New arm go brrr
     * These are unitless, however for simplicity put all the lengths in meters. All values should have the same unit
     * @param l1 length of the first arm segment (attached to elevator)
     * @param l2 length of the second arm segment (attached to first segment)
     * @param hmin minimum elevator height
     * @param hmax maximum elevator height
     */
    public ArmIK(double l1, double l2, double hmin, double hmax) {
        this.l1 = l1;
        this.l2 = l2;
        this.hmin = hmin;
        this.hmax = hmax;
    }

    /**
     * Calculates the inverse kinematics and returns useful info
     * @param x target X-position of the arm end-effector (horizontal) (same units as constructor)
     * @param y target Y-position of the arm end-effector (vertical) (same units as constructor)
     * @param targetRotation target rotation from horizontal of the end-effector joint (radians)
     * @return 0: target elevator position (same unit as constructor); 1: target joint1 angle (radians); 2: target joint2 angle (radians)
     */
    public double[] calculateIK(double x, double y, double targetRotation) {
        double y1 = Math.max(hmin, Math.min(hmax, y));
        double ydiff = y1 - y;

        double h = l1 * Math.sin(Math.acos(Math.max(-1, Math.min(1, x / l1))));
        double theta1 = Math.acos(Math.max(-1, Math.min(1, (h + ydiff) / l1)));
        double theta2 = theta1 - Math.PI / 2;


        double[] returnVal = new double[3];
        returnVal[0] = Math.max(-hmin, Math.min(hmax, (h + y1)));
        returnVal[1] = theta1;
        returnVal[2] = theta2 + targetRotation;

        return returnVal;
    }
}
