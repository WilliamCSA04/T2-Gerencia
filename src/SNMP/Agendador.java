package SNMP;

import java.util.Timer;
import java.util.TimerTask;

public class Agendador {

    private String ip;
    private String metrica;
    private String indice;
    private int tempo;
    private int count;

    public Agendador(String ip, String metrica, String indice, int tempo) {
        this.ip = ip;
        this.metrica = metrica;
        this.indice = indice;
        this.tempo = tempo;
        count = tempo;
    }

    public void agendamento() {
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            private Connection connection = new Connection();

            int resultadoIn = 0;
            int resultadoOut = 0;

            @Override
            public void run() {
                executaGet();
            }

            private void executaGet() {
                if (metrica.equals("Taxa Kbytes")) {
                    //Taxa de Kbytes enviados e recebidos por segundo - indice 4 (transofrmar em selecionavel)
                    resultadoIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.2.2.1.10." + indice));
                    resultadoOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.2.2.1.16." + indice));
                    connection.updateChart(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);

                } else if (metrica.equals("ICMP Echo Requests")) {
                    //ipacotes ICMP Echo Requests recebidos por segundo - nao testado
                    resultadoIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.5.8." + indice));
                    connection.updateChart(metrica, ((tempo - count) / 1000), resultadoIn * 100);

                } else if (metrica.equals("Segmentos TCP")) {
                    //taxa de segmentos TCP enviados e recebidos por segundo
                    resultadoIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.6.10." + indice));
                    resultadoOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.6.11." + indice));
                    connection.updateChart(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);

                } else if (metrica.equals("Pacotes SNMP")) {
                    //quantidade de pacotes SNMP recebidos por segundo
                    resultadoIn = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.11.1." + indice));
                    resultadoOut = Integer.parseInt(Connection.get(ip, ".1.3.6.1.2.1.11.2." + indice));
                    connection.updateChart(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);
                }

                tempo += count;
            }
        }, 0, tempo);
    }

}
