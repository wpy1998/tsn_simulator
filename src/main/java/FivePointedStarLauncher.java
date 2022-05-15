import Facility.NetCard;
import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.Header;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;

import java.util.Scanner;

import static Hardware.Computer.topology_id;

public class FivePointedStarLauncher {
    TSNDevice tsnDevice0, tsnDevice1, tsnDevice2, tsnDevice3, tsnDevice4, tsnDevice5, tsnDevice6,
            tsnDevice7, tsnDevice8, tsnDevice9, tsnDevice10, tsnDevice11, tsnDevice12, tsnDevice13,
            tsnDevice14, tsnDevice15, tsnDevice16, tsnDevice17, tsnDevice18, tsnDevice19;
    TSNSwitch tsnSwitch0, tsnSwitch1, tsnSwitch2, tsnSwitch3, tsnSwitch4, tsnSwitch5, tsnSwitch6,
            tsnSwitch7, tsnSwitch8, tsnSwitch9;
    StreamLauncher streamLauncher;
    NetworkLauncher networkLauncher;
    public FivePointedStarLauncher(){
    }

    public void init(Computer computer){
        tsnDevice0 = TSNDevice.builder().hostName("D0").build();
        tsnDevice1 = TSNDevice.builder().hostName("D1").build();
        tsnDevice2 = TSNDevice.builder().hostName("D2").build();
        tsnDevice3 = TSNDevice.builder().hostName("D3").build();
        tsnDevice4 = TSNDevice.builder().hostName("D4").build();
        tsnDevice5 = TSNDevice.builder().hostName("D5").build();
        tsnDevice6 = TSNDevice.builder().hostName("D6").build();
        tsnDevice7 = TSNDevice.builder().hostName("D7").build();
        tsnDevice8 = TSNDevice.builder().hostName("D8").build();
        tsnDevice9 = TSNDevice.builder().hostName("D9").build();
        tsnDevice10 = TSNDevice.builder().hostName("D10").build();
        tsnDevice11 = TSNDevice.builder().hostName("D11").build();
        tsnDevice12 = TSNDevice.builder().hostName("D12").build();
        tsnDevice13 = TSNDevice.builder().hostName("D13").build();
        tsnDevice14 = TSNDevice.builder().hostName("D14").build();
        tsnDevice15 = TSNDevice.builder().hostName("D15").build();
        tsnDevice16 = TSNDevice.builder().hostName("D16").build();
        tsnDevice17 = TSNDevice.builder().hostName("D17").build();
        tsnDevice18 = TSNDevice.builder().hostName("D18").build();
        tsnDevice19 = TSNDevice.builder().hostName("D19").build();

        tsnSwitch0 = TSNSwitch.builder().hostName("S0").build();
        tsnSwitch1 = TSNSwitch.builder().hostName("S1").build();
        tsnSwitch2 = TSNSwitch.builder().hostName("S2").build();
        tsnSwitch3 = TSNSwitch.builder().hostName("S3").build();
        tsnSwitch4 = TSNSwitch.builder().hostName("S4").build();
        tsnSwitch5 = TSNSwitch.builder().hostName("S5").build();
        tsnSwitch6 = TSNSwitch.builder().hostName("S6").build();
        tsnSwitch7 = TSNSwitch.builder().hostName("S7").build();
        tsnSwitch8 = TSNSwitch.builder().hostName("S8").build();
        tsnSwitch9 = TSNSwitch.builder().hostName("S9").build();

        connectNetCard(tsnSwitch0.createNetCard(), tsnDevice0.getNetCard());
        connectNetCard(tsnSwitch0.createNetCard(), tsnDevice1.getNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice2.getNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnDevice3.getNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnDevice4.getNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnDevice5.getNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnDevice6.getNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnDevice7.getNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnDevice8.getNetCard());
        connectNetCard(tsnSwitch4.createNetCard(), tsnDevice9.getNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnDevice10.getNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnDevice11.getNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnDevice12.getNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnDevice13.getNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnDevice14.getNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnDevice15.getNetCard());
        connectNetCard(tsnSwitch8.createNetCard(), tsnDevice16.getNetCard());
        connectNetCard(tsnSwitch8.createNetCard(), tsnDevice17.getNetCard());
        connectNetCard(tsnSwitch9.createNetCard(), tsnDevice18.getNetCard());
        connectNetCard(tsnSwitch9.createNetCard(), tsnDevice19.getNetCard());

        connectNetCard(tsnSwitch0.createNetCard(), tsnSwitch1.createNetCard());
        connectNetCard(tsnSwitch0.createNetCard(), tsnSwitch2.createNetCard());
        connectNetCard(tsnSwitch0.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch0.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch2.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch1.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnSwitch3.createNetCard());
        connectNetCard(tsnSwitch2.createNetCard(), tsnSwitch4.createNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnSwitch4.createNetCard());

        connectNetCard(tsnSwitch5.createNetCard(), tsnSwitch6.createNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnSwitch7.createNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnSwitch8.createNetCard());
        connectNetCard(tsnSwitch5.createNetCard(), tsnSwitch9.createNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnSwitch7.createNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnSwitch8.createNetCard());
        connectNetCard(tsnSwitch6.createNetCard(), tsnSwitch9.createNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnSwitch8.createNetCard());
        connectNetCard(tsnSwitch7.createNetCard(), tsnSwitch9.createNetCard());
        connectNetCard(tsnSwitch8.createNetCard(), tsnSwitch9.createNetCard());

        connectNetCard(tsnSwitch4.createNetCard(), tsnSwitch5.createNetCard());
        connectNetCard(tsnSwitch3.createNetCard(), tsnSwitch6.createNetCard());

        networkLauncher = NetworkLauncher.builder().topologyId(topology_id)
                .urlFront(computer.urls.get("tsn-topology")).build();
        networkLauncher.registerDevice(tsnDevice0);
        networkLauncher.registerDevice(tsnDevice1);
        networkLauncher.registerDevice(tsnDevice2);
        networkLauncher.registerDevice(tsnDevice3);
        networkLauncher.registerDevice(tsnDevice4);
        networkLauncher.registerDevice(tsnDevice5);
        networkLauncher.registerDevice(tsnDevice6);
        networkLauncher.registerDevice(tsnDevice7);
        networkLauncher.registerDevice(tsnDevice8);
        networkLauncher.registerDevice(tsnDevice9);
        networkLauncher.registerDevice(tsnDevice10);
        networkLauncher.registerDevice(tsnDevice11);
        networkLauncher.registerDevice(tsnDevice12);
        networkLauncher.registerDevice(tsnDevice13);
        networkLauncher.registerDevice(tsnDevice14);
        networkLauncher.registerDevice(tsnDevice15);
        networkLauncher.registerDevice(tsnDevice16);
        networkLauncher.registerDevice(tsnDevice17);
        networkLauncher.registerDevice(tsnDevice18);
        networkLauncher.registerDevice(tsnDevice19);
        networkLauncher.registerSwitch(tsnSwitch0);
        networkLauncher.registerSwitch(tsnSwitch1);
        networkLauncher.registerSwitch(tsnSwitch2);
        networkLauncher.registerSwitch(tsnSwitch3);
        networkLauncher.registerSwitch(tsnSwitch4);
        networkLauncher.registerSwitch(tsnSwitch5);
        networkLauncher.registerSwitch(tsnSwitch6);
        networkLauncher.registerSwitch(tsnSwitch7);
        networkLauncher.registerSwitch(tsnSwitch8);
        networkLauncher.registerSwitch(tsnSwitch9);

        streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();
//        streamLauncher.registerListenerServer(tsnDevice4, 0);
        streamLauncher.registerListenerServer(tsnDevice14, 0);
//        streamLauncher.registerListenerServer(tsnDevice16, 0);
    }

