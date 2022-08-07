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

import static Hardware.Computer.netCardMap;
import static Hardware.Computer.topology_id;

public class FatTreeLauncher {
    TSNSwitch[] kernelSwitch, podSwitch;
    TSNDevice[] devices;
    StreamLauncher streamLauncher;
    NetworkLauncher networkLauncher;
    public FatTreeLauncher(){
        kernelSwitch = new TSNSwitch[64];
        podSwitch = new TSNSwitch[64];
        devices = new TSNDevice[1024];
    }

    public void init(Computer computer){
        for (int i = 0; i < kernelSwitch.length; i++){
            String num = String.valueOf(i);
            if (num.length() < 2){
                num = "0" + num;
            }
            kernelSwitch[i] = TSNSwitch.builder().hostName("KerS" + num).build();
            podSwitch[i] = TSNSwitch.builder().hostName("PodS" + num).build();
        }
        for (int i = 0; i < devices.length; i++){
            String num = String.valueOf(i);
            if (num.length() < 4){
                num = "0" + num;
            }
            devices[i] = TSNDevice.builder().hostName("TD" + num).sendingSpeed(10000).build();
        }
        for (int i = 0; i < 16; i++){
            int a, b, c, d;
            a = i * 4;
            b = a + 1;
            c = b + 1;
            d = c + 1;
            connectNetCard(podSwitch[a].createNetCard(), podSwitch[c].createNetCard());
            connectNetCard(podSwitch[a].createNetCard(), podSwitch[d].createNetCard());
            connectNetCard(podSwitch[b].createNetCard(), podSwitch[c].createNetCard());
            connectNetCard(podSwitch[b].createNetCard(), podSwitch[d].createNetCard());
            for (int j = 0; j < 16; j++){
                connectNetCard(podSwitch[a].createNetCard(), kernelSwitch[j * 4].createNetCard());
                connectNetCard(podSwitch[a].createNetCard(), kernelSwitch[j * 4 + 1].createNetCard());
                connectNetCard(podSwitch[b].createNetCard(), kernelSwitch[j * 4 + 2].createNetCard());
                connectNetCard(podSwitch[b].createNetCard(), kernelSwitch[j * 4 + 3].createNetCard());
            }
            for (int j = 0; j < 32; j++){
                TSNDevice device = devices[i * 64 + j];
                connectNetCard(podSwitch[c].createNetCard(device.getNetCard().getSendingSpeed()),
                        device.getNetCard());
                TSNDevice device1 = devices[i * 64 + j + 32];
                connectNetCard(podSwitch[d].createNetCard(device1.getNetCard().getSendingSpeed()),
                        device1.getNetCard());
            }
        }
//        for (int i = 0; i < 64; i++){
//            System.out.println("podS.netCard.size = " + podSwitch[i].netCards.size() +
//                    ", kernelS.netCard.size = " + kernelSwitch[i].netCards.size());
//        }
        networkLauncher = NetworkLauncher.builder().topologyId(topology_id)
                .urlFront(computer.urls.get("tsn-topology")).build();
        for (int i = 0; i < podSwitch.length; i++){
            networkLauncher.registerSwitch(podSwitch[i]);
            networkLauncher.registerSwitch(kernelSwitch[i]);
        }
        for (int i = 0; i < devices.length; i++){
            networkLauncher.registerDevice(devices[i]);
        }
        streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();
        streamLauncher.registerListenerServer(devices[100], 0);
        streamLauncher.registerListenerServer(devices[200], 0);
        streamLauncher.registerListenerServer(devices[300], 0);
        streamLauncher.registerListenerServer(devices[400], 0);
        streamLauncher.registerListenerServer(devices[500], 0);
        streamLauncher.registerListenerServer(devices[600], 0);
        streamLauncher.registerListenerServer(devices[700], 0);
        streamLauncher.registerListenerServer(devices[800], 0);
        streamLauncher.registerListenerServer(devices[900], 0);
        streamLauncher.registerListenerServer(devices[1000], 0);
    }

