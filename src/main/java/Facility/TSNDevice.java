package Facility;

import Yang.Header;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.List;

import static Hardware.Computer.*;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:10 AM
 */
public class TSNDevice {
    private NetworkCard networkCard = null;
    private String hostName, ip, hostMerge;
    public List<Header> talkerHeaders;
    public Header listenerHeader;
    private int uniqueId;
    @Getter
    private int sendingSpeed;

    @Builder
    public TSNDevice(@NonNull String hostName, Integer sendingSpeed){
        this.hostName = hostName;
        this.uniqueId = 0;
        this.sendingSpeed = (sendingSpeed == null) ? 1000 : sendingSpeed;
        talkerHeaders = new ArrayList<>();
        if (deviceMap.get(this.hostName) != null){
            this.hostName = this.hostName + "*";
        }
        setIp();
        deviceMap.put(this.hostName, this);
        createNetCard();
        setHostMerge();
    }

    private void createNetCard(){
        networkCard = NetworkCard.builder()
                .name("ens33")
                .owner(hostName)
                .ip(this.ip).build();
        networkCardMap.put(this.hostName + networkCard.getMac() + networkCard.getName(),
                networkCard);
    }

    public NetworkCard getNetworkCard(){
        return networkCard;
    }

    public int allocateUniqueId(){
        int result = this.uniqueId;
        uniqueId++;
        return result;
    }

    private void setHostMerge(){
        hostMerge = hostName + networkCard.getMac();
    }

    public String getHostMerge(){
        return hostMerge;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(){
        int last = allocateIp();
        int a, b, c, d;
        d = last % 256;
        last /= 256;
        c = last % 256;
        last /= 256;
        b = last % 256;
        last /= 256;
        a = last % 256;
        this.ip = "10." + b + "." + c + "." + d;
    }

    public JSONObject getNodeJSONObject(){
        JSONObject node = new JSONObject();
        node.put("node-id", getHostMerge());
        node.put("node-type", "device");
        node.put("id", this.networkCard.getMac());

        JSONArray addresses = new JSONArray();
        JSONObject address = new JSONObject();
        address.put("id", 0);
        address.put("mac", networkCard.getMac());
        address.put("ip", networkCard.getIp());
        address.put("first-seen", firstSeen);
        address.put("last-seen", System.currentTimeMillis());
        addresses.add(address);
        node.put("addresses", addresses);

        JSONArray terminationPoint = new JSONArray();
        JSONArray attachmentPoint = new JSONArray();
        for (int i = 0; i < networkCard.getPorts().size(); i++){
            Port port = networkCard.getPorts().get(i);
            JSONObject tp = new JSONObject();
            tp.put("tp-id", port.getName());
            terminationPoint.add(tp);

            JSONObject ap = new JSONObject();
            ap.put("tp-id", port.getName());
            ap.put("corresponding-tp", port.getConnectTo());
            ap.put("active", true);
            attachmentPoint.add(ap);
        }
        node.put("termination-point", terminationPoint);
        node.put("attachment-points", attachmentPoint);
        return node;
    }
}
