package process;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import listeners.HoldKeyListener;
import listeners.HoldMouseListener;
import listeners.ToggleKeyListener;
import listeners.ToggleMouseListener;
import model.AutoClickerConfiguration;

import java.awt.*;
import java.util.EventListener;

public class AutoClickerProcess {
    private EventListener eventListener;
    private boolean isRunning = false;

    public void start() {
        if(isRunning) {
            return;
        }
        isRunning = true;
        Thread thread = new Thread(){
            public void run() {
                Robot robot;
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
                while(isRunning) {
                    System.out.println("Iteration begun");
                    //point = MouseInfo.getPointerInfo().getLocation();
                    //robot.mouseMove(point.x, point.y);
                    robot.mousePress(16);
                    robot.mouseRelease(16);
                    System.out.println("Clicked");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        thread.start();
    }
    public void stop() {
        isRunning = false;
    }
    public void toggle() {
        if(isRunning) {
            stop();
        } else {
            start();
        }
    }

    public void configure(AutoClickerConfiguration autoClickerConfiguration) {
        //First remove any existing event listener
        if(eventListener != null) {
            if(eventListener instanceof NativeKeyListener) {
                GlobalScreen.removeNativeKeyListener((NativeKeyListener) eventListener);
                System.out.println("Removed key listener");
            } else {
                GlobalScreen.removeNativeMouseListener((NativeMouseListener) eventListener);
                System.out.println("Removed mouse listener");
            }
        }
        //Then add new event listener
        if(autoClickerConfiguration.getInputDevice().equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            if(autoClickerConfiguration.getActivationType().equals(AutoClickerConfiguration.TYPE_HOLD)) {
                GlobalScreen.addNativeKeyListener(new HoldKeyListener(this, autoClickerConfiguration.getInputCode()));
                System.out.println("Added hold key listener for " + autoClickerConfiguration.getInputCode());
            } else if(autoClickerConfiguration.getActivationType().equals(AutoClickerConfiguration.TYPE_TOGGLE)){
                GlobalScreen.addNativeKeyListener(new ToggleKeyListener(this, autoClickerConfiguration.getInputCode()));
                System.out.println("Added toggle key listener");
            }
        } else if(autoClickerConfiguration.getInputDevice().equals(AutoClickerConfiguration.TYPE_MOUSE)){
            if(autoClickerConfiguration.getActivationType().equals(AutoClickerConfiguration.TYPE_HOLD)) {
                GlobalScreen.addNativeMouseListener(new HoldMouseListener(this, autoClickerConfiguration.getInputCode()));
                System.out.println("Added hold mouse listener");
            } else if(autoClickerConfiguration.getActivationType().equals(AutoClickerConfiguration.TYPE_TOGGLE)){
                GlobalScreen.addNativeMouseListener(new ToggleMouseListener(this, autoClickerConfiguration.getInputCode()));
                System.out.println("Added toggle mouse listener");
            }
        }
    }
}
