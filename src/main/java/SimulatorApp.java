import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;

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
     *      d2         d4
     *      |          |
     * d1---s1--------s2---d3
     *      | \      / |
     *      |   \  /   |
     *      |    / \   |
     *      |  /     \ |
     * d5---s3--------s4---d7
     *      |          |
     *      d6        d8
     */
    public static void main(String[] args) {
        Computer computer = new Computer();
        TSNSwitch tsnSwitch1 = TSNSwitch.builder().hostName("S1").build();
        TSNSwitch tsnSwitch2 = TSNSwitch.builder().hostName("S2").build();
        TSNSwitch tsnSwitch3 = TSNSwitch.builder().hostName("S3").build();
        TSNSwitch tsnSwitch4 = TSNSwitch.builder().hostName("S4").build();

        TSNDevice tsnDevice1 = TSNDevice.builder().hostName("D1").build();
        TSNDevice tsnDevice2 = TSNDevice.builder().hostName("D2").build();
        TSNDevice tsnDevice3 = TSNDevice.builder().hostName("D3").build();
        TSNDevice tsnDevice4 = TSNDevice.builder().hostName("D4").build();
        TSNDevice tsnDevice5 = TSNDevice.builder().hostName("D5").build();
        TSNDevice tsnDevice6 = TSNDevice.builder().hostName("D6").build();
        TSNDevice tsnDevice7 = TSNDevice.builder().hostName("D7").build();
        TSNDevice tsnDevice8 = TSNDevice.builder().hostName("D8").build();

        //switch -- device
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice1.getNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice2.getNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnDevice3.getNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnDevice4.getNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnDevice5.getNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnDevice6.getNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnDevice7.getNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnDevice8.getNetCard());

        //switch -- switch
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch2.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnSwitch4.createNetCard());

        NetworkLauncher networkLauncher = NetworkLauncher.builder().topologyId(topology_id)
                .urlFront(computer.urls.get("tsn-topology")).build();
        networkLauncher.registerDevice(tsnDevice1);
        networkLauncher.registerDevice(tsnDevice2);
        networkLauncher.registerDevice(tsnDevice3);
        networkLauncher.registerDevice(tsnDevice4);
        networkLauncher.registerDevice(tsnDevice5);
        networkLauncher.registerDevice(tsnDevice6);
        networkLauncher.registerDevice(tsnDevice7);
        networkLauncher.registerDevice(tsnDevice8);
        networkLauncher.registerSwitch(tsnSwitch1);
        networkLauncher.registerSwitch(tsnSwitch2);
        networkLauncher.registerSwitch(tsnSwitch3);
        networkLauncher.registerSwitch(tsnSwitch4);

        StreamLauncher streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            if (str.equals("exit") || str.equals("quit") || str.equals("stop")){
                networkLauncher.removeDevice(tsnDevice1);
                networkLauncher.removeDevice(tsnDevice2);
                networkLauncher.removeDevice(tsnDevice3);
                networkLauncher.removeDevice(tsnDevice4);
                networkLauncher.removeDevice(tsnDevice5);
                networkLauncher.removeDevice(tsnDevice6);
                networkLauncher.removeDevice(tsnDevice7);
                networkLauncher.removeDevice(tsnDevice8);
                networkLauncher.removeSwitch(tsnSwitch1);
                networkLauncher.removeSwitch(tsnSwitch2);
                networkLauncher.removeSwitch(tsnSwitch3);
                networkLauncher.removeSwitch(tsnSwitch4);
                break;
            }
        }
    }
}
