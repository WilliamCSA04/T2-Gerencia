package SNMP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class Connection {

    private static Snmp snmp;

    private static void start() {
        TransportMapping tm = getDefaultTransportMapping();
        snmp = new Snmp(tm);
        try {
            tm.listen();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static TransportMapping getDefaultTransportMapping() {
        try {
            return new DefaultUdpTransportMapping();
        } catch (IOException ex) {
            return null;
        }
    }

    private static CommunityTarget getConfiguredCommunityTarget(String ipWithPort) {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(ConfigVariables.getCommunity()));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(ipWithPort));
        target.setRetries(2);
        target.setTimeout(1000);
        return target;
    }

    public static String getSystemDescription() {
        start();
        CommunityTarget target = getConfiguredCommunityTarget(ConfigVariables.getIp() + ConfigVariables.getPort());
        PDU pdu = createConfiguredPDU();
        return executeResponseEvent(pdu, target);
    }
    
    public static List<String> getAllSystemDescription() {
        start();
        List<String> ipList = ConfigVariables.getAllIpsWithPort();
        List<String> descriptions = new ArrayList<>();
        for (String ipWithPort : ipList) {
            CommunityTarget target = getConfiguredCommunityTarget(ipWithPort);
            PDU pdu = createConfiguredPDU();
            descriptions.add(executeResponseEvent(pdu, target));
        }   
        return descriptions;
    }

    private static PDU createConfiguredPDU() {
        PDU pdu = new PDU();
        VariableBinding vb = createVariableBindingWithOID();
        pdu.add(vb);
        pdu.setType(PDU.GET);
        pdu.setRequestID(new Integer32(1));
        return pdu;
    }

    private static VariableBinding createVariableBindingWithOID() {
        OID oid = createOID();
        return new VariableBinding(oid);
    }

    private static OID createOID() {
        return new OID(ConfigVariables.getOID());
    }

    private static String executeResponseEvent(PDU pdu, CommunityTarget target) {
        try {
            ResponseEvent response = snmp.get(pdu, target);
            if (response != null) {
                PDU pduResponse = response.getResponse();
                if (pduResponse != null) {
                    int errorStatus = pduResponse.getErrorStatus();
                    if (errorStatus == PDU.noError) {
                        Vector<? extends VariableBinding> vb = pduResponse.getVariableBindings();
                        String message = "";
                        for (VariableBinding variableBinding : vb) {
                            message += variableBinding.toValueString();
                        }
                        snmp.close();
                        return message;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

}
