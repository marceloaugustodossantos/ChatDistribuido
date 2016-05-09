package br.com.pod.clientmanager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pod.objetosremotos.Grupo;

public class TesteServer {

    public String teste;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(10997);
        System.out.println("Servidor disponível...");
        // execução infinita
        while (true) {
            System.out.println("Servidor aguardando conexões...");
            Socket client = serverSocket.accept(); // aguarda o cliente conectar - sistema bloqueado

            String msg = "Priscila:Gabriel-Gouveia:Ednamar-Mylla:Cachorro-";
//            Grupo grupu = new Grupo(System.currentTimeMillis(), "lala");
//            Grupo grupu2 = new Grupo(System.currentTimeMillis(), "lala2");
//            Grupo grupu3 = new Grupo(System.currentTimeMillis(), "lala3");
//
//            List<Grupo> grupos = new ArrayList<>();
//            grupos.add(grupu);
//            grupos.add(grupu2);
//            grupos.add(grupu3);
//
//            String result = "";
//            for (Grupo g : grupos) {
//                result += g.getId() + ":" + g.getNome() + "-";
//            }
            System.out.println(msg);
            client.getOutputStream().write(msg.getBytes());
            client.getOutputStream().flush();
            client.shutdownOutput();
            client.close();
        }
    }
}
