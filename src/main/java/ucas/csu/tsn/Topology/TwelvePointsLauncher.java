package ucas.csu.tsn.Topology;

import ucas.csu.tsn.Facility.NetworkCard;
import ucas.csu.tsn.Facility.TSNDevice;
import ucas.csu.tsn.Facility.TSNSwitch;
import ucas.csu.tsn.Hardware.Computer;
import ucas.csu.tsn.Yang.Header;
import ucas.csu.tsn.Yang.NetworkLauncher;
import ucas.csu.tsn.Yang.StreamLauncher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ucas.csu.tsn.Hardware.Computer.topology_id;

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
        connectNetCard(tsnSwitch1.getLan(), tsnDevice1.getNetworkCard());
        connectNetCard(tsnSwitch1.getLan(), tsnDevice2.getNetworkCard());
        connectNetCard(tsnSwitch5.getLan(), tsnDevice3.getNetworkCard());
        connectNetCard(tsnSwitch5.getLan(), tsnDevice4.getNetworkCard());
        connectNetCard(tsnSwitch9.getLan(), tsnDevice5.getNetworkCard());
        connectNetCard(tsnSwitch9.getLan(), tsnDevice6.getNetworkCard());
        connectNetCard(tsnSwitch11.getLan(), tsnDevice7.getNetworkCard());
        connectNetCard(tsnSwitch11.getLan(), tsnDevice8.getNetworkCard());

        //switch - - switch
        connectNetCard(tsnSwitch1.getLan(), tsnSwitch2.getLan());
        connectNetCard(tsnSwitch1.getLan(), tsnSwitch3.getLan());
        connectNetCard(tsnSwitch2.getLan(), tsnSwitch3.getLan());
        connectNetCard(tsnSwitch4.getLan(), tsnSwitch5.getLan());
        connectNetCard(tsnSwitch5.getLan(), tsnSwitch6.getLan());
        connectNetCard(tsnSwitch6.getLan(), tsnSwitch7.getLan());
        connectNetCard(tsnSwitch7.getLan(), tsnSwitch8.getLan());
        connectNetCard(tsnSwitch8.getLan(), tsnSwitch9.getLan());
        connectNetCard(tsnSwitch9.getLan(), tsnSwitch10.getLan());
        connectNetCard(tsnSwitch10.getLan(), tsnSwitch11.getLan());
        connectNetCard(tsnSwitch11.getLan(), tsnSwitch12.getLan());
        connectNetCard(tsnSwitch12.getLan(), tsnSwitch10.getLan());
        connectNetCard(tsnSwitch3.getLan(), tsnSwitch4.getLan());
        connectNetCard(tsnSwitch4.getLan(), tsnSwitch7.getLan());
        connectNetCard(tsnSwitch7.getLan(), tsnSwitch10.getLan());
        connectNetCard(tsnSwitch10.getLan(), tsnSwitch3.getLan());

//        //extra
//        connectNetCard(tsnSwitch3.createNetCard(), tsnSwitch7.createNetCard());
//        connectNetCard(tsnSwitch4.createNetCard(), tsnSwitch10.createNetCard());

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
        generate(cir);
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
            }else if (str.equals("clear")){
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
                System.out.print("please input a number: ");
                cir = Integer.parseInt(scanner.next());
                generate(cir);
            }
        }
    }

    private void generate(int cir){
        for (int i = 0; i < cir; i++){
            if (i % 100 == 99){
                generateUnicastStream(10000000);
            }else if (i % 10 == 9){
                generateUnicastStream(1000000);
            }else {
                generateUnicastStream(10000);
            }
//            generateBroadcastStream(100000);
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
        List<TSNDevice> tsnDevices1 = new ArrayList<>();
        tsnDevices1.add(tsnDevice3);
        tsnDevices1.add(tsnDevice5);
        tsnDevices1.add(tsnDevice7);
        List<TSNDevice> tsnDevices2 = new ArrayList<>();
        tsnDevices2.add(tsnDevice1);
        tsnDevices2.add(tsnDevice5);
        tsnDevices2.add(tsnDevice7);
        List<TSNDevice> tsnDevices3 = new ArrayList<>();
        tsnDevices3.add(tsnDevice1);
        tsnDevices3.add(tsnDevice3);
        tsnDevices3.add(tsnDevice7);
        List<TSNDevice> tsnDevices4 = new ArrayList<>();
        tsnDevices4.add(tsnDevice1);
        tsnDevices4.add(tsnDevice3);
        tsnDevices4.add(tsnDevice5);

        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices1, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices1, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices2, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices2, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices3, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices3, (short) 1);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices4, (short) 0);
        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices4, (short) 1);
    }

    public void connectNetCard(NetworkCard n1, NetworkCard n2){
        connectNetCard(n1, n2, 1000);
    }

    public void connectNetCard(NetworkCard n1, NetworkCard n2, int speed){
        n1.setConnectTo(n2.getOwner() + n2.getMac() + n2.getName(), speed);
        n2.setConnectTo(n1.getOwner() + n1.getMac() + n1.getName(), speed);
    }
}
