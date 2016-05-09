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
import java.rmi.RemoteException;
/**
 *
 * @author Marcelo Augusto
 */
public class TXTPersistence implements Persistence {

    @Override
    public void salvarNotificacao(String token, String notificacao) throws RemoteException {
        try {
            File arquivo = new File("src/main/resources/tokens/" + token + ".txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(arquivo, true), true));
            bf.write(notificacao);
            bf.newLine();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String buscarNotificacoesDeUsuario(String token) throws RemoteException {        
        String result = null;
        try {
            File arquivo = new File("src/main/resources/tokens/" + token + ".txt");
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
            File arquivo = new File("src/main/resources/tokens/grupos.txt");
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

    @Override
    public String buscarMensagens() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvarMensgens(String mensagensJson) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarUsuarios(String url) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String salvarUsuarios(String usuariosJson) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  

}
