package listeners;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import process.AutoClickerProcess;

public class ToggleMouseListener implements NativeMouseListener {
    private AutoClickerProcess autoClickerProcess;
    private Integer inputKey; //the input that activates the corresponding auto clicker

    public ToggleMouseListener(AutoClickerProcess autoClickerProcess, Integer inputKey) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputKey = inputKey;
        //needs to start and stop the auto clicker
        //needs to know which input to look out for with setter methods to change it
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
    }
}
