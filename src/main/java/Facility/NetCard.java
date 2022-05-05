package Facility;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.NonNull;

import static Hardware.Computer.netCardMap;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 7:14 AM
 */
public class NetCard {
    private static int mac_id = 1;
    private String ip, mac, name, connectTo, owner, linkId;

    @Builder
    public NetCard(@NonNull String name, @NonNull String ip){
        this.name = name;
        this.owner = owner;
        this.ip = ip;
        setMac();
        this.connectTo = null;
        if (netCardMap.get(name) != null){
            this.name = name + "*";
        }
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
        char template[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int last = allocateMac();
        this.mac = "00:00:00:00:00:";
        if (last < 16){
            this.mac = this.mac + "0" + template[last];
        }else if (last < 256){
            int a = last / 16;
            int b = last % 16;
            this.mac = this.mac + template[a] + template[b];
        }
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

    public static void connectNetCard(NetCard n1, NetCard n2){
        n1.setConnectTo(n2.getName());
        n2.setConnectTo(n1.getName());
    }

    public String  getLinkId(){
        NetCard dest = netCardMap.get(connectTo);
        this.linkId = getName() + "(" + getMac() + ")--" + dest.getName() +
                "(" + dest.getMac() + ")";
        return linkId;
    }

    public JSONObject getLinkJSONObject(){
        NetCard dest = netCardMap.get(connectTo);
        if (dest == null){
            return null;
        }
        this.linkId = getName() + "(" + getMac() + ")--" + dest.getName() +
                "(" + dest.getMac() + ")";

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
        return link;
    }
}
