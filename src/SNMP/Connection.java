package SNMP;

import static SNMP.Connection.getDefaultTransportMapping;
import java.io.IOException;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class Connection {

    private Snmp snmp;
    private static final String community = "public";
    private static final String ip = "127.0.0.1";

    public Connection() {
        this.snmp = new Snmp(getDefaultTransportMapping());
    }

    public static TransportMapping getDefaultTransportMapping() {
        try {
            return new DefaultUdpTransportMapping();
        } catch (IOException ex) {
            return null;
        }
    }

    public static CommunityTarget getConfiguredCommunityTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(ip + "/161"));
        target.setRetries(2);
        target.setTimeout(1000);
        return target;   
    }

}
