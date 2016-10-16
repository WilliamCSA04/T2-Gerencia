package SNMP;

import java.util.Timer;
import java.util.TimerTask;

public class Agendador {

    private String ip;
    private String comunidade;
    private String metrica;
    private String indice;
    private int tempo;
    private int count;

    public Agendador(String ip, String comunidade, String metrica, String indice, int tempo) {
        this.ip = ip;
        this.comunidade = comunidade;
        this.metrica = metrica;
        this.indice = indice;
        this.tempo = tempo;
        count = tempo;
    }

    public void agendamento() {
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            private Conexao conexao = new Conexao();

            int resultadoIn = 0;
            int resultadoOut = 0;

            @Override
            public void run() {
                executaGet();
            }

            private void executaGet() {
                if (metrica.equals("Taxa Kbytes")) {
                    //Taxa de Kbytes enviados e recebidos por segundo - indice 4 (transofrmar em selecionavel)
                    resultadoIn = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.2.2.1.10.4"));
                    resultadoOut = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.2.2.1.16.4"));
                    conexao.atualizaGrafico(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);

                } else if (metrica.equals("ICMP Echo Requests")) {
                    //ipacotes ICMP Echo Requests recebidos por segundo - nao testado
                    resultadoIn = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.5.8.0"));
                    conexao.atualizaGrafico(metrica, ((tempo - count) / 1000), resultadoIn * 100);

                } else if (metrica.equals("Segmentos TCP")) {
                    //taxa de segmentos TCP enviados e recebidos por segundo
                    resultadoIn = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.6.10.0"));
                    resultadoOut = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.6.11.0"));
                    conexao.atualizaGrafico(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);

                } else if (metrica.equals("Pacotes SNMP")) {
                    //quantidade de pacotes SNMP recebidos por segundo
                    resultadoIn = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.11.1.0"));
                    resultadoOut = Integer.parseInt(conexao.get(ip, comunidade, ".1.3.6.1.2.1.11.2.0"));
                    conexao.atualizaGrafico(metrica, ((tempo - count) / 1000), resultadoIn, ((tempo - count) / 1000), resultadoOut);
                }

                tempo += count;
            }
        }, 0, tempo);
    }

}
