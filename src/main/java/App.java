import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import model.AutoClickerConfiguration;
import view.AutoClickerView;
import view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        /**
         * TODO clean up Exception handling/logging (exc.printStrackTrace)
         * TODO let user know when window is focused, meaning they can't simulate output
         * TODO configurations shouldn't share the same input keys?
         */
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            System.out.println("macOS");
            System.setProperty( "apple.awt.application.appearance", "system" ); //Header bar will be dark/light based on macOS theme
            System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS"); //So that Command-Q properly closes app
            System.setProperty( "apple.awt.application.name", "Click4Ever" );
        } else {
            System.out.println("Windows");
        }
        FlatDarkLaf.setup(); //FIXME eventually choose dark/light mode based on OS system setting

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainView.getInstance().createAndShowGUI();
            }
        });
    }
}