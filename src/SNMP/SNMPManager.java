package SNMP;

import java.io.IOException;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;

import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;



public class SNMPManager {
    
    private final String port ="/161";
    private final String address = "127.0.0.";
    private final int addressLowLimit = 1;
    private final int addressTopLimit = 1;
    private final String OID = ".1.3.6.1.2.1.1.1.0";

}