    public void start(int cir){
        generate(cir);
        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            if (str.equals("exit") || str.equals("quit") || str.equals("stop")){
                for (int i = 0; i < podSwitch.length; i++){
                    networkLauncher.removeSwitch(podSwitch[i]);
                    networkLauncher.removeSwitch(kernelSwitch[i]);
                }
                for (int i = 0; i < devices.length; i++){
                    networkLauncher.removeDevice(devices[i]);
                    streamLauncher.removeListenerServer(devices[i]);
                    for (Header header: devices[i].talkerHeaders){
                        streamLauncher.removeTalkerStream(devices[i], header);
                    }
                    devices[i].talkerHeaders.clear();
                }
                break;
            }else if (str.equals("clear")){
                for (int i = 0; i < devices.length; i++){
                    for (Header header: devices[i].talkerHeaders){
                        streamLauncher.removeTalkerStream(devices[i], header);
                    }
                    devices[i].talkerHeaders.clear();
                }
                System.out.print("please input a number: ");
                cir = Integer.parseInt(scanner.next());
                generate(cir);
            }
        }
    }

    private void generate(int cir){
        for (int i = 0; i < cir; i++){
            if (i % 100 < 90){
                generateUnicastStream(10000);
            }else if (i % 100 < 99){
                generateUnicastStream(1000000);
            }else {
                generateUnicastStream(10000000);
            }
//            generateBroadcastStream(100000);
        }
    }

    private void generateUnicastStream(int body){
        List<TSNDevice> tsnDevices1 = new ArrayList<>();
        tsnDevices1.add(devices[100]);
        List<TSNDevice> tsnDevices2 = new ArrayList<>();
        tsnDevices2.add(devices[200]);
        List<TSNDevice> tsnDevices3 = new ArrayList<>();
        tsnDevices3.add(devices[300]);
        List<TSNDevice> tsnDevices4 = new ArrayList<>();
        tsnDevices4.add(devices[400]);
        List<TSNDevice> tsnDevices5 = new ArrayList<>();
        tsnDevices5.add(devices[500]);
        List<TSNDevice> tsnDevices6 = new ArrayList<>();
        tsnDevices6.add(devices[600]);
        List<TSNDevice> tsnDevices7 = new ArrayList<>();
        tsnDevices7.add(devices[700]);
        List<TSNDevice> tsnDevices8 = new ArrayList<>();
        tsnDevices8.add(devices[800]);
        List<TSNDevice> tsnDevices9 = new ArrayList<>();
        tsnDevices9.add(devices[900]);
        List<TSNDevice> tsnDevices10 = new ArrayList<>();
        tsnDevices10.add(devices[1000]);

        streamLauncher.registerTalkerStream(body, devices[0], tsnDevices1, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[0], tsnDevices1, (short) 1);
        streamLauncher.registerTalkerStream(body, devices[101], tsnDevices2, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[101], tsnDevices2, (short) 1);
        streamLauncher.registerTalkerStream(body, devices[201], tsnDevices3, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[201], tsnDevices3, (short) 1);
        streamLauncher.registerTalkerStream(body, devices[301], tsnDevices4, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[301], tsnDevices4, (short) 1);
        streamLauncher.registerTalkerStream(body, devices[401], tsnDevices5, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[401], tsnDevices5, (short) 1);
    }

    public void generateBroadcastStream(int body){
//        List<TSNDevice> tsnDevices1 = new ArrayList<>();
//        tsnDevices1.add(tsnDevice3);
//        tsnDevices1.add(tsnDevice5);
//        tsnDevices1.add(tsnDevice7);
//        List<TSNDevice> tsnDevices2 = new ArrayList<>();
//        tsnDevices2.add(tsnDevice1);
//        tsnDevices2.add(tsnDevice5);
//        tsnDevices2.add(tsnDevice7);
//        List<TSNDevice> tsnDevices3 = new ArrayList<>();
//        tsnDevices3.add(tsnDevice1);
//        tsnDevices3.add(tsnDevice3);
//        tsnDevices3.add(tsnDevice7);
//        List<TSNDevice> tsnDevices4 = new ArrayList<>();
//        tsnDevices4.add(tsnDevice1);
//        tsnDevices4.add(tsnDevice3);
//        tsnDevices4.add(tsnDevice5);
//
//        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices1, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice2, tsnDevices1, (short) 1);
//        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices2, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice4, tsnDevices2, (short) 1);
//        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices3, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice6, tsnDevices3, (short) 1);
//        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices4, (short) 0);
//        streamLauncher.registerTalkerStream(body, tsnDevice8, tsnDevices4, (short) 1);
    }

    public void connectNetCard(NetCard n1, NetCard n2){
        n1.setConnectTo(n2.getName());
        n2.setConnectTo(n1.getName());
    }
}
