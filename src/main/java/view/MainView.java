package view;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import process.AutoClickerProcess;
import model.AutoClickerConfiguration;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView() {
        setTitle("Click4Ever");
        setLayout(new BorderLayout());
        setSize(300, 450);
        setResizable(false);
        setVisible(true);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        //30 is for a?
        AutoClickerProcess acp = new AutoClickerProcess();
        AutoClickerConfiguration autoClickerConfiguration = new AutoClickerConfiguration(NativeKeyEvent.VC_A, AutoClickerConfiguration.TYPE_KEYBOARD, AutoClickerConfiguration.TYPE_HOLD, 8, 12, "Unnamed");
        acp.configure(autoClickerConfiguration);
        AutoClickerProcess acp2 = new AutoClickerProcess();
        AutoClickerConfiguration autoClickerConfiguration2 = new AutoClickerConfiguration(NativeKeyEvent.VC_B, AutoClickerConfiguration.TYPE_KEYBOARD, AutoClickerConfiguration.TYPE_HOLD, 8, 12, "Unnamed");
        acp2.configure(autoClickerConfiguration2);

        /*
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //Allows you to scroll through AutoClicker tabs
        tabbedPane.setFocusable(false);
         */
        //Load models from list and create controller and view around it?
    }
}
