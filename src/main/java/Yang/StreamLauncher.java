package Yang;

import Facility.TSNDevice;
import RestfulAPI.RestfulDeleteInfo;
import RestfulAPI.RestfulPutInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        front = (uniqueId % 10000) / 100;
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

    public void registerTalkerStream(int body, TSNDevice source,
                                     List<TSNDevice> dest, short rank){
        List<String> ips = new ArrayList<>(), macs = new ArrayList<>();
        for (int i = 0; i < dest.size(); i++){
            TSNDevice device = dest.get(i);
            ips.add(device.getIp());
            macs.add(device.getNetCard().getMac().replace(":", "-"));
        }
        Header header = Header.builder()
                .uniqueId(convertUniqueID(source.allocateUniqueId()))
                .rank(rank)
                .mac(source.getNetCard().getMac().replace(":", "-"))
                .ipv4(source.getNetCard().getIp())
                .hostName(source.getHostMerge())
                .dest_ip(ips)
                .dest_mac(macs)
                .build();
        source.talkerHeaders.add(header);
        join_talker(header, source.getHostMerge(), "Byte", body);
    }

    public void removeTalkerStream(TSNDevice device, Header header){
        leave_talker(header, device.getHostMerge());
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
                .rank((short) 0)
                .mac(device.getNetCard().getMac().replace(":", "-"))
                .ipv4(device.getNetCard().getIp())
                .hostName(device.getHostMerge())
                .build();
        device.listenerHeader = header;
        join_listener(header, device.getHostMerge());
    }

    public void removeListenerServer(TSNDevice device){
        if (device.listenerHeader == null) return;
        leave_listener(device.listenerHeader, device.getHostMerge());
        device.listenerHeader = null;
    }

    private int join_talker(Header header, String hostName, String unit, long size){
        String url = this.talkerFront + hostName + "/stream-list/" + header.getKey();
//        System.out.println(url);
        RestfulPutInfo restfulPutInfo = RestfulPutInfo.builder()
                .url(url)
//                .isDebug(true)
                .build();

        JSONObject joinStream = header.getJSONObject(true, true,
                true, true, true,
                true, true);
        joinStream.put("packet-unit", unit);
        joinStream.put("packet-size", size);
        joinStream.put("create-time", System.currentTimeMillis());
        JSONArray streams = new JSONArray();
        streams.add(joinStream);
        JSONObject device = new JSONObject();
        device.put("stream-list", streams);
        System.out.println("--register talker to controller--");
        return restfulPutInfo.putInfo(device.toString());
    }

    public int leave_talker(Header header, String hostName){
        String url = this.talkerFront + hostName + "/stream-list/" + header.getKey();
        System.out.println("--remove talker from controller--");
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
