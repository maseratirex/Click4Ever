package model;

import java.io.Serializable;

public class AutoClickerConfiguration implements Serializable {
    public static final String TYPE_MOUSE = "Mouse";
    public static final String TYPE_KEYBOARD = "Keyboard";
    public static final String TYPE_HOLD = "Hold";
    public static final String TYPE_TOGGLE = "Toggle";

    private final int inputCode; //mouse button code or key code
    private final String inputDevice; //mouse/keyboard
    private final int outputCode; //mouse button code or key code
    private final String outputDevice; //mouse/keyboard
    private final String activationType; //hold/toggle
    private final int minCPS;

    private final int maxCPS;

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
}
