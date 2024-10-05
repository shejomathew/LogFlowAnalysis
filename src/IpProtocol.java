import java.util.HashMap;
import java.util.Map;

public class IpProtocol {

    private static final Map<Integer, String> protocolMap = new HashMap<>();

    static {
        // Populate the protocol map
        protocolMap.put(1, "ICMP");
        protocolMap.put(2, "IGMP");
        protocolMap.put(6, "TCP");
        protocolMap.put(17, "UDP");
        protocolMap.put(132, "SCTP");
        protocolMap.put(50, "ESP");
        protocolMap.put(51, "AH");
        protocolMap.put(58, "ICMPv6");
        protocolMap.put(89, "OSPF");
        protocolMap.put(47, "GRE");
        // Add more protocols as needed


    }
    public static String getProtocolName(int number) {
        return protocolMap.get(number);
    }

}
