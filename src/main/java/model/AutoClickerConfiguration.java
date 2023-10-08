package model;

import java.io.Serializable;

public class AutoClickerConfiguration implements Serializable {
    public static final String TYPE_MOUSE = "Mouse";
    public static final String TYPE_KEYBOARD = "Keyboard";
    public static final String TYPE_HOLD = "Hold";
    public static final String TYPE_TOGGLE = "Toggle";

    private int inputCode; //mouse button code or key code
    private String inputDevice; //mouse/keyboard
    private int outputCode; //mouse button code or key code
    private String outputDevice; //mouse/keyboard
    private String activationType; //hold/toggle
    private int minCPS;
    private int maxCPS;

    public AutoClickerConfiguration(int inputCode, String inputDevice, int outputCode, String outputDevice, String activationType, int minCPS, int maxCPS) {
        this.inputCode = inputCode;
        this.outputCode = outputCode;
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.activationType = activationType;
        this.minCPS = minCPS;
        this.maxCPS = maxCPS;
    }

    public int getInputCode() {
        return inputCode;
    }

    public String getInputDevice() {
        return inputDevice;
    }

    public int getOutputCode() {
        return outputCode;
    }

    public String getOutputDevice() {
        return outputDevice;
    }

    public String getActivationType() {
        return activationType;
    }

    public int getMinCPS() {
        return minCPS;
    }

    public int getMaxCPS() {
        return maxCPS;
    }

    public void setInputCode(int inputCode) {
        this.inputCode = inputCode;
    }

    public void setInputDevice(String inputDevice) {
        this.inputDevice = inputDevice;
    }

    public void setOutputCode(int outputCode) {
        this.outputCode = outputCode;
    }

    public void setOutputDevice(String outputDevice) {
        this.outputDevice = outputDevice;
    }
    public void setActivationType(String activationType) {
        this.activationType = activationType;
    }

    public void setMinCPS(int minCPS) {
        this.minCPS = minCPS;
    }

    public void setMaxCPS(int maxCPS) {
        this.maxCPS = maxCPS;
    }
}
