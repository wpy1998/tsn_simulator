import Hardware.Computer;

import java.io.UnsupportedEncodingException;

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
//        SquareLauncher launcher = new SquareLauncher();
//        launcher.init(computer);
//        launcher.start(300);

        TwelvePointsLauncher twelvePointsLauncher = new TwelvePointsLauncher();
        twelvePointsLauncher.init(computer);
        twelvePointsLauncher.start(300);
    }
    //5, 10, 15, 20, 25
}
