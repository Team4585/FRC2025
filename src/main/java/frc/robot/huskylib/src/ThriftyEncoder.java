package frc.robot.huskylib.src;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

// The ThriftyEncoder class contains fields and methods pertaining to the function of the absolute encoder.
public class ThriftyEncoder {
	private final AnalogInput analogInput;
	private boolean inverted;
	private double positionOffset;

	public ThriftyEncoder(int port) {
		this.analogInput = new AnalogInput(port);
		this.inverted = false;
		this.positionOffset = 0.0;
	}

	// Returns the current raw position of the absolute encoder.
	public double getPosition() {
		return (inverted ? -1.0 : 1.0) * ((analogInput.getAverageVoltage() / RobotController.getVoltage5V()) * (Math.PI * 2) - Math.PI);
	}

	// Inverts the absolute encoder.
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	// Sets the position offset between the raw position and the virtual position.
	public void setPositionOffset(double offset) {
		positionOffset = offset;
	}

	// Returns the position offset between the raw position and the virtual
	// position.
	public double getPositionOffset() {
		return positionOffset;
	}

	// Returns the virtual position of the absolute encoder (raw position minus
	// offset).
	public double getVirtualPosition() {
		return getPosition() - positionOffset;
	}

	// Resets the virtual position to the current raw position.
	public void resetVirtualPosition() {
		positionOffset = getPosition();
	}
}
