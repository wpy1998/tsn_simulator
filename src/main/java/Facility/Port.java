package Facility;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import static Hardware.Computer.portMap;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:14 AM
 */
public class Port {
    @Getter
    private int sendingSpeed;
    private double avg, best, worst, loss;
    private String ip, mac, name, connectTo, linkId, owner, fatherNetworkCard;

    @Builder
    public Port(@NonNull String name, @NonNull String ip,
                @NonNull String owner, @NonNull String mac,
                @NonNull String fatherNetworkCard,
                @NonNull String linkId,
                Double avg, Double best, Double worst,
                Double loss, Integer sendingSpeed){
        this.name = name;
        this.ip = ip;
        this.owner = owner;
        this.fatherNetworkCard = fatherNetworkCard;
        this.mac = mac;
        this.sendingSpeed = (sendingSpeed == null) ? 1000 : sendingSpeed;
        this.avg = (avg == null) ? 1.70 : avg;
        this.best = (best == null) ? 0.99 : best;
        this.worst = (worst == null) ? 2.2 : worst;
        this.loss = (loss == null) ? 0.0 : loss;
        this.connectTo = null;
        if (portMap.get(name) != null){
            this.name = name + "*";
        }
        this.linkId = linkId;
//        System.out.println(mac);
        portMap.put(this.name, this);
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public void setConnectTo(String connectTo){
        this.connectTo = connectTo;
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
        return linkId;
    }

    public JSONObject getLinkJSONObject(){
        Port dest = portMap.get(connectTo);
        if (dest == null){
            return null;
        }
        this.linkId = getLinkId();

        JSONObject link = new JSONObject();
        link.put("link-id", linkId);
        JSONObject source = new JSONObject();
        source.put("source-tp", getName());
        source.put("source-node", this.owner + getMac());
        link.put("source", source);
        JSONObject destination = new JSONObject();
        destination.put("dest-node", dest.getOwner() + dest.getMac());
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
