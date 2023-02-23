package ucas.csu.tsn.Topology;

import ucas.csu.tsn.Facility.NetworkCard;
import ucas.csu.tsn.Facility.TSNDevice;
import ucas.csu.tsn.Facility.TSNSwitch;

public class ForlinxLauncher {
    TSNSwitch tsnSwitch1, tsnSwitch2, tsnSwitch3, tsnSwitch4;
    TSNDevice tsnDevice1, tsnDevice2;

    public ForlinxLauncher(){
    }

    public void init(){
        tsnSwitch1 = TSNSwitch.builder().hostName("forlinx").build();
        tsnSwitch2 = TSNSwitch.builder().hostName("forlinx").build();
        tsnSwitch3 = TSNSwitch.builder().hostName("forlinx").build();
        tsnSwitch4 = TSNSwitch.builder().hostName("forlinx").build();
        tsnSwitch1.setIp_lan("192.168.1.11");
        tsnSwitch1.setMac_lan("22:58:d2:0a:3e:4a");
        tsnSwitch2.setIp_lan("192.168.1.12");
        tsnSwitch2.setMac_lan("e6:cd:ac:77:36:6c");
        tsnSwitch3.setIp_lan("192.168.1.13");
        tsnSwitch3.setMac_lan("");
        tsnSwitch4.setIp_lan("192.168.1.14");
        tsnSwitch4.setMac_lan("");
        connectNetCard(tsnSwitch1.getLan(), tsnSwitch2.getLan());
    }

    public void connectNetCard(NetworkCard n1, NetworkCard n2){
        connectNetCard(n1, n2, 1000);
    }

    public void connectNetCard(NetworkCard n1, NetworkCard n2, int speed){
        n1.setConnectTo(n2.getOwner() + n2.getMac() + n2.getName(), speed);
        n2.setConnectTo(n1.getOwner() + n1.getMac() + n1.getName(), speed);
    }
}
