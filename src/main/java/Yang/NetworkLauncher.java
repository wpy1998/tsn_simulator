package Yang;

import Facility.Port;
import Facility.TSNDevice;
import Facility.TSNSwitch;
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
public class NetworkLauncher {
    private String topologyId, urlFront;
    @Builder
    public NetworkLauncher(@NonNull String topologyId, @NonNull String urlFront){
        this.topologyId = topologyId;
        this.urlFront = urlFront;
    }

    public void registerDevice(TSNDevice tsnDevice){
        String url = this.urlFront + "topology/" + topologyId;
        System.out.println("--register node to controller--");
        String url1 = url + "/node/" + tsnDevice.getMac().replace(":", "-");
        JSONObject node = tsnDevice.getNodeJSONObject();
        RestfulPutInfo restfulPutInfo1 = RestfulPutInfo.builder()
                .url(url1)
//                .isDebug(true)
                .build();
        JSONArray array1 = new JSONArray();
        array1.add(node);
        JSONObject object1 = new JSONObject();
        object1.put("node", node);
        restfulPutInfo1.putInfo(object1.toString());
    }

    public void registerSwitch(TSNSwitch tsnSwitch){
        String url = this.urlFront + "topology/" + topologyId;

        System.out.println("--register node to controller--");
        String url1 = url + "/node/" + tsnSwitch.getLan().getMac().replace(":", "-");
        JSONObject node = tsnSwitch.getNodeJSONObject();
        RestfulPutInfo restfulPutInfo1 = RestfulPutInfo.builder()
                .url(url1)
//                .isDebug(true)
                .build();
        JSONArray array1 = new JSONArray();
        array1.add(node);
        JSONObject object1 = new JSONObject();
        object1.put("node", node);
        restfulPutInfo1.putInfo(object1.toString());

        for (Port port : tsnSwitch.getLan().getPorts()){
            System.out.println("--register link to controller--");
            String url2 = url + "/link/" + port.getLinkId();
            JSONObject link = port.getLinkJSONObject();
            RestfulPutInfo restfulPutInfo2 = RestfulPutInfo.builder().url(url2).build();
            JSONArray array2 = new JSONArray();
            array2.add(link);
            JSONObject object2 = new JSONObject();
            object2.put("link", link);
            restfulPutInfo2.putInfo(object2.toString());
        }
    }

    public void removeDevice(TSNDevice tsnDevice){
        String url = this.urlFront + "topology/" + topologyId + "/node/" +
                tsnDevice.getMac().replace(":", "-");
        System.out.println("--remove node from controller--");
        RestfulDeleteInfo restfulDeleteInfo = RestfulDeleteInfo.builder()
                .url(url)
                .build();
        restfulDeleteInfo.deleteInfo();

//        String url1 = this.urlFront + "topology/" + topologyId + "/link/"
//                + tsnDevice.getNetCard().getLinkId();
//        System.out.println("--remove link from controller--");
//        RestfulDeleteInfo restfulDeleteInfo1 = RestfulDeleteInfo.builder()
//                .url(url1).build();
//        restfulDeleteInfo1.deleteInfo();

    }

    public void removeSwitch(TSNSwitch tsnSwitch){
        String url = this.urlFront + "topology/" + topologyId + "/node/" +
                tsnSwitch.getLan().getMac().replace(":", "-");
        System.out.println("--remove node from controller--");
        RestfulDeleteInfo restfulDeleteInfo = RestfulDeleteInfo.builder().url(url).build();
        restfulDeleteInfo.deleteInfo();

        for (Port port : tsnSwitch.getLan().getPorts()){
            String url1 = this.urlFront + "topology/" + topologyId + "/link/"
                    + port.getLinkId();
            System.out.println("--remove link from controller--");
            RestfulDeleteInfo restfulDeleteInfo1 = RestfulDeleteInfo.builder()
                    .url(url1).build();
            restfulDeleteInfo1.deleteInfo();
        }
    }
}
