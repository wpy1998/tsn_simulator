package Yang;

import Facility.TSNDevice;
import RestfulAPI.RestfulDeleteInfo;
import RestfulAPI.RestfulPutInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.NonNull;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 8:57 PM
 */
public class StreamLauncher {
    private String talkerFront, listenerFront;
    @Builder
    public StreamLauncher(@NonNull String talkerFront, @NonNull String listenerFront){
        this.talkerFront = talkerFront;
        this.listenerFront = listenerFront;
    }

    private String convertUniqueID(int uniqueId){
        int front, next;
        next = uniqueId % 100;
        front = uniqueId % 10000 - next;
        String s1, s2;
        s1 = String.valueOf(front);
        s2 = String.valueOf(next);
        if (s1.length() == 1){
            s1 = "0" + s1;
        }
        if (s2.length() == 1){
            s2 = "0" + s2;
        }
        return s1 + "-" + s2;
    }

    public void registerTalkerStream(String body, TSNDevice device, int uniqueId,
                                     String dest_ip, String dest_mac){
        Header header = Header.builder().uniqueId(convertUniqueID(uniqueId))
                .rank((short) 0).mac(device.getNetCard().getMac().
                        replace(":", "-"))
                .ipv4(device.getNetCard().getIp()).hostName(device.getHostName())
                .dest_ip(dest_ip).dest_mac(dest_mac).build();
        device.talkerHeaders.add(header);
        join_talker(header, device.getHostName());

    }

    public void removeTalkerStream(TSNDevice device, Header header){
        leave_talker(header, device.getHostName());
//        for (Header h: device.talkerHeaders){
//            if (h.getKey().equals(header.getKey())){
//                break;
//            }
//        }
    }

    public void registerListenerServer(TSNDevice device, int uniqueId){
        if (device.listenerHeader != null){
            return;
        }
        Header header = Header.builder().uniqueId(convertUniqueID(uniqueId))
                .rank((short) 0).mac(device.getNetCard().getMac().
                        replace(":", "-"))
                .ipv4(device.getNetCard().getIp()).hostName(device.getHostName())
                .build();
        device.listenerHeader = header;
        join_listener(header, device.getHostName());
    }

    public void removeListenerServer(TSNDevice device){
        if (device.listenerHeader == null) return;
        leave_listener(device.listenerHeader, device.getHostName());
        device.listenerHeader = null;
    }

    private int join_talker(Header header, String hostName){
        String url = this.talkerFront + hostName + "/stream-list/" + header.getKey();
//        System.out.println(url);
        RestfulPutInfo restfulPutInfo = RestfulPutInfo.builder().url(url).build();

        JSONObject joinStream = header.getJSONObject(true, true,
                true, true, true,
                true, true);
        joinStream.put("body", "join talker");
        JSONArray streams = new JSONArray();
        streams.add(joinStream);
        JSONObject device = new JSONObject();
        device.put("stream-list", streams);
        return restfulPutInfo.putInfo(device.toString());
    }

    public int leave_talker(Header header, String hostName){
        String url = this.talkerFront + hostName + "/stream-list/" + header.getKey();
        System.out.println("--remove talker stream from controller--");
        RestfulDeleteInfo restfulDeleteInfo = RestfulDeleteInfo.builder().url(url).build();
        return restfulDeleteInfo.deleteInfo();
    }

    private int join_listener(Header header, String hostName){
        String url = this.listenerFront + hostName + "/stream-list/" + header.getKey();
//        System.out.println(url);
        RestfulPutInfo restfulPutInfo = RestfulPutInfo.builder().url(url).build();

        JSONObject joinStream = header.getJSONObject(true, false,
                true, false, false,
                true, true);
        joinStream.put("body", "join listener");
        JSONArray streams = new JSONArray();
        streams.add(joinStream);
        JSONObject device = new JSONObject();
        device.put("stream-list", streams);
        System.out.println("--register listener to controller--");
        return restfulPutInfo.putInfo(device.toString());
    }

    private int leave_listener(Header header, String hostName){
        String url = this.listenerFront + hostName + "/stream-list/" + header.getKey();
        RestfulDeleteInfo restfulDeleteInfo = RestfulDeleteInfo.builder().url(url).build();
        System.out.println("--remove listener from controller--");
        return restfulDeleteInfo.deleteInfo();
    }
}
