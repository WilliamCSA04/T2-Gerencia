package SNMP;

import java.util.Timer;
import java.util.TimerTask;

public class Schedule {

    private String ip;
    private String metric;
    private String index;
    private int time;
    private int count;

    public Schedule(String ip, String metric, String index, int time) {
        this.ip = ip;
        this.metric = metric;
        this.index = index;
        this.time = time;
        count = time;
    }

    public void scheduler() {
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            private Connection connection = new Connection();

            int resultIn = 0;
            int resultOut = 0;

            @Override
            public void run() {
                searchOnMib();
            }

            private void searchOnMib() {
                if (metric.equals("Taxa Kbytes")) {
                    //Taxa de Kbytes enviados e recebidos por segundo - indice 4 (transofrmar em selecionavel)
                    resultIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.2.2.1.10." + index));
                    resultOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.2.2.1.16." + index));
                    connection.updateChart(metric, ((time - count) / 1000), resultIn, ((time - count) / 1000), resultOut);

                } else if (metric.equals("ICMP Echo Requests")) {
                    //ipacotes ICMP Echo Requests recebidos por segundo - nao testado
                    resultIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.5.8." + index));
                    connection.updateChart(metric, ((time - count) / 1000), resultIn * 100);

                } else if (metric.equals("Segmentos TCP")) {
                    //taxa de segmentos TCP enviados e recebidos por segundo
                    resultIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.6.10." + index));
                    resultOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.6.11." + index));
                    connection.updateChart(metric, ((time - count) / 1000), resultIn, ((time - count) / 1000), resultOut);

                } else if (metric.equals("Pacotes SNMP")) {
                    //quantidade de pacotes SNMP recebidos por segundo
                    resultIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.11.1." + index));
                    resultOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.11.2." + index));
                    connection.updateChart(metric, ((time - count) / 1000), resultIn, ((time - count) / 1000), resultOut);
                }

                time += count;
            }
        }, 0, time);
    }

}
