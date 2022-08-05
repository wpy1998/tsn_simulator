package Facility;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static Hardware.Computer.deviceMap;
import static Hardware.Computer.netCardMap;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:14 AM
 */
public class NetCard {
    private static int mac_id = 1;
    @Getter
    private int sendingSpeed;
    private double avg, best, worst, loss;
    private String ip, mac, name, connectTo, linkId, owner;

    @Builder
    public NetCard(@NonNull String name, @NonNull String ip, Double avg, Double best, Double worst,
                   Double loss, Integer sendingSpeed, @NonNull String owner){
        this.name = name;
        this.ip = ip;
        this.owner = owner;
        this.sendingSpeed = (sendingSpeed == null) ? 1000 : sendingSpeed;
        this.avg = (avg == null) ? 1.70 : avg;
        this.best = (best == null) ? 0.99 : best;
        this.worst = (worst == null) ? 2.2 : worst;
        this.loss = (loss == null) ? 0.0 : loss;
        setMac();
        this.connectTo = null;
        if (netCardMap.get(name) != null){
            this.name = name + "*";
        }
//        System.out.println(mac);
        netCardMap.put(this.name, this);
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public void setConnectTo(String connectTo){
        this.connectTo = connectTo;
    }

    public synchronized int allocateMac(){
        int result = mac_id;
        mac_id++;
        return result;
    }

    public void setMac(){
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

    public String getConnectTo(){
        return connectTo;
    }

    public String getOwner(){
        return owner;
    }

    public String getLinkId(){
        NetCard destPort = netCardMap.get(connectTo);
        this.linkId = getOwner() + "(" + getName() + ")--" + destPort.getOwner() +
                "(" + destPort.getName() + ")";
        return linkId;
    }

    public JSONObject getLinkJSONObject(){
        NetCard dest = netCardMap.get(connectTo);
        if (dest == null){
            return null;
        }
        this.linkId = getLinkId();

        JSONObject link = new JSONObject();
        link.put("link-id", linkId);
        JSONObject source = new JSONObject();
        source.put("source-tp", getName());
        source.put("source-node", this.owner);
        link.put("source", source);
        JSONObject destination = new JSONObject();
        destination.put("dest-node", dest.getOwner());
        destination.put("dest-tp", dest.getName());
        link.put("destination", destination);
        link.put("speed", getSpeed());
        return link;
    }

    public JSONObject getSpeed(){
        JSONObject speed = new JSONObject();
        speed.put("sending-speed", this.sendingSpeed);
        speed.put("loss", 0.0);
        speed.put("best-transmission-delay", 0.99);
        speed.put("worst-transmission-delay", 2.22);
        speed.put("avg-transmission-delay", 1.70);
        return speed;
    }
}
