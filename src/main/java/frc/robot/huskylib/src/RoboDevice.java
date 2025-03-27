package frc.robot.huskylib.src;

import java.util.ArrayList;

public class RoboDevice {
    private static final ArrayList<RoboDevice> m_allParts = new ArrayList<>();

    private Boolean m_debugModeOn = false;
    private String m_deviceName = "";
    private final ArrayList<RoboDevice> m_ChildDevices;

    public RoboDevice(String deviceName) {
        m_deviceName = deviceName;
        m_ChildDevices = new ArrayList<>();
    }

    public RoboDevice AddChildDevice(RoboDevice newChild) {
        RoboDevice.removePart(newChild);
        m_ChildDevices.add(newChild);
        return newChild;
    }

    private void doChildrenActions() {
        for (RoboDevice Child : m_ChildDevices) {
            Child.doActions();
        }
    }

    private void doChildrenTeleopInit() {
        for (RoboDevice Child : m_ChildDevices) {
            Child.doTeleopInit();
        }
    }

    private void doChildrenAutonomousInit() {
        for (RoboDevice Child : m_ChildDevices) {
            Child.doAutonomousInit();
        }
    }

    public void doTeleopInit() {
        doChildrenTeleopInit();
    }

    public void doAutonomousInit() {
        doChildrenAutonomousInit();
    }

    public void doActions() {
        // System.out.println("Do Actions for " + getDeviceName());
        doChildrenActions();
    }

    public String getDeviceName() {
        return m_deviceName;
    }

    public Boolean debugModeOn() {
        return m_debugModeOn;
    }

    public void setDebugModeOn() {
        m_debugModeOn = true;
    }

    public void setDebugModeOff() {
        m_debugModeOn = false;
    }

    public void toggleDebugMode() {
        m_debugModeOn = !m_debugModeOn;
    }

    // ================================================================================

    public static void addPart(RoboDevice e) {
        RoboDevice.m_allParts.add(e);
    }

    public static void removePart(RoboDevice e) {
        RoboDevice.m_allParts.remove(e);
    }

    public static ArrayList<RoboDevice> getParts() {
        return (m_allParts);
    }

    public static void doActionsAll() {
        for (RoboDevice r : RoboDevice.getParts()) {
            r.doActions();
        }
    }
    // ================================================================================
}
