package listeners;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import process.AutoClickerProcess;

public class HoldMouseListener implements NativeMouseListener {
    private final AutoClickerProcess autoClickerProcess;
    private final int inputCode;

    public HoldMouseListener(AutoClickerProcess autoClickerProcess, int inputCode) {
        this.autoClickerProcess = autoClickerProcess;
        this.inputCode = inputCode;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        if(nativeEvent.getButton() == inputCode) {
            autoClickerProcess.start();
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        if(nativeEvent.getButton() == inputCode) {
            autoClickerProcess.stop();
        }
    }
}
