import Facility.NetCard;
import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.Header;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Hardware.Computer.topology_id;

public class TwelvePointsLauncher {
    TSNDevice tsnDevice1, tsnDevice2, tsnDevice3, tsnDevice4, tsnDevice5, tsnDevice6,
            tsnDevice7, tsnDevice8;
    TSNSwitch tsnSwitch1, tsnSwitch2, tsnSwitch3, tsnSwitch4, tsnSwitch5, tsnSwitch6,
            tsnSwitch7, tsnSwitch8, tsnSwitch9, tsnSwitch10, tsnSwitch11, tsnSwitch12;
    StreamLauncher streamLauncher;
    NetworkLauncher networkLauncher;

    public TwelvePointsLauncher(){
    }

    public void init(Computer computer){
        tsnSwitch1 = TSNSwitch.builder().hostName("S1").build();
        tsnSwitch2 = TSNSwitch.builder().hostName("S2").build();
        tsnSwitch3 = TSNSwitch.builder().hostName("S3").build();
        tsnSwitch4 = TSNSwitch.builder().hostName("S4").build();
        tsnSwitch5 = TSNSwitch.builder().hostName("S5").build();
        tsnSwitch6 = TSNSwitch.builder().hostName("S6").build();
        tsnSwitch7 = TSNSwitch.builder().hostName("S7").build();
        tsnSwitch8 = TSNSwitch.builder().hostName("S8").build();
        tsnSwitch9 = TSNSwitch.builder().hostName("S9").build();
        tsnSwitch10 = TSNSwitch.builder().hostName("S10").build();
        tsnSwitch11 = TSNSwitch.builder().hostName("S11").build();
        tsnSwitch12 = TSNSwitch.builder().hostName("S12").build();

        tsnDevice1 = TSNDevice.builder().hostName("D1").build();
        tsnDevice2 = TSNDevice.builder().hostName("D2").build();
        tsnDevice3 = TSNDevice.builder().hostName("D3").build();
        tsnDevice4 = TSNDevice.builder().hostName("D4").build();
        tsnDevice5 = TSNDevice.builder().hostName("D5").build();
        tsnDevice6 = TSNDevice.builder().hostName("D6").build();
        tsnDevice7 = TSNDevice.builder().hostName("D7").build();
        tsnDevice8 = TSNDevice.builder().hostName("D8").build();

        //switch - - device
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice1.getNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice2.getNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnDevice3.getNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnDevice4.getNetCard());
        connectNetCard(tsnSwitch9.createNetCard(), tsnDevice5.getNetCard());
        connectNetCard(tsnSwitch9.createNetCard(), tsnDevice6.getNetCard());
        connectNetCard(tsnSwitch11.createNetCard(), tsnDevice7.getNetCard());
        connectNetCard(tsnSwitch11.createNetCard(), tsnDevice8.getNetCard());

