package SNMP;

import Graphics.Graphic;
import Graphics.Point;
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
    private ArrayList<Point> pointList = new ArrayList<>();
    private Graphic chart = new Graphic();
    private int beforeIn = -1;
    private int beforeOut = -1;

    private int actualIn = -1;
    private int actualOut = -1;

    private float beforeY;
    private float actualY;

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
        target.setRetries(0);
        target.setTimeout(100);
        return target;
    }

    public static String getSystemDescription() {
        start();
        CommunityTarget target = getConfiguredCommunityTarget(ConfigVariables.getIp() + ConfigVariables.getPort());
        PDU pdu = createConfiguredPDU();
        return executeResponseEvent(pdu, target);
    }

    public static String get(String ip, String OID) {
        start();
        CommunityTarget target = getConfiguredCommunityTarget(ip + ConfigVariables.getPort());
        PDU pdu = createConfiguredPDU(OID);
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
            System.out.println("Executando...");
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

    private static PDU createConfiguredPDU(String OID) {
        PDU pdu = new PDU();
        VariableBinding vb = createVariableBindingWithOID(OID);
        pdu.add(vb);
        pdu.setType(PDU.GET);
        pdu.setRequestID(new Integer32(1));
        return pdu;
    }

    private static VariableBinding createVariableBindingWithOID() {
        OID oid = createOID();
        return new VariableBinding(oid);
    }

    private static VariableBinding createVariableBindingWithOID(String OID) {
        OID oid = createOID(OID);
        return new VariableBinding(oid);
    }

    private static OID createOID() {
        return new OID(ConfigVariables.getOID());
    }

    private static OID createOID(String OID) {
        return new OID(OID);
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

    public void updateChart(String titulo, int inX, int inY, int outX, int outY) {
        if (beforeIn != -1 && beforeOut != -1) {
            actualIn = inY;
            actualOut = outY;

            pointList.add(new Point(inX, (actualIn - beforeIn), outX, (actualOut - beforeOut)));

            beforeIn = actualIn;
            beforeOut = actualOut;
        } else {
            beforeIn = inY;
            beforeOut = outY;
        }

        chart.criaGrafico("Gráfico", titulo, pointList);
    }

    public void updateChart(String titulo, int x, float y) {
        if (beforeY != -1) {
            actualY = (float) y;

            pointList.add(new Point(x, (actualY - beforeY)));

            beforeY = (float) actualY;
        } else {
            beforeY = (float) y;
        }

        chart.criaGrafico2("Gráfico", titulo, pointList);
    }

    public void chamaAgendador(String ip, String metrica, String indice, int tempo) {
        //chamaGet(ip, comunidade, metrica, indice, tempo);
        Agendador agendador = new Agendador(ip, metrica, indice, tempo);
        agendador.agendamento();
    }

}
