package view;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import model.AutoClickerConfiguration;
import model.MainModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainView {
    private static final MainView instance = new MainView();
    private boolean windowFocused;
    private final MainModel mainModel = MainModel.getInstance();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private MainView() {}
    public static MainView getInstance() {
        return instance;
    }
    public void createAndShowGUI() {
        JFrame window = new JFrame("Click4Ever");
        window.addWindowListener(new MainWindowListener());
        window.addWindowFocusListener(new MainWindowListener());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(300, 450);

        //Display tab pane and tabs
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //Allows you to scroll through AutoClicker tabs
        tabbedPane.setFocusable(false);

        for(String autoClickerName : mainModel.getAutoClickerNames()) {
            AutoClickerConfiguration configuration = mainModel.getConfiguration(autoClickerName);
            tabbedPane.addTab(autoClickerName, new AutoClickerView(configuration));
        }
        window.add(tabbedPane);

        //Menu bar for add and delete Auto Clickers
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BorderLayout());
        JButton addNewAutoClickerButton = new JButton("Add new auto clicker");
        addNewAutoClickerButton.setFocusPainted(false);
        addNewAutoClickerButton.addActionListener(new AddNewAutoClickerListener());
        JButton deleteAutoClickerButton = new JButton("Delete auto clicker");
        deleteAutoClickerButton.setFocusPainted(false);
        deleteAutoClickerButton.addActionListener(new DeleteAutoClickerListener());
        menuBar.add(addNewAutoClickerButton, BorderLayout.LINE_START);
        menuBar.add(deleteAutoClickerButton, BorderLayout.LINE_END);
        window.setJMenuBar(menuBar);

        //Display the window.
        window.setVisible(true);
    }

    public boolean isWindowFocused() {
        return windowFocused;
    }

    private class AddNewAutoClickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Prompt user for Auto Clicker's name until valid
            String autoClickerName = JOptionPane.showInputDialog("Name your Auto Clicker");
            if(autoClickerName == null) { //If input cancelled
                return;
            }
            while(autoClickerName.isBlank() || mainModel.getAutoClickerNames().contains(autoClickerName)) {
                autoClickerName = JOptionPane.showInputDialog("Cannot be only whitespace or match other Auto Clicker names");
                if(autoClickerName == null) { //If input cancelled
                    return;
                }
            }
            AutoClickerConfiguration defaultConfiguration = mainModel.getDefaultConfiguration();
            mainModel.addConfiguration(autoClickerName, defaultConfiguration);
            System.out.println("Added Auto Clicker configuration");
            tabbedPane.addTab(autoClickerName, new AutoClickerView(defaultConfiguration));
            System.out.println("Added Auto Clicker view");
        }
    }
    private class DeleteAutoClickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] possibleNames = mainModel.getAutoClickerNames().toArray();
            if(possibleNames.length == 0) { //no auto clicker to delete
                JOptionPane.showMessageDialog(null, "No Auto Clicker to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String autoClickerName = (String) JOptionPane.showInputDialog(
                    null,
                    "Select the Auto Clicker to delete",
                    "Delete",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    possibleNames,
                    possibleNames[0]
            );
            if(autoClickerName == null) { //If input cancelled
                return;
            }
            int indexToDelete = tabbedPane.indexOfTab(autoClickerName);
            AutoClickerView view = (AutoClickerView) tabbedPane.getComponentAt(indexToDelete);
            view.killAutoClicker();
            tabbedPane.remove(indexToDelete);
            System.out.println("Deleted Auto Clicker view");
            mainModel.deleteConfiguration(autoClickerName);
            System.out.println("Deleted Auto Clicker configuration");
        }
    }
    private class MainWindowListener extends WindowAdapter {
        @Override
        public void windowGainedFocus(WindowEvent e) {
            System.out.println("Window focused. Paused clicking");
            windowFocused = true;
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            System.out.println("Window unfocused. Resumed clicking");
            windowFocused = false;
        }
        @Override
        public void windowOpened(WindowEvent event) {
            System.out.println("Window opened");
            try {
                GlobalScreen.registerNativeHook();
            } catch (NativeHookException exc) {
                throw new RuntimeException(exc);
            }
        }
        @Override
        public void windowClosing(WindowEvent event) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                throw new RuntimeException(ex);
            }
            mainModel.saveConfigurations();
        }
    }
}
