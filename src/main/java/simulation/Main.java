package simulation;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        Simulation.getInstance().run();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Simulation.getInstance().pause();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Simulation.getInstance().resume();
    }
}
