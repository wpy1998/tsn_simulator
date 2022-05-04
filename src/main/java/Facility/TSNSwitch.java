package Facility;

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
public class TSNSwitch {
    public List<NetCard> netCards;
    private String hostName, ip;

    @Builder
    public TSNSwitch(@NonNull String hostName){
        this.hostName = hostName;
        if (switchMap.get(this.hostName) != null){
            this.hostName = this.hostName + "*";
        }
        setIp();
        switchMap.put(this.hostName, this);
        netCards = new ArrayList<>();
    }

    public NetCard createNetCard(){
        NetCard netCard = NetCard.builder().name(this.hostName + "-netCard" + netCards.size())
                .owner(this.hostName).ip(this.ip).build();
        netCards.add(netCard);
        netCardMap.put(netCard.getName(), netCard);
        return netCard;
    }

    public String getHostMerge(){
        if (netCards.size() == 0){
            return hostName;
        }else {
            return hostName + ":" + netCards.get(0).getMac();
        }
    }

    public void setIp(){
        int last = allocateIp();
        this.ip = "10.0.0." + Integer.toString(last);
    }

    public JSONObject getNodeJSONObject(){
        JSONObject node = new JSONObject();
        node.put("node-id", getHostMerge());
        node.put("node-type", "switch");
        node.put("id", netCards.get(0).getMac());
        node.put("port", 830);
        node.put("username", "admin");
        node.put("password", "admin");

        JSONArray terminationPoint = new JSONArray();
        for (NetCard netCard: netCards){
            JSONObject tp = new JSONObject();
            tp.put("tp-id", netCard.getName());
            terminationPoint.add(tp);
        }
        node.put("termination-point", terminationPoint);

        return node;
    }
}
