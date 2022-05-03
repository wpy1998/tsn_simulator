package Facility;

import Yang.Header;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static Hardware.Computer.*;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:10 AM
 */
public class TSNDevice {
    private NetCard netCard = null;
    private String hostName;
    public List<Header> talkerHeaders;
    public Header listenerHeader;
    private int uniqueId;

    @Builder
    public TSNDevice(@NonNull String hostName){
        this.hostName = hostName;
        this.uniqueId = 0;
        talkerHeaders = new ArrayList<>();
        if (deviceMap.get(this.hostName) != null){
            this.hostName = this.hostName + "*";
        }
        deviceMap.put(this.hostName, this);
        createNetCard();
    }

    private NetCard createNetCard(){
        netCard = NetCard.builder().name(this.hostName + "-netCard")
                .owner(this.hostName).build();
        netCardMap.put(netCard.getName(), netCard);
        return netCard;
    }

    public NetCard getNetCard(){
        return netCard;
    }

    public int allocateUniqueId(){
        int result = this.uniqueId;
        uniqueId++;
        return result;
    }

    public String getHostMerge(){
        if (netCard == null){
            return hostName;
        }else {
            return hostName + ":" + netCard.getMac();
        }
    }

    public JSONObject getNodeJSONObject(){
        JSONObject node = new JSONObject();
        node.put("node-id", getHostMerge());
        node.put("node-type", "device");
        node.put("id", this.netCard.getMac());

        JSONArray addresses = new JSONArray();
        JSONObject address = new JSONObject();
        address.put("id", 0);
        address.put("mac", netCard.getMac());
        address.put("ip", netCard.getIp());
        address.put("first-seen", firstSeen);
        address.put("last-seen", System.currentTimeMillis());
        addresses.add(address);
        node.put("addresses", addresses);

        JSONArray terminationPoint = new JSONArray();
        JSONObject tp = new JSONObject();
        tp.put("tp-id", netCard.getName());
        terminationPoint.add(tp);
        node.put("termination-point", terminationPoint);

        JSONArray attachmentPoint = new JSONArray();
        JSONObject ap = new JSONObject();
        ap.put("tp-id", netCard.getName());
        ap.put("corresponding-tp", netCard.getConnectTo());
        ap.put("active", true);
        attachmentPoint.add(ap);
        node.put("attachment-points", attachmentPoint);

        return node;
    }
}
