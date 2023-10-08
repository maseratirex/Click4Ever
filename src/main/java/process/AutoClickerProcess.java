package process;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import listeners.HoldKeyListener;
import listeners.HoldMouseListener;
import listeners.ToggleKeyListener;
import listeners.ToggleMouseListener;
import model.AutoClickerConfiguration;
import model.MainModel;
import view.MainView;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventListener;
import java.util.concurrent.ThreadLocalRandom;

public class AutoClickerProcess {
    private EventListener eventListener;
    private MainView mainView = MainView.getInstance();
    private MainModel mainModel = MainModel.getInstance();
    private AutoClickerConfiguration configuration;
    private boolean running = false;
    private boolean userViewing = false;
    private Runnable runner;

    public AutoClickerProcess(AutoClickerConfiguration configuration) {
        /**
         * TODO Fix the Runners' speed issue on Mac
         */
        this.configuration = configuration;
        updateInput();
        updateOutput();
    }

    public void start() {
        if(mainView.isWindowFocused()) {
            running = false;
            return;
        }
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
    public void toggleViewing() {
        if(userViewing) {
            userViewing = false;
        } else {
            userViewing = true;
            stop();
        }
    }
    public void kill() {
        running = false;
        runner = null;
        if(eventListener != null) {
            if(eventListener instanceof NativeKeyListener) {
                GlobalScreen.removeNativeKeyListener((NativeKeyListener) eventListener);
                System.out.println("Removed key listener");
            } else {
                GlobalScreen.removeNativeMouseListener((NativeMouseListener) eventListener);
                System.out.println("Removed mouse listener");
            }
        }
        System.out.println("Killed process");
    }
    public void updateInput() {
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
                System.out.println("Added hold key listener");
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
    }
    public void updateOutput() {
        //Specify which output device to press: click mouse button or type key
        if(configuration.getOutputDevice().equals(AutoClickerConfiguration.TYPE_MOUSE)) {
            runner = new MouseClickerRunner(configuration.getOutputCode(), configuration.getMinCPS(), configuration.getMaxCPS());
            System.out.println("Set new MouseClickerRunner");
        } else if(configuration.getOutputDevice().equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            runner = new KeyTyperRunner(configuration.getOutputCode(), configuration.getMinCPS(), configuration.getMaxCPS());
            System.out.println("Set new KeyTyperRunner");
        }
    }
    private class MouseClickerRunner implements Runnable {
        private final int outputCode;
        private final int minCPS;
        private final int maxCPS;

        public MouseClickerRunner(int outputCode, int minCPS, int maxCPS) {
            this.outputCode = outputCode;
            this.minCPS = minCPS;
            this.maxCPS = maxCPS;
        }

        @Override
        public void run() {
            /**
             * FIXME teleporting cursor issue on Mac
             * FIXME overall execution speed per click on Mac
             */
            Robot robot;
            try {
                robot = new Robot();
            } catch (AWTException exc) {
                throw new RuntimeException(exc);
            }
            while(running) {
                int buttonMask = InputEvent.getMaskForButton(outputCode);
                robot.mousePress(buttonMask);
                robot.mouseRelease(buttonMask);
                System.out.println("Clicked");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000/maxCPS, 1000/minCPS + 1));
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
    private class KeyTyperRunner implements Runnable {
        private final int outputCode;
        private final int minCPS;
        private final int maxCPS;

        public KeyTyperRunner(int outputCode, int minCPS, int maxCPS) {
            this.outputCode = outputCode;
            this.minCPS = minCPS;
            this.maxCPS = maxCPS;
        }

        @Override
        public void run() {
            Robot robot;
            try {
                robot = new Robot();
            } catch (AWTException exc) {
                throw new RuntimeException(exc);
            }
            while(running) {
                robot.keyPress(outputCode);
                robot.keyRelease(outputCode);
                System.out.println("Typed");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000/maxCPS, 1000/minCPS + 1));
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
}
