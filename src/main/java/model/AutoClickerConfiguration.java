package model;
public class AutoClickerConfiguration {
    public static final String TYPE_MOUSE = "Mouse";
    public static final String TYPE_KEYBOARD = "Keyboard";
    public static final String TYPE_HOLD = "Hold";
    public static final String TYPE_TOGGLE = "Toggle";

    private int inputCode; //mouse button code or key code
    private String inputDevice; //mouse/keyboard
    private String activationType; //hold/toggle
    private double minCPS = 8;

    private double maxCPS = 12;
    private String name = "Unnamed";

    public AutoClickerConfiguration(int inputCode, String inputDevice, String activationType, double minCPS, double maxCPS, String name) {
        this.inputCode = inputCode;
        this.inputDevice = inputDevice;
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

    public String getActivationType() {
        return activationType;
    }

    public double getMinCPS() {
        return minCPS;
    }

    public double getMaxCPS() {
        return maxCPS;
    }

    public String getName() {
        return name;
    }
}
