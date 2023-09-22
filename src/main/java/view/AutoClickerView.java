package view;

import model.AutoClickerConfiguration;
import process.AutoClickerProcess;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        this.addMouseListener(new WindowHoverListener());
    }
    public void killAutoClicker() {
        autoClickerProcess.kill();
    }
    private class WindowHoverListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            autoClickerProcess.toggleViewing();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            autoClickerProcess.toggleViewing();
        }
    }
}
