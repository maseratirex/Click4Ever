package process;

import java.awt.Robot;
import java.awt.AWTException;
import java.util.concurrent.ThreadLocalRandom;

public class MouseClickerRunner implements Runnable {
    private final int outputCode;
    private final int minCPS;
    private final int maxCPS;
    private final AutoClickerProcess autoClickerProcess;

    public MouseClickerRunner(AutoClickerProcess autoClickerProcess, int outputCode, int minCPS, int maxCPS) {
        this.autoClickerProcess = autoClickerProcess;
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
        while(autoClickerProcess.isRunning()) {
            robot.mousePress(outputCode);
            robot.mouseRelease(outputCode);
            System.out.println("CLICKED");
            try {
                Thread.sleep(1000/ThreadLocalRandom.current().nextInt(minCPS, maxCPS));
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}
