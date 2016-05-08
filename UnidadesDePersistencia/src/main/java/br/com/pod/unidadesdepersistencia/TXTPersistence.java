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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class TXTPersistence implements Persistence {

    @Override
    public void salvarMensgem(String token, String mensagem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
            arquivo.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void salvarUsuario(String string, String convertUsuarioToJson) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvarGrupo(String string, String convertGrupoToJson) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarGrupos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarGrupo(String idGrupo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizarGrupo(String string, String convertGrupoToJson) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarUsuario(String string) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Long, Usuario> listarUsuarios() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
