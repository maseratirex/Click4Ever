package view;

import model.AutoClickerConfiguration;
import process.AutoClickerProcess;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class AutoClickerView extends JPanel {
    private final AutoClickerProcess autoClickerProcess;
    private final AutoClickerConfiguration configuration;
    public AutoClickerView(AutoClickerConfiguration configuration) {
        this.configuration = configuration;
        autoClickerProcess = new AutoClickerProcess(configuration);
        setupInputArea();
        setupOutputArea();
        setupActivationOptionsArea();
        setupSetCPSArea();
    }

    private void setupInputArea() {
        JPanel inputArea = new JPanel();
        inputArea.setLayout(new BoxLayout(inputArea, BoxLayout.LINE_AXIS));
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JLabel setToggleKeyLabel = new JLabel("Toggle Key/Button");
        inputArea.add(setToggleKeyLabel);
        inputArea.add(Box.createRigidArea(new Dimension(10,0)));

        JTextField inputField = new JTextField();
        String inputDevice = configuration.getInputDevice();
        if(inputDevice.equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            inputField.setText(inputDevice + " " + KeyEvent.getKeyText(configuration.getInputCode()));
        } else {
            inputField.setText(inputDevice + " " + configuration.getInputCode());
        }

        //Style inputField
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setBackground(new Color(0x323232));
        inputField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));

        inputArea.add(inputField);
        add(inputArea);

        inputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int inputCode = e.getButton();
                String inputDevice = AutoClickerConfiguration.TYPE_MOUSE;
                configuration.setInputCode(inputCode);
                configuration.setInputDevice(inputDevice);
                autoClickerProcess.updateInput();
                inputField.setText(inputDevice + " " + inputCode);
                inputField.setFocusable(false);
                inputField.setFocusable(true);
            }
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int inputCode = e.getKeyCode();
                String inputDevice = AutoClickerConfiguration.TYPE_KEYBOARD;
                configuration.setInputCode(inputCode);
                configuration.setInputDevice(inputDevice);
                autoClickerProcess.updateInput();
                inputField.setText(inputDevice + " " + KeyEvent.getKeyText(inputCode));
                inputField.setFocusable(false);
                inputField.setFocusable(true);
            }
        });
    }
    private void setupOutputArea() {
        JPanel outputArea = new JPanel();
        outputArea.setLayout(new BoxLayout(outputArea, BoxLayout.LINE_AXIS));
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Output"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JLabel setSimulatedKeyLabel = new JLabel("Simulated Key/Button");
        outputArea.add(setSimulatedKeyLabel);
        outputArea.add(Box.createRigidArea(new Dimension(10,0)));

        JTextField outputField = new JTextField();
        String outputDevice = configuration.getOutputDevice();
        if(outputDevice.equals(AutoClickerConfiguration.TYPE_KEYBOARD)) {
            outputField.setText(outputDevice + " " + KeyEvent.getKeyText(configuration.getOutputCode()));
        } else {
            outputField.setText(outputDevice + " " + configuration.getOutputCode());
        }
        //Style outputField
        outputField.setHorizontalAlignment(JTextField.CENTER);
        outputField.setBackground(new Color(0x323232));
        outputField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        outputField.setEditable(false);

        outputArea.add(outputField);
        add(outputArea);

        outputField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int outputCode = e.getButton();
                String outputDevice = AutoClickerConfiguration.TYPE_MOUSE;
                configuration.setOutputCode(outputCode);
                configuration.setOutputDevice(outputDevice);
                autoClickerProcess.updateOutput();
                outputField.setText(outputDevice + " " + outputCode);
                outputField.setFocusable(false);
                outputField.setFocusable(true);
            }
        });
        outputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int outputCode = e.getKeyCode();
                String outputDevice = AutoClickerConfiguration.TYPE_KEYBOARD;
                configuration.setOutputCode(outputCode);
                configuration.setOutputDevice(outputDevice);
                autoClickerProcess.updateOutput();
                outputField.setText(outputDevice + " " + KeyEvent.getKeyText(outputCode));
                outputField.setFocusable(false);
                outputField.setFocusable(true);
            }
        });
    }
    private void setupActivationOptionsArea() {
        JPanel activationOptionsArea = new JPanel();
        activationOptionsArea.setLayout(new BoxLayout(activationOptionsArea, BoxLayout.Y_AXIS));
        activationOptionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Activation Type"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        ButtonGroup activationOptions = new ButtonGroup();
        JRadioButton whileHeldDown = new JRadioButton("Repeat While Toggle Key Held Down");
        JRadioButton whileToggled = new JRadioButton("Repeat Until Toggle Key Pressed Again");
        activationOptions.add(whileHeldDown);
        activationOptions.add(whileToggled);
        if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_HOLD)) whileHeldDown.setSelected(true);
        else if(configuration.getActivationType().equals(AutoClickerConfiguration.TYPE_TOGGLE)) whileToggled.setSelected(true);
        add(activationOptionsArea);
        activationOptionsArea.add(whileHeldDown);
        activationOptionsArea.add(whileToggled);

        whileHeldDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configuration.setActivationType(AutoClickerConfiguration.TYPE_HOLD);
                autoClickerProcess.updateInput();
            }
        });
        whileToggled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configuration.setActivationType(AutoClickerConfiguration.TYPE_TOGGLE);
                autoClickerProcess.updateInput();
            }
        });
    }
    public void killAutoClicker() {
        autoClickerProcess.kill();
    }
    private void setupSetCPSArea() {
        JPanel setCPSArea = new JPanel();
        setCPSArea.setLayout(new BoxLayout(setCPSArea, BoxLayout.Y_AXIS));
        setCPSArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Set Clicks Per Second (1 to 1000)"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel minCPSArea = new JPanel();
        JLabel setMinCPSLabel = new JLabel("Minimum CPS");
        SpinnerNumberModel minCPSInputModel = new SpinnerNumberModel(configuration.getMinCPS(), 1, 1000, 1);
        JSpinner minCPSInputField = new JSpinner(minCPSInputModel);
        minCPSInputField.setBackground(new Color(0x323232));
        minCPSArea.add(setMinCPSLabel);
        minCPSArea.add(Box.createRigidArea(new Dimension(10,0)));
        minCPSArea.add(minCPSInputField);
        setCPSArea.add(minCPSArea);

        JPanel maxCPSArea = new JPanel();
        JLabel setMaxCPSLabel = new JLabel("Maximum CPS");
        SpinnerNumberModel maxCPSInputModel = new SpinnerNumberModel(configuration.getMaxCPS(), configuration.getMinCPS(), 1000, 1);
        JSpinner maxCPSInputField = new JSpinner(maxCPSInputModel);
        maxCPSInputField.setBackground(new Color(0x323232));
        maxCPSArea.add(setMaxCPSLabel);
        maxCPSArea.add(Box.createRigidArea(new Dimension(10,0)));
        maxCPSArea.add(maxCPSInputField);
        setCPSArea.add(maxCPSArea);
        add(setCPSArea);

        minCPSInputField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                configuration.setMinCPS((int) minCPSInputModel.getValue());
                maxCPSInputModel.setMinimum(configuration.getMinCPS());
                autoClickerProcess.updateOutput();
            }
        });
        maxCPSInputField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                configuration.setMaxCPS((int) maxCPSInputModel.getValue());
                minCPSInputModel.setMaximum(configuration.getMaxCPS());
                autoClickerProcess.updateOutput();
            }
        });
    }
}
