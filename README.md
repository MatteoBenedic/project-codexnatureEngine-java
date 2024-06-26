# Progetto di ingegneria del software A.A. 2023-2024 - gruppo AM12
## Componenti del gruppo
- Angaroni Giulio
- Benedic Matteo
- Brunati Ilaria
- Caruso Emanuele

## Specifica
Il progetto consiste nello sviluppo di una versione software del gioco da tavolo Codex Naturalis.

## Funzionalità base
| Feature        | Status        |
| -------------  | ------------- |
|     UML        |     ✔️        |
|    Model       |     ✔️        |
|  Controller    |     ✔️        |
|  Networking    |     ✔️        |
|    View        |     ✔️        |
|    TUI         |     ✔️        |
|    GUI         |     ✔️        |
| Disconnessione |     ✔️        |
|   Deployment   |     ✔️        |



## Funzionalità avanzate
| Feature              | Status         |
| -------------------- | -------------  |
|   Multiple games     |     ✔️        |
|    Chat              |     ✔️        |
|Connection resilience |     ❌        |

## Tabella voto
| Requisiti                                         | Voto massimo   | Realizzazione   |
| ------------------------------------------------- | -------------  | ----------------|
|Regole semplificate + TUI + RMI o Socket 	        |     18	       |    ✔️          |
|Regole complete + TUI + RMI o Socket               |     20	       |    ✔️          |
|Regole complete + TUI + GUI + RMI o Socket + 1FA	  |     24	       |    ✔️          |
|Regole complete + TUI + GUI + RMI + Socket + 1FA   |	    27	       |    ✔️          |
|Regole complete + TUI + GUI + RMI + Socket + 2FA   |     30	       |    ✔️          |
|Regole complete + TUI + GUI + RMI + Socket + 3FA   |     30L	       |    ❌          |


## Istruzioni per l'esecuzione
Server: `java -jar AM12.jar server [PORT_SOCKET] [PORT_RMI] [SERVER_IP_ADDRESS]`<br />
Client (Socket, CLI): `java -jar AM12.jar client [SERVER_IP_ADDRESS] [PORT_SOCKET] cli socket`<br />
Client (Socket, GUI): `java -jar AM12.jar client [SERVER_IP_ADDRESS] [PORT_SOCKET] gui socket`<br />
Client (RMI, CLI): `java -jar AM12.jar client [SERVER_IP_ADDRESS] [PORT_RMI] cli rmi`<br />
Client (RMI, GUI): `java -jar AM12.jar client [SERVER_IP_ADDRESS] [PORT_RMI] gui rmi`
