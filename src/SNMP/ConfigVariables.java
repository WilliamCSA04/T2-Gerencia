package SNMP;

import java.util.List;
import java.util.ArrayList;



public class ConfigVariables {
    
    private static final String port = "/161";
    private static String partialAddress = "127.0.0.";
    private static int addressLowLimit = 1;
    private static int addressTopLimit = 1;
    private static String OID = ".1.3.6.1.2.1.1.1.0";
    private static final String community = "public";
    private static String ip = "127.0.0.1";

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
    
    public static List<String> getAllIpsWithPort(){
        List<String> ipList = new ArrayList<>();       
        addIpValuesToListWithPort(ipList);
        return ipList;
    }
    
    public static void setIp(String ip) {
        ConfigVariables.ip = ip;
    }

    public static void setPartialAddress(String partialAddress) {
        ConfigVariables.partialAddress = partialAddress;
    }

    public static void setAddressLowLimit(int addressLowLimit) {
        ConfigVariables.addressLowLimit = addressLowLimit;
    }

    public static void setAddressTopLimit(int addressTopLimit) {
        ConfigVariables.addressTopLimit = addressTopLimit;
    }
    
    private static void addIpValuesToListWithPort(List<String> ipList){
        for(int actual = addressLowLimit; actual <= addressTopLimit; actual++){
            ipList.add(returnIpWithPort(actual));
        }
    }
    
    private static String returnIpWithPort(int lastIpNumber){
        return partialAddress + lastIpNumber + port;
    }
    
    
    
}
