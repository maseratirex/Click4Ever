package listeners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import process.AutoClickerProcess;

public class ToggleKeyListener implements NativeKeyListener {
    private final AutoClickerProcess autoClickerProcess;
    private final int inputCode;

    public ToggleKeyListener(AutoClickerProcess autoClickerProcess, int inputCode) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputCode = inputCode;
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
        if(nativeEvent.getKeyCode() == inputCode) {
            autoClickerProcess.toggle();
        }
    }
}
