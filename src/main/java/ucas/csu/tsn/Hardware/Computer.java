package ucas.csu.tsn.Hardware;

import ucas.csu.tsn.Facility.NetworkCard;
import ucas.csu.tsn.Facility.TSNDevice;
import ucas.csu.tsn.Facility.Port;
import ucas.csu.tsn.Facility.TSNSwitch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wpy
 * @description: TODO
 * @date : 5/1/22 8:28 AM
 */
public class Computer {
    public static final String topology_id = "tsn-simulator-network";
    public static Map<String, Port> portMap = new HashMap<>();
    public static Map<String, NetworkCard> networkCardMap = new HashMap<>();
    public static Map<String, TSNDevice> deviceMap = new HashMap<>();
    public static Map<String, TSNSwitch> switchMap = new HashMap<>();
    public static long firstSeen = System.currentTimeMillis();
    public static String cuc_ip = "localhost";
    public static int ip_id = 1;

    public Map<String, String> urls;
    public Computer(){
        urls = new HashMap<>();
        urls.put("tsn-talker", "http://" + cuc_ip +
                ":8181/restconf/config/tsn-talker-type:stream-talker-config/devices/");
        urls.put("tsn-topology", "http://" + cuc_ip +
                ":8181/restconf/config/network-topology:network-topology/");
        urls.put("tsn-listener", "http://" + cuc_ip +
                ":8181/restconf/config/tsn-listener-type:stream-listener-config/devices/");
    }

    public static synchronized int allocateIp(){
        int result = ip_id;
        ip_id++;
        return result;
    }
}
