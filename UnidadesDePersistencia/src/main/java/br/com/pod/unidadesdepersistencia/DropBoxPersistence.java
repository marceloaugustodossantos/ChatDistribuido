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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class DropBoxPersistence implements Persistence {

    private String acessToken = "Ylhv3F2UgGAAAAAAAAAAB5-2R6H_9ePNXfD54g8dny42v7H7JdyYGSTaB-h22kOW";

    @Override
    public void salvarMensgem(String token, String mensagem) {
        try {
            DbxRequestConfig config = new DbxRequestConfig("d-academico", null);
            DbxClient client = new DbxClient(config, acessToken);
            System.out.println(client);
            client.createFolder("/teste");
            File file = new File("/home/douglas/Área de Trabalho/Apresentação TCC.pdf");
            InputStream is = new FileInputStream(file);
            client.uploadFile("/teste/outroteste/" + file.getName(), DbxWriteMode.add(), is.available(), is);
            System.out.println(client.createShareableUrl("/teste/outroteste/Apresentação TCC.pdf"));
        } catch (FileNotFoundException | DbxException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DropBoxPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getMensagem(String token, int indice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMensagens(String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
