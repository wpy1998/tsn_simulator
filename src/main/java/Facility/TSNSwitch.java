package Facility;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static Hardware.Computer.*;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:10 AM
 */
public class TSNSwitch {
    public List<Port> ports;
    private String hostName, ip_lan, ip_wan, hostMerge;
    @Getter
    private NetworkCard wan, lan;

    @Builder
    public TSNSwitch(@NonNull String hostName){
        this.hostName = hostName;
        if (switchMap.get(this.hostName) != null){
            this.hostName = this.hostName + "*";
        }
        setIp_lan();
        setIp_wan();
        switchMap.put(this.hostName, this);
        ports = new ArrayList<>();
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

    public void setIp_lan(){
        int last = allocateIp();
        this.ip_lan = "10.0.0." + last;
    }

    public void setIp_wan(){
        int last = allocateIp();
        this.ip_wan = "10.0.0." + last;
    }

    public JSONObject getNodeJSONObject(){
        JSONObject node = new JSONObject();
        node.put("node-id", getHostMerge());
        node.put("node-type", "switch");
        node.put("id", lan.getMac());
        node.put("port", 830);
        node.put("username", "admin");
        node.put("password", "admin");

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
            terminationPoint.add(tp);
        }
        node.put("termination-point", terminationPoint);

        return node;
    }
}
