package listeners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import process.AutoClickerProcess;

public class ToggleKeyListener implements NativeKeyListener {
    private AutoClickerProcess autoClickerProcess;
    private Integer inputKey; //the input that activates the corresponding auto clicker

    public ToggleKeyListener(AutoClickerProcess autoClickerProcess, Integer inputKey) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputKey = inputKey;
        //needs to start and stop the auto clicker
        //needs to know which input to look out for with setter methods to change it
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        //do nothing
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        //do nothing
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        //should toggle the auto clicker logic
    }
}
