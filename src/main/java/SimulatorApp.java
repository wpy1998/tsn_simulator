import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.Header;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import static Facility.NetCard.connectNetCard;
import static Hardware.Computer.topology_id;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:02 AM
 */
public class SimulatorApp {
    /**
     *      d2     d4
     *      |      |
     * d1---s1----s2---d3
     *      | \  /|
     *      |  \/ |
     *      | / \ |
     *      |/   \|
     * d5---s3----s4---d7
     *      |     |
     *      d6    d8
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        Computer computer = new Computer();
        SimulatorLauncher launcher = new SimulatorLauncher();
        launcher.init(computer);
        launcher.start(10);
    }
}
