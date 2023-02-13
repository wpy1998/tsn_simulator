import Hardware.Computer;
import Topology.SquareLauncher;

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
     *     d6    d8
     */
    public static void main(String[] args){
        Computer computer = new Computer();
        SquareLauncher launcher = new SquareLauncher();
        launcher.init(computer);
        launcher.start(1000);

//        Topology.TwelvePointsLauncher twelvePointsLauncher = new Topology.TwelvePointsLauncher();
//        twelvePointsLauncher.init(computer);
//        twelvePointsLauncher.start(100);

//        Topology.FatTreeLauncher fatTreeLauncher = new Topology.FatTreeLauncher();
//        fatTreeLauncher.setK(8);
//        fatTreeLauncher.init(computer);
//        fatTreeLauncher.start(100);
    }
    //5, 10, 15, 20, 25
}
