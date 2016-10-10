# T2 - Gerencia de Redes

O  trabalho  consiste  em implementar  um sistema  de  gerência  usando o  protocolo SNMP. 
Inicialmente  o  sistema  deve  fazer  uma  pesquisa  na  rede  local  a  procura de máquinas que sejam agentes SNMP. O sistema deverá apresentar o endereço IP de todos os agentes encontrados, juntamente com sua descrição.
A partir da descoberta dos agentes, o usuário poderá escolhero agente que deseja monitorar. Selecionado o agente, será necessário:

- apresentar  graficamente (usando  gráfico  de  linhas) um  subconjunto  das  métricas capturadas periodicamente:
a) taxa de Kbytes enviados e recebidos por segundo
b) pacotes ICMP Echo Requests recebidos por segundo
c) taxa de segmentos TCP enviados e recebidos por segundo
d) quantidade de pacotes SNMP recebidos por segundo
- permitir  a  definição  de  limites  inferior  e  superior  de  utilização  para  as  métricas apresentadas graficamente e gerar alarmes quando algum limite for ultrapassado;
- Continuar funcionando normalmente apesar da ocorrência de um alarme.
- permitir a alteração dos limites inferior e superior durante a sua execução.
- gerar um alarme quando a máquina não puder mais ser consultada (o sistema não deve travar), e quando a máquina ficar ativa novamente, o sistema deve continuar a monitoração.

Além disso, a  visualização de objetos contadores deve o correr com a diminuição do  valor  atual  pelo  amostrado  anteriormente  e o  sistema  deve  ser implementado para mostrar  valores  corretos  mesmo  que  os  contadores  tenham  sido  zerados  por  terem alcançado seu limite máximo.
Antes de  selecionar  o  agente  que  deseja monitorar,  as  seguintes  configurações devem ser realizadas (usando uma interface gráfica): definição do tempo de  periodicidade utilizado para capturar e atualizar as informações nos gráficos e limites inferior e superior para ca da um dos gráficos.
