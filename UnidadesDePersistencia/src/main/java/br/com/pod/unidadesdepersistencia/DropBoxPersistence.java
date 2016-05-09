/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
public class DropBoxPersistence implements Persistence {

    private final String ACESS_TOKEN = "Ylhv3F2UgGAAAAAAAAAAB5-2R6H_9ePNXfD54g8dny42v7H7JdyYGSTaB-h22kOW";

    @Override
    public void salvarNotificacao(String token, String notificacao) throws RemoteException {
        try {
            DbxRequestConfig config = new DbxRequestConfig("chat-pod", null);
            DbxClient client = new DbxClient(config, ACESS_TOKEN);
            
            File file = new File("/src/main/resources/tokens/"+token+".txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(file, true), true));
            bf.write(notificacao);
            InputStream is = new FileInputStream(file);
            
            client.uploadFile("/Notificacoes/" + file.getName(), DbxWriteMode.add(), is.available(), is);
            
            bf.close();
            is.close();
            file.delete();
        } catch (FileNotFoundException | DbxException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DropBoxPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String buscarNotificacoesDeUsuario(String token) throws RemoteException {
        String result = "";
        try {
            DbxRequestConfig config = new DbxRequestConfig("chat-pod", null);
            DbxClient client = new DbxClient(config, ACESS_TOKEN);
            FileOutputStream outputStream = new FileOutputStream("src/main/resources/temp/"+token+".txt");
            client.getFile("/Notificacoes/"+token+".txt", null, outputStream);
            client.delete ("/Notificacoes/"+token+".txt");
            File arquivo = new File("src/main/resources/temp/"+token+".txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            result = br.readLine();
            outputStream.close();
            br.close();
            arquivo.delete();
        } catch (FileNotFoundException | DbxException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DropBoxPersistence.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public String buscarGrupos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