        //switch - - switch
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch2.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnSwitch5.createNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnSwitch6.createNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnSwitch7.createNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnSwitch8.createNetCard());
        connectNetCard(tsnSwitch8.createNetCard(), tsnSwitch9.createNetCard());
        connectNetCard(tsnSwitch9.createNetCard(), tsnSwitch10.createNetCard());
        connectNetCard(tsnSwitch10.createNetCard(), tsnSwitch11.createNetCard());
        connectNetCard(tsnSwitch11.createNetCard(), tsnSwitch12.createNetCard());
        connectNetCard(tsnSwitch12.createNetCard(), tsnSwitch10.createNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnSwitch7.createNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnSwitch10.createNetCard());
        connectNetCard(tsnSwitch10.createNetCard(), tsnSwitch3.createNetCard());

        networkLauncher = NetworkLauncher.builder().topologyId(topology_id)
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
        networkLauncher.registerSwitch(tsnSwitch5);
        networkLauncher.registerSwitch(tsnSwitch6);
        networkLauncher.registerSwitch(tsnSwitch7);
        networkLauncher.registerSwitch(tsnSwitch8);
        networkLauncher.registerSwitch(tsnSwitch9);
        networkLauncher.registerSwitch(tsnSwitch10);
        networkLauncher.registerSwitch(tsnSwitch11);
        networkLauncher.registerSwitch(tsnSwitch12);

        streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();
        streamLauncher.registerListenerServer(tsnDevice1, 0);
        streamLauncher.registerListenerServer(tsnDevice3, 0);
        streamLauncher.registerListenerServer(tsnDevice5, 0);
        streamLauncher.registerListenerServer(tsnDevice7, 0);
    }

    public void start(int cir){
        for (int i = 0; i < cir; i++){
//            generateUnicastStream(400);
//            generateUnicastStream(500);
            generateUnicastStream(1000);
            generateBroadcastStream(1000);
        }
        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            if (str.equals("exit") || str.equals("quit") || str.equals("stop")){
                streamLauncher.removeListenerServer(tsnDevice1);
                streamLauncher.removeListenerServer(tsnDevice2);
                streamLauncher.removeListenerServer(tsnDevice3);
                streamLauncher.removeListenerServer(tsnDevice4);
                streamLauncher.removeListenerServer(tsnDevice5);
                streamLauncher.removeListenerServer(tsnDevice6);
                streamLauncher.removeListenerServer(tsnDevice7);
                streamLauncher.removeListenerServer(tsnDevice8);

                for (Header header: tsnDevice1.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice1, header);
                }
                tsnDevice1.talkerHeaders.clear();

                for (Header header: tsnDevice2.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice2, header);
                }
                tsnDevice2.talkerHeaders.clear();

                for (Header header: tsnDevice3.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice3, header);
                }
                tsnDevice3.talkerHeaders.clear();

                for (Header header: tsnDevice4.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice4, header);
                }
                tsnDevice4.talkerHeaders.clear();

                for (Header header: tsnDevice5.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice5, header);
                }
                tsnDevice5.talkerHeaders.clear();

                for (Header header: tsnDevice6.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice6, header);
                }
                tsnDevice6.talkerHeaders.clear();

                for (Header header: tsnDevice7.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice7, header);
                }
                tsnDevice7.talkerHeaders.clear();

                for (Header header: tsnDevice8.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice8, header);
                }
                tsnDevice8.talkerHeaders.clear();

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
                networkLauncher.removeSwitch(tsnSwitch5);
                networkLauncher.removeSwitch(tsnSwitch6);
                networkLauncher.removeSwitch(tsnSwitch7);
                networkLauncher.removeSwitch(tsnSwitch8);
                networkLauncher.removeSwitch(tsnSwitch9);
                networkLauncher.removeSwitch(tsnSwitch10);
                networkLauncher.removeSwitch(tsnSwitch11);
                networkLauncher.removeSwitch(tsnSwitch12);
                break;
            }
        }
    }

    private void generateUnicastStream(int body){
        List<TSNDevice> tsnDevices1 = new ArrayList<>();
        tsnDevices1.add(tsnDevice1);
        List<TSNDevice> tsnDevices2 = new ArrayList<>();
        tsnDevices2.add(tsnDevice3);
        List<TSNDevice> tsnDevices3 = new ArrayList<>();
        tsnDevices3.add(tsnDevice5);
        List<TSNDevice> tsnDevices4 = new ArrayList<>();
        tsnDevices4.add(tsnDevice7);

        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices2, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices2, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices4, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices4, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices1, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices1, (short) 1);
    }

    public void generateBroadcastStream(int body){
        List<TSNDevice> tsnDevices = new ArrayList<>();
        tsnDevices.add(tsnDevice1);
        tsnDevices.add(tsnDevice3);
        tsnDevices.add(tsnDevice5);
        tsnDevices.add(tsnDevice7);

        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices, (short) 1);
    }

    public void connectNetCard(NetCard n1, NetCard n2){
        n1.setConnectTo(n2.getName());
        n2.setConnectTo(n1.getName());
    }
}
