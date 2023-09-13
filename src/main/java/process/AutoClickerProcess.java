package controller;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import listeners.HoldKeyListener;
import listeners.HoldMouseListener;
import listeners.ToggleKeyListener;
import listeners.ToggleMouseListener;
import model.AutoClickerConfiguration;
import process.KeyTyperRunner;
import process.MouseClickerRunner;

import java.util.EventListener;

public class AutoClickerProcess {
    private EventListener eventListener;
    private boolean running = false;
    private Runnable runner;

    public void start() {
        if(running) {
            return;
        }
        running = true;
        Thread thread = new Thread(runner);
        thread.start();
    }
    public void stop() {
        running = false;
    }
    public void toggle() {
        if(running) {
            stop();
        } else {
            start();
        }
    }
    public boolean isRunning() {
        return running;
    }

    public void configure(AutoClickerConfiguration configuration) {
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
        if(configuration.getInputDevice().equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_HOLD)) {
                eventListener = new HoldKeyListener(this, configuration.getInputCode());
                GlobalScreen.addNativeKeyListener((NativeKeyListener) eventListener);
                System.out.println("Added hold key listener for " + configuration.getInputCode());
            } else if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_TOGGLE)){
                eventListener = new ToggleKeyListener(this, configuration.getInputCode());
                GlobalScreen.addNativeKeyListener((NativeKeyListener) eventListener);
                System.out.println("Added toggle key listener");
            }
        } else if(configuration.getInputDevice().equals(AutoClickerConfiguration.TYPE_MOUSE)){
            if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_HOLD)) {
                eventListener = new HoldMouseListener(this, configuration.getInputCode());
                GlobalScreen.addNativeMouseListener((NativeMouseListener) eventListener);
                System.out.println("Added hold mouse listener");
            } else if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_TOGGLE)){
                eventListener = new ToggleMouseListener(this, configuration.getInputCode());
                GlobalScreen.addNativeMouseListener((NativeMouseListener) eventListener);
                System.out.println("Added toggle mouse listener");
            }
        }
        //Now specify which output device to press: click mouse button or type key
        if(configuration.getOutputDevice().equals(AutoClickerConfiguration.TYPE_MOUSE)) {
            runner = new MouseClickerRunner(this, configuration.getOutputCode(), configuration.getMinCPS(), configuration.getMaxCPS());
        } else if(configuration.getOutputDevice().equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            runner = new KeyTyperRunner(this, configuration.getOutputCode(), configuration.getMinCPS(), configuration.getMaxCPS());
        }
    }
}