    public void start(int cir){
        for (int i = 0; i < cir; i++){
            generateStream();
        }
        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            if (str.equals("exit") || str.equals("quit") || str.equals("stop")){
                streamLauncher.removeListenerServer(tsnDevice0);
                streamLauncher.removeListenerServer(tsnDevice1);
                streamLauncher.removeListenerServer(tsnDevice2);
                streamLauncher.removeListenerServer(tsnDevice3);
                streamLauncher.removeListenerServer(tsnDevice4);
                streamLauncher.removeListenerServer(tsnDevice5);
                streamLauncher.removeListenerServer(tsnDevice6);
                streamLauncher.removeListenerServer(tsnDevice7);
                streamLauncher.removeListenerServer(tsnDevice8);
                streamLauncher.removeListenerServer(tsnDevice9);
                streamLauncher.removeListenerServer(tsnDevice10);
                streamLauncher.removeListenerServer(tsnDevice11);
                streamLauncher.removeListenerServer(tsnDevice12);
                streamLauncher.removeListenerServer(tsnDevice13);
                streamLauncher.removeListenerServer(tsnDevice14);
                streamLauncher.removeListenerServer(tsnDevice15);
                streamLauncher.removeListenerServer(tsnDevice16);
                streamLauncher.removeListenerServer(tsnDevice17);
                streamLauncher.removeListenerServer(tsnDevice18);
                streamLauncher.removeListenerServer(tsnDevice19);

                for (Header header: tsnDevice0.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice0, header);
                }
                tsnDevice0.talkerHeaders.clear();

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

                for (Header header: tsnDevice9.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice9, header);
                }
                tsnDevice9.talkerHeaders.clear();

