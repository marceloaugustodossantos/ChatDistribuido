/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Marcelo Augusto
 */
public class TXTPersistence extends UnicastRemoteObject implements Persistence {

    public TXTPersistence() throws RemoteException {
        super();
    }

    @Override
    public void salvarNotificacoes(String notificacoes) throws RemoteException {
        try {
            File arquivo = new File("src/main/resources/repository/notificacoes.txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(arquivo, true), true));
            bf.write(notificacoes);
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String buscarNotificacoes() throws RemoteException {
        String result = null;
        try {
            File arquivo = new File("src/main/resources/repository/notificacoes.txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            result = br.readLine();
            br.close();
            arquivo.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public String buscarGrupos() throws RemoteException {
        String result = null;
        try {
            File arquivo = new File("src/main/resources/repository/grupos.txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            result = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void atualizarGrupos(String gruposGson) throws RemoteException {
        try {
            File gruposAntigos = new File("src/main/resources/repository/grupos.txt");
            gruposAntigos.delete();
            File gruposAtualizados = new File("src/main/resources/repository/grupos.txt");
            BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(gruposAtualizados, true), true));
            bw.write(gruposGson);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String buscarMensagens() throws RemoteException {
        return "";
    }

    @Override
    public void salvarMensgens(String mensagensJson) throws RemoteException {
    }

    @Override
    public String buscarUsuarios() throws RemoteException {
        return "";
    }

    @Override
    public String salvarUsuarios(String usuariosJson) throws RemoteException {
        return "";
    }

}
