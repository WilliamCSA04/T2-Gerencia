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
    
    private String port;
    private String address;
    private int addressLowLimit;
    private int addressTopLimit;
    private String OID;
    
    public SNMPManager(String port, String address, int addressLowLimit, int addressTopLimit, String OID){
        this.port=port;
        this.address = address;
        this.addressLowLimit = addressLowLimit;
        this.addressTopLimit = addressTopLimit;
        this.OID = OID;
    }
    
    public SNMPManager(){
        this("/161", "127.0.0.", 1, 1, ".1.3.6.1.2.1.1.1.0");
    }

}
