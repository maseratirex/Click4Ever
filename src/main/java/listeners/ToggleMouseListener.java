package listeners;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import process.AutoClickerProcess;

public class ToggleMouseListener implements NativeMouseListener {
    private final AutoClickerProcess autoClickerProcess;
    private final int inputCode;

    public ToggleMouseListener(AutoClickerProcess autoClickerProcess, int inputCode) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputCode = inputCode;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        if(nativeEvent.getButton() == inputCode) {
            autoClickerProcess.toggle();
        }
    }
}
