package Facility;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static Hardware.Computer.networkCardMap;
import static Hardware.Computer.portMap;

public class NetworkCard {
    private static int mac_id = 1;
    private String ip, mac, name, owner;
    private List<Port> ports;

    @Builder
    NetworkCard(@NonNull String name, @NonNull String ip,
                @NonNull String owner){
        this.name = name;
        this.ip = ip;
        this.owner = owner;
        initMac();
        ports = new ArrayList<>();
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public void setConnectTo(String connectTo, int speed){
        NetworkCard dest = networkCardMap.get(connectTo);
//        System.out.println(dest.owner + dest.getMac() + dest.getName());
        String linkName = getOwner() + getMac() + "(" + getName() + ")--" +
                dest.getOwner() + dest.getMac() + "(" + dest.getName() + ")";
        String flag = dest.getOwner() + dest.getMac() + "(" + dest.getName() + ")--" +
                getOwner() + getMac() + "(" + getName() + ")";
        if (portMap.get(linkName) == null){
            Port port = createPort(linkName, flag, name, speed);
            portMap.put(linkName, port);
        }
    }

    private Port createPort(String linkName, String connectTo, String fatherNetworkCard,
                            int speed){
        String portName;
        if (fatherNetworkCard == "ens33"){
            portName = "ens33";
        }else {
            portName = "swp" + ports.size();
        }
        Port port = Port.builder().name(portName)
                .owner(owner)
                .ip(this.ip)
                .mac(mac)
                .sendingSpeed(speed)
                .fatherNetworkCard(fatherNetworkCard)
                .linkId(linkName)
                .build();
        portMap.put(port.getLinkId(), port);
        ports.add(port);
        port.setConnectTo(connectTo);
        return port;
    }

    public synchronized int allocateMac(){
        int result = mac_id;
        mac_id++;
        return result;
    }

    private void initMac(){
        int last = allocateMac(), a, b, c, d, e, f;
        f = last % 256;
        last /= 256;
        e = last % 256;
        last /= 256;
        d = last % 256;
        last /= 256;
        c = last % 256;
        last /= 256;
        b = last % 256;
        last /= 256;
        a = last % 256;
        this.mac = convertTo16(a) + ":" + convertTo16(b) + ":" + convertTo16(c) + ":"
                + convertTo16(d) + ":" + convertTo16(e) + ":" + convertTo16(f);
    }

    public void setIp(String ip){
        this.ip = ip;
        for (Port port: this.ports){
            port.setIp(ip);
        }
    }

    public void setMac(String mac){
        this.mac = mac;
        for (Port port: this.ports){
            port.setMac(this.mac);
        }
    }

    private String convertTo16(int id){
        char template[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        String result = "";
        if (id < 16){
            result = result + "0" + template[id];
        }else if (id < 256){
            int a = id / 16;
            int b = id % 16;
            result = result + template[a] + template[b];
        }
        return result;
    }

    public String getIp(){
        return ip;
    }

    public String getMac(){
        return mac;
    }

    public String getName(){
        return name;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public String getOwner(){
        return owner;
    }
}
