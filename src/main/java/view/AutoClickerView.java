package view;

import model.AutoClickerConfiguration;
import process.AutoClickerProcess;

import javax.swing.*;

public class AutoClickerView extends JPanel {
    private final AutoClickerProcess autoClickerProcess;
    public AutoClickerView(AutoClickerConfiguration configuration) {
        autoClickerProcess = new AutoClickerProcess();
        autoClickerProcess.configure(configuration);
        JLabel placeholderLabel = new JLabel("Placeholder");
        add(placeholderLabel);
        /**
         * TODO add GUI components to set AutoClickerConfiguration
         * TODO create method to publish AutoClickerConfiguration to AutoClickerProcess
         */
    }
}
