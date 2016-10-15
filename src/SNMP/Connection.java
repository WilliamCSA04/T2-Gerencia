package SNMP;

import static SNMP.Connection.getDefaultTransportMapping;
import java.io.IOException;
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
    private static SNMPManager snmpManager;

    private static void start() {
        SNMPManager snmpManager = new SNMPManager();
        TransportMapping tm = getDefaultTransportMapping();
        snmp = new Snmp(getDefaultTransportMapping());
        try {
            tm.listen();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        target.setCommunity(new OctetString(snmpManager.getCommunity()));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(snmpManager.getIp() + snmpManager.getPort()));
        target.setRetries(2);
        target.setTimeout(1000);
        return target;
    }

    public static String get() {
        start();
        CommunityTarget target = getConfiguredCommunityTarget();
        PDU pdu = createConfiguredPDU();
        return executeResponseEvent(pdu, target);
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
        return new OID(snmpManager.getOID());
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

    public static String getSystemDescription() {
        start();
        return getAsString();
    }

    private static String getAsString() {
        try {
            ResponseEvent event = getResponseEvent();
            if (event != null) {
                PDU responsePDU = event.getRequest();
                if (responsePDU != null) {
                    return responsePDU.get(0).getVariable().toString();
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

    private static ResponseEvent getResponseEvent() throws IOException {
        ResponseEvent event = snmp.send(createConfiguredPDU(), getConfiguredCommunityTarget(), null);
        if (event != null) {
            return event;
        }
        return null;
    }

}
