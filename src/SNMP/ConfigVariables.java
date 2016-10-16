package SNMP;

import java.util.List;
import java.util.ArrayList;



public class ConfigVariables {
    
    private static String port = "/161";
    private static String partialAddress = "127.0.0.";
    private static int addressLowLimit = 1;
    private static int addressTopLimit = 1;
    private static String OID = ".1.3.6.1.2.1.1.1.0";
    private static final String community = "public";
    private static final String ip = "127.0.0.1";

    public static String getPort() {
        return port;
    }

    public static String getAddress() {
        return partialAddress;
    }

    public static int getAddressLowLimit() {
        return addressLowLimit;
    }

    public static int getAddressTopLimit() {
        return addressTopLimit;
    }

    public static String getOID() {
        return OID;
    }

    public static String getCommunity() {
        return community;
    }

    public static String getIp() {
        return ip;
    }
    
    public static List<String> getAllIps(){
        List<String> ipList = new ArrayList<>();       
        addIpValuesToList(ipList);
        return ipList;
    }
    
    private static void addIpValuesToList(List<String> ipList){
        for(int actual = addressLowLimit; actual <= addressTopLimit; actual++){
            ipList.add(returnIpWithPort(actual));
        }
    }
    
    private static String returnIpWithPort(int lastIpNumber){
        return partialAddress + lastIpNumber + port;
    }
    
    
    
}
