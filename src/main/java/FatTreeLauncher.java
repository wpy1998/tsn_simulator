import Facility.NetCard;
import Facility.TSNDevice;
import Facility.TSNSwitch;
import Hardware.Computer;
import Yang.Header;
import Yang.NetworkLauncher;
import Yang.StreamLauncher;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Hardware.Computer.topology_id;

public class FatTreeLauncher {
    @Setter
    int k;
    TSNSwitch[] kernelSwitch, podSwitch;
    TSNDevice[] devices;
    StreamLauncher streamLauncher;
    NetworkLauncher networkLauncher;
    public FatTreeLauncher(){
        k = 8;
    }

    public void init(Computer computer){
        kernelSwitch = new TSNSwitch[k * k / 4];
        podSwitch = new TSNSwitch[k * k];
        devices = new TSNDevice[k * k * k / 4];
        for (int i = 0; i < kernelSwitch.length; i++){
            String num = String.valueOf(i);
            if (num.length() < 2){
                num = "0" + num;
            }
            kernelSwitch[i] = TSNSwitch.builder().hostName("KerS" + num).build();
        }
        for (int i = 0; i < podSwitch.length; i++){
            String num = String.valueOf(i);
            if (num.length() < 2){
                num = "0" + num;
            }
            podSwitch[i] = TSNSwitch.builder().hostName("PodS" + num).build();
        }
        for (int i = 0; i < devices.length; i++){
            String num = String.valueOf(i);
            if (num.length() < 4){
                num = "0" + num;
            }
            devices[i] = TSNDevice.builder().hostName("TD" + num).sendingSpeed(10000).build();
        }

        for (int i = 0; i < k; i++){//pod k
            System.out.println("pod" + i);
            for (int j = k * i; j < (k * i + k / 2); j++){
                for (int m = (k * i + k / 2); m < (k * i + k); m++){
                    connectNetCard(podSwitch[j].createNetCard(), podSwitch[m].createNetCard());
//                    System.out.printf("podSwitch[%d] connected podSwitch[%d]\n", j, m);
                }
                for (int m = 0; m < kernelSwitch.length; m++){
                    if (m * 2 / k == (j % k)){
                        connectNetCard(podSwitch[j].createNetCard(), kernelSwitch[m].createNetCard());
//                        System.out.printf("podSwitch[%d] connected kernelSwitch[%d]\n", j, m);
                    }
                }
            }
            for (int j = 0; j < k / 2; j++){
                int a = k * i + k / 2 + j;
                for (int m = 0; m < k / 2; m++){
                    int b = m + j * k / 2 + i * k * k / 4;
                    TSNDevice device = devices[b];
                    connectNetCard(podSwitch[a].createNetCard(device.getNetCard().getSendingSpeed()),
                            device.getNetCard());
//                    System.out.printf("podSwitch[%d] connected device[%d]\n", a, b);
                }
            }
        }
//        for (int i = 0; i < podSwitch.length; i++){
//            System.out.println("podS[" + i + "].netCard.size = " + podSwitch[i].netCards.size());
//        }
//        for (int i = 0; i < kernelSwitch.length; i++){
//            System.out.println("kernelS[" + i + "].netCard.size = " + kernelSwitch[i].netCards.size());
//        }
        networkLauncher = NetworkLauncher.builder().topologyId(topology_id)
                .urlFront(computer.urls.get("tsn-topology")).build();
        for (int i = 0; i < podSwitch.length; i++){
            networkLauncher.registerSwitch(podSwitch[i]);
        }
        for (int i = 0; i < kernelSwitch.length; i++){
            networkLauncher.registerSwitch(kernelSwitch[i]);
        }
        for (int i = 0; i < devices.length; i++){
            networkLauncher.registerDevice(devices[i]);
        }
        streamLauncher = StreamLauncher.builder()
                .talkerFront(computer.urls.get("tsn-talker"))
                .listenerFront(computer.urls.get("tsn-listener")).build();
        streamLauncher.registerListenerServer(devices[0], 0);
        streamLauncher.registerListenerServer(devices[10], 0);
        streamLauncher.registerListenerServer(devices[20], 0);
        streamLauncher.registerListenerServer(devices[30], 0);
        streamLauncher.registerListenerServer(devices[40], 0);
    }

    public void start(int cir){
        generate(cir);
        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            if (str.equals("exit") || str.equals("quit") || str.equals("stop")){
                for (int i = 0; i < podSwitch.length; i++){
                    networkLauncher.removeSwitch(podSwitch[i]);
                }
                for (int i = 0; i < kernelSwitch.length; i++){
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
            if (i % 100 == 99){
                generateUnicastStream(128000000);
            }else if (i % 10 == 9){
                generateUnicastStream(1280000);
            }else {
                generateUnicastStream(128000);
            }
//            generateBroadcastStream(100000);
        }
    }

    private void generateUnicastStream(int body){
        List<TSNDevice> tsnDevices0 = new ArrayList<>();
        tsnDevices0.add(devices[0]);
        List<TSNDevice> tsnDevices1 = new ArrayList<>();
        tsnDevices1.add(devices[10]);
        List<TSNDevice> tsnDevices2 = new ArrayList<>();
        tsnDevices2.add(devices[20]);
        List<TSNDevice> tsnDevices3 = new ArrayList<>();
        tsnDevices3.add(devices[30]);
        List<TSNDevice> tsnDevices4 = new ArrayList<>();
        tsnDevices4.add(devices[40]);

        streamLauncher.registerTalkerStream(body, devices[k * k / 4], tsnDevices0, (short) 0);
        streamLauncher.registerTalkerStream(body, devices[k * k / 4], tsnDevices0, (short) 1);
//        streamLauncher.registerTalkerStream(body, devices[71], tsnDevices2, (short) 0);
//        streamLauncher.registerTalkerStream(body, devices[71], tsnDevices2, (short) 1);
//        streamLauncher.registerTalkerStream(body, devices[81], tsnDevices3, (short) 0);
//        streamLauncher.registerTalkerStream(body, devices[81], tsnDevices3, (short) 1);
//        streamLauncher.registerTalkerStream(body, devices[91], tsnDevices4, (short) 0);
//        streamLauncher.registerTalkerStream(body, devices[91], tsnDevices4, (short) 1);
//        streamLauncher.registerTalkerStream(body, devices[101], tsnDevices5, (short) 0);
//        streamLauncher.registerTalkerStream(body, devices[101], tsnDevices5, (short) 1);
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
