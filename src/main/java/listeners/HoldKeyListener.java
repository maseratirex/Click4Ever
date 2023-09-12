package listeners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import process.AutoClickerProcess;

public class HoldKeyListener implements NativeKeyListener {
    private AutoClickerProcess autoClickerProcess;
    private Integer inputKey; //the input that activates the corresponding auto clicker

    public HoldKeyListener(AutoClickerProcess autoClickerProcess, Integer inputKey) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputKey = inputKey;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        //do nothing
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        //should start the auto clicker logic
        if(nativeEvent.getKeyCode() == inputKey) {
            autoClickerProcess.start();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        //should stop the auto clicker logic
        if(nativeEvent.getKeyCode() == inputKey) {
            autoClickerProcess.stop();
        }
    }
}
