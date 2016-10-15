package SNMP;



public class SNMPManager {
    
    private String port;
    private String address;
    private int addressLowLimit;
    private int addressTopLimit;
    private String OID;
    private final String community = "public";
    private final String ip = "127.0.0.1";

    public String getCommunity() {
        return community;
    }

    public String getIp() {
        return ip;
    }
    
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

    public String getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public int getAddressLowLimit() {
        return addressLowLimit;
    }

    public int getAddressTopLimit() {
        return addressTopLimit;
    }

    public String getOID() {
        return OID;
    }
    
    
    
}
