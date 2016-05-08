package br.com.pod.socket;

import br.com.pod.clientmanager.ClientManagerOperations;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 *
 * @author Pris
 */
public class ClientManagerSocket {

    public String teste;
    static ClientManagerOperations operations;

    public static void main(String[] args) throws IOException, RemoteException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(10998);
        System.out.println("Servidor disponível...");
        // execução infinita
        while (true) {
            System.out.println("Servidor aguardando conexões...");
            Socket client = serverSocket.accept(); // aguarda o cliente conectar - sistema bloqueado

            byte[] b = new byte[1024];
            client.getInputStream().read(b);

            String protocolo = new String(b).trim();

            dispatcher(protocolo, client);

            // byte[] b = new byte[1024];
            // client.getInputStream().read(b);
            // String msg = "Priscila:Gabriel:Gouveia";
            // client.getOutputStream().write(msg.getBytes());
            // client.getOutputStream().flush();
            // client.shutdownOutput();
            // client.close();
            //
            //// String xmessage = new String(b).trim();
            // System.out.println("Mensagem recebida: " + msg);
        }
    }

//<signin>:<credential>:<name>
//<login>:<credential>
//<logout>:<credential>
//<getmessages>:
//<publish>:<idUser>:<idGroup>:<message>
//<subscribe>:<idUser>:<idGroup>
//<groupsavailable>
//<groupsregistered>:<credential>
    static void dispatcher(String protocolo, Socket client) throws RemoteException, IOException{
        String[] action = protocolo.split(":");
        String response = "";
        switch (action[0]) {
            case "signin": {
                long id = operations.signin(action[1], action[2], client.getLocalAddress().toString(), client.getPort());
                response = String.valueOf(id);
                sendToClient(response, client);
                break;//retorno
            }
            case "login":
                operations.login(action[1]);
                break;
            case "logout":
                operations.logout(action[1]);
                break;
            case "getmessages":{
                response = operations.getMessages();
                sendToClient(response, client);
                break;//retorno
            }
            case "publish":
                operations.publish(Long.parseLong(action[1]), Long.parseLong(action[2]), action[3]);
                break;
            case "subscribe":
                operations.subscribe(Long.parseLong(action[1]),Long.parseLong(action[2]));
                break;
            case "groupsavailable":
                response = operations.getAvailableGroups();
                sendToClient(response, client);
                break;//retorno
            case "groupsregistered":
                response = operations.getRegisteredGroups(Long.parseLong(action[1]));
                sendToClient(response, client);
                break;//retorno
        }
    }

    static void sendToClient(String response, Socket client) throws IOException {
        client.getOutputStream().write(response.getBytes());
        client.getOutputStream().flush();
        client.shutdownOutput();
        client.close();
    }

}
