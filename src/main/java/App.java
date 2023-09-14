import com.formdev.flatlaf.FlatDarkLaf;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import model.AutoClickerConfiguration;
import view.AutoClickerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class App {
    private static HashMap<String, AutoClickerConfiguration> configurations; //key: name of auto clicker; value: config for auto clicker
    private static JTabbedPane tabbedPane;
    public static void main(String[] args) {
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            System.out.println("macOS");
            System.setProperty( "apple.awt.application.appearance", "system" ); //Header bar will be dark/light based on macOS theme
            System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS"); //So that Command-Q properly closes app
            System.setProperty( "apple.awt.application.name", "Click4Ever" );
        } else {
            System.out.println("Windows");
        }
        FlatDarkLaf.setup(); //FIXME eventually choose dark/light mode based on OS system setting

        loadConfigurations();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void loadConfigurations() {
        System.out.println("Loading configurations");
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            configurations = (HashMap<String, AutoClickerConfiguration>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load configurations");
            configurations = new HashMap<>();
        }
    }
    private static void createAndShowGUI() {
        JFrame window = new JFrame("Click4Ever");
        window.addWindowListener(new MainWindowListener());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(300, 450);

        //Display tab pane and tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //Allows you to scroll through AutoClicker tabs
        tabbedPane.setFocusable(false);

        for(HashMap.Entry<String, AutoClickerConfiguration> configurationEntry : configurations.entrySet()) {
            String autoClickerName = configurationEntry.getKey();
            AutoClickerConfiguration configuration = configurationEntry.getValue();
            tabbedPane.addTab(autoClickerName, new AutoClickerView(configuration));
        }
        window.add(tabbedPane);

        //Menu bar for add and delete Auto Clickers
        JMenuBar menuBar = new JMenuBar();
        JButton addNewAutoClickerButton = new JButton("Add new auto clicker");
        addNewAutoClickerButton.addActionListener(new AddNewAutoClickerListener());
        JButton deleteAutoClickerButton = new JButton("Delete auto clicker");
        deleteAutoClickerButton.addActionListener(new DeleteAutoClickerListener());
        menuBar.add(addNewAutoClickerButton);
        menuBar.add(deleteAutoClickerButton);
        window.setJMenuBar(menuBar);

        //Display the window.
        window.setVisible(true);
    }
    private static class AddNewAutoClickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Prompt user for Auto Clicker's name until valid
            String autoClickerName = JOptionPane.showInputDialog("Name your Auto Clicker");
            if(autoClickerName == null) { //If input cancelled
                return;
            }
            while(autoClickerName.isBlank() || configurations.containsKey(autoClickerName)) {
                autoClickerName = JOptionPane.showInputDialog("Cannot be only whitespace or match other Auto Clicker names");
                if(autoClickerName == null) { //If input cancelled
                    return;
                }
            }
            //Initialize with default values
            AutoClickerConfiguration defaultConfiguration = new AutoClickerConfiguration(
                    NativeKeyEvent.VC_CLOSE_BRACKET,
                    AutoClickerConfiguration.TYPE_KEYBOARD,
                    KeyEvent.VK_E,
                    AutoClickerConfiguration.TYPE_KEYBOARD,
                    AutoClickerConfiguration.TYPE_HOLD,
                    200,
                    1000
            );
            configurations.put(autoClickerName, defaultConfiguration);
            tabbedPane.addTab(autoClickerName, new AutoClickerView(defaultConfiguration));
        }
    }
    private static class DeleteAutoClickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Deleting auto clicker");
            Object[] possibleNames = configurations.keySet().toArray();
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
            tabbedPane.remove(indexToDelete);
            configurations.remove(autoClickerName);
        }
    }
    private static class MainWindowListener extends WindowAdapter {
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
            System.out.println("Saving configurations");
            try {
                FileOutputStream fos = new FileOutputStream("src/main/resources/config.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(configurations);
                oos.close();
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}