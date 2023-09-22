package model;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class MainModel {
    private static final MainModel instance = new MainModel();
    private HashMap<String, AutoClickerConfiguration> configurations; //key: name of auto clicker; value: config for auto clicker
    private MainModel() {
        loadConfigurations();
    }
    public static MainModel getInstance() {
        return instance;
    }
    public void loadConfigurations() {
        System.out.println("Loading configurations");
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            configurations = (HashMap<String, AutoClickerConfiguration>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
            configurations = new HashMap<>();
        }
    }
    public void saveConfigurations() {
        System.out.println("Saving configurations");
        try {
            FileOutputStream fos = new FileOutputStream("src/main/resources/config.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(configurations);
            oos.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public boolean addConfiguration(String autoClickerName, AutoClickerConfiguration configuration) {
        if(configurations.containsKey(autoClickerName)) return false;
        configurations.put(autoClickerName, configuration);
        return true;
    }
    public void deleteConfiguration(String autoClickerName) {
        configurations.remove(autoClickerName);
    }
    public void updateConfiguration(String autoClickerName, AutoClickerConfiguration configuration) {
        configurations.put(autoClickerName, configuration);
    }
    public Set<String> getAutoClickerNames() {
        return configurations.keySet();
    }
    public AutoClickerConfiguration getConfiguration(String autoClickerName) {
        return configurations.get(autoClickerName);
    }
    public AutoClickerConfiguration getNullConfiguration() {
        return new AutoClickerConfiguration(
            NativeKeyEvent.VC_CLOSE_BRACKET,
            AutoClickerConfiguration.TYPE_KEYBOARD,
            KeyEvent.VK_E,
            AutoClickerConfiguration.TYPE_KEYBOARD,
            AutoClickerConfiguration.TYPE_HOLD,
            200,
            1000
        );
    }
}