                for (Header header: tsnDevice10.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice10, header);
                }
                tsnDevice10.talkerHeaders.clear();

                for (Header header: tsnDevice11.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice11, header);
                }
                tsnDevice11.talkerHeaders.clear();

                for (Header header: tsnDevice12.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice12, header);
                }
                tsnDevice12.talkerHeaders.clear();

                for (Header header: tsnDevice13.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice13, header);
                }
                tsnDevice13.talkerHeaders.clear();

                for (Header header: tsnDevice14.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice14, header);
                }
                tsnDevice14.talkerHeaders.clear();

                for (Header header: tsnDevice15.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice15, header);
                }
                tsnDevice15.talkerHeaders.clear();

                for (Header header: tsnDevice16.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice16, header);
                }
                tsnDevice16.talkerHeaders.clear();

                for (Header header: tsnDevice17.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice17, header);
                }
                tsnDevice17.talkerHeaders.clear();

                for (Header header: tsnDevice18.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice18, header);
                }
                tsnDevice18.talkerHeaders.clear();

                for (Header header: tsnDevice19.talkerHeaders){
                    streamLauncher.removeTalkerStream(tsnDevice19, header);
                }
                tsnDevice19.talkerHeaders.clear();

                networkLauncher.removeDevice(tsnDevice0);
                networkLauncher.removeDevice(tsnDevice1);
                networkLauncher.removeDevice(tsnDevice2);
                networkLauncher.removeDevice(tsnDevice3);
                networkLauncher.removeDevice(tsnDevice4);
                networkLauncher.removeDevice(tsnDevice5);
                networkLauncher.removeDevice(tsnDevice6);
                networkLauncher.removeDevice(tsnDevice7);
                networkLauncher.removeDevice(tsnDevice8);
                networkLauncher.removeDevice(tsnDevice9);
                networkLauncher.removeDevice(tsnDevice10);
                networkLauncher.removeDevice(tsnDevice11);
                networkLauncher.removeDevice(tsnDevice12);
                networkLauncher.removeDevice(tsnDevice13);
                networkLauncher.removeDevice(tsnDevice14);
                networkLauncher.removeDevice(tsnDevice15);
                networkLauncher.removeDevice(tsnDevice16);
                networkLauncher.removeDevice(tsnDevice17);
                networkLauncher.removeDevice(tsnDevice18);
                networkLauncher.removeDevice(tsnDevice19);
                networkLauncher.removeSwitch(tsnSwitch0);
                networkLauncher.removeSwitch(tsnSwitch1);
                networkLauncher.removeSwitch(tsnSwitch2);
                networkLauncher.removeSwitch(tsnSwitch3);
                networkLauncher.removeSwitch(tsnSwitch4);
                networkLauncher.removeSwitch(tsnSwitch5);
                networkLauncher.removeSwitch(tsnSwitch6);
                networkLauncher.removeSwitch(tsnSwitch7);
                networkLauncher.removeSwitch(tsnSwitch8);
                networkLauncher.removeSwitch(tsnSwitch9);
                break;
            }
        }
    }

    private void generateStream(){
        int body = 1000;

//        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice4, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice4, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice14, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice14, (short) 1);
//        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice16, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice0, tsnDevice16, (short) 1);
    }

    private void connectNetCard(NetCard n1, NetCard n2){
        n1.setConnectTo(n2.getName());
        n2.setConnectTo(n1.getName());
    }
}
