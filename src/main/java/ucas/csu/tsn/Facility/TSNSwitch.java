package ucas.csu.tsn.Facility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import static ucas.csu.tsn.Hardware.Computer.*;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:10 AM
 */
public class TSNSwitch {
    private String hostName, ip_lan, ip_wan, hostMerge;
    @Getter
    private NetworkCard wan, lan;

    @Builder
    public TSNSwitch(@NonNull String hostName, String mac){
        this.hostName = hostName;
        if (switchMap.get(this.hostName) != null){
            this.hostName = this.hostName + "*";
        }
        initIP();
        switchMap.put(this.hostName, this);
        createNetworkCard();
    }

    public void createNetworkCard(){
        wan = NetworkCard.builder().name("eno0").owner(hostName)
                .ip(this.ip_wan).build();
        lan = NetworkCard.builder().name("eno2").owner(hostName)
                .ip(this.ip_lan).build();
        networkCardMap.put(this.hostName + lan.getMac() + wan.getName(), wan);
        networkCardMap.put(this.hostName + lan.getMac() + lan.getName(), lan);
    }

    public String getHostMerge(){
        hostMerge = hostName + lan.getMac();
        return hostMerge;
    }

    public void initIP(){
        int last = allocateIp();
        this.ip_lan = "10.0.0." + last;
        last = allocateIp();
        this.ip_wan = "10.0.0." + last;
    }

    public void setIp_lan(String ip_lan){
        this.ip_lan = ip_lan;
        lan.setIp(ip_lan);
    }

    public void setIp_wan(String ip_wan){
        this.ip_wan = ip_wan;
        wan.setIp(ip_wan);
    }

    public void setMac_lan(String mac){
        lan.setMac(mac);
    }

    public void setMac_wan(String mac){
        wan.setMac(mac);
    }

    public JSONObject getNodeJSONObject(){
        JSONObject node = new JSONObject();
        node.put("node-id", lan.getMac().replace(":", "-"));
        node.put("node-type", "switch");
        node.put("id", lan.getMac());
        node.put("port", 830);
        node.put("username", "admin");
        node.put("password", "admin");

        JSONArray addresses = new JSONArray();
        JSONObject address = new JSONObject();
        address.put("id", 0);
        address.put("mac", lan.getMac());
        address.put("ip", lan.getIp());
        address.put("first-seen", firstSeen);
        address.put("last-seen", System.currentTimeMillis());
        addresses.add(address);
        node.put("addresses", addresses);

        JSONArray terminationPoint = new JSONArray();
        for (int i = 0; i < wan.getPorts().size(); i++){
            Port port = wan.getPorts().get(i);
            JSONObject tp = new JSONObject();
            tp.put("tp-id", port.getName());
            terminationPoint.add(tp);
        }
        for (int i = 0; i < lan.getPorts().size(); i++){
            Port port = lan.getPorts().get(i);
            JSONObject tp = new JSONObject();
            tp.put("tp-id", port.getName());
            tp.put("bridge-name", "br0");
            terminationPoint.add(tp);
        }
        node.put("termination-point", terminationPoint);

        return node;
    }
}
