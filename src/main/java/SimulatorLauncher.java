import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.Header;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;

import java.util.Scanner;

import static Facility.NetCard.connectNetCard;
import static Hardware.Computer.topology_id;

public class SimulatorLauncher {
    TSNDevice tsnDevice1, tsnDevice2, tsnDevice3, tsnDevice4, tsnDevice5, tsnDevice6,
            tsnDevice7, tsnDevice8;
    TSNSwitch tsnSwitch1, tsnSwitch2, tsnSwitch3, tsnSwitch4;
    StreamLauncher streamLauncher;
    NetworkLauncher networkLauncher;

    public SimulatorLauncher(){
    }

    public void init(Computer computer){
        tsnDevice1 = TSNDevice.builder().hostName("D1").build();
        tsnDevice2 = TSNDevice.builder().hostName("D2").build();
        tsnDevice3 = TSNDevice.builder().hostName("D3").build();
        tsnDevice4 = TSNDevice.builder().hostName("D4").build();
        tsnDevice5 = TSNDevice.builder().hostName("D5").build();
        tsnDevice6 = TSNDevice.builder().hostName("D6").build();
        tsnDevice7 = TSNDevice.builder().hostName("D7").build();
        tsnDevice8 = TSNDevice.builder().hostName("D8").build();

        tsnSwitch1 = TSNSwitch.builder().hostName("S1").build();
        tsnSwitch2 = TSNSwitch.builder().hostName("S2").build();
        tsnSwitch3 = TSNSwitch.builder().hostName("S3").build();
        tsnSwitch4 = TSNSwitch.builder().hostName("S4").build();

        //switch - - device
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

        streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();
        streamLauncher.registerListenerServer(tsnDevice3, 0);
        streamLauncher.registerListenerServer(tsnDevice5, 0);
        streamLauncher.registerListenerServer(tsnDevice8, 0);
    }

    public void start(int cir){
        for (int i = 0; i < cir; i++){
            generateStream();
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
                break;
            }
        }
    }

    private void generateStream(){
        int body = 1000;
        //talker stream of tsnDevice1
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice1, tsnDevice8, (short) 1);

        //talker stream of tsnDevice2
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevice8, (short) 1);

        //talker stream of tsnDevice3
        streamLauncher.registerTalkerStream(body, tsnDevice3, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice3, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice3, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice3, tsnDevice8, (short) 1);

        //talker stream of tsnDevice4
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevice8, (short) 1);

        //talker stream of tsnDevice5
        streamLauncher.registerTalkerStream(body, tsnDevice5, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice5, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice5, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice5, tsnDevice8, (short) 1);

        //talker stream of tsnDevice6
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevice8, (short) 1);

        //talker stream of tsnDevice7
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice5, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice8, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice7, tsnDevice8, (short) 1);

        //talker stream of tsnDevice8
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevice3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevice3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevice5, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevice5, (short) 1);
    }
}
