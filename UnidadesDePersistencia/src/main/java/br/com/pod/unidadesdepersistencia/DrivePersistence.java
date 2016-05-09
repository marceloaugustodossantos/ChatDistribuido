/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import br.com.pod.interfacesremotas.Persistence;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class DrivePersistence implements Persistence {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static FileDataStoreFactory dataStoreFactory;
    private Drive drive;
    private final int ENTIDADE_MENSAGENS = 1;
    private final int ENTIDADE_USUARIOS = 2;

    public DrivePersistence(){
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(new java.io.File(System.getProperty("user.home"), ".store/drive_sample"));
            Credential credential = authorize();
            drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName("chat-pod").build();
        } catch (Exception ex) {
            Logger.getLogger(DrivePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //recebe uma lista com todas as mensagens no formato json
    public String buscarMensagens() throws RemoteException {
        String result = "";
        try {
            java.io.File parentDir = new java.io.File("src/main/resources/temp/");
            File fileMetadata = new File();
            fileMetadata.setTitle("teste2.txt");
            OutputStream out = new FileOutputStream(new java.io.File(parentDir, fileMetadata.getTitle()));

            MediaHttpDownloader downloader
                    = new MediaHttpDownloader(httpTransport, drive.getRequestFactory().getInitializer());
            downloader.setDirectDownloadEnabled(true);
            downloader.download(new GenericUrl(recuperarPropriedadesDeAcessoMensagens(ENTIDADE_MENSAGENS)[1]), out);

            java.io.File arquivo = new java.io.File("src/main/resources/temp/mensagens.txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            result = br.readLine();
            br.close();
            arquivo.delete();
        } catch (IOException ex) {
            Logger.getLogger(DrivePersistence.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return result;
    }

    //recebe uma lista com todas as mensagens no formato json
    public void salvarMensgens(String mensagensJson) throws RemoteException {
        try {
            deleteFile(mensagensJson); //excluir mensagens antigas

            File fileMetadata = new File();
            fileMetadata.setTitle("mensagens.txt");

            java.io.File fileMenssages = new java.io.File("/src/main/resources/temp/mensagens.txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(fileMenssages, true), true));
            bf.write(mensagensJson);
            bf.close();

            FileContent mediaContent = new FileContent("text/txt", fileMenssages);
            fileMenssages.delete();

            Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
            MediaHttpUploader uploader = insert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(true);

            String url = insert.execute().getDownloadUrl();
            String id = fileMetadata.getId();
            salvarPropriedadesDeAcessoMensagens(ENTIDADE_MENSAGENS, id, url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteFile(String id) {
        try {
            Drive.Files.Delete delete = drive.files().delete(id);
            delete.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //retorna uma lista com todos os usuarios no formato json
    public String buscarUsuarios(String url) throws RemoteException{
        String result = "";
        try {
            java.io.File parentDir = new java.io.File("src/main/resources/temp/");
            File fileMetadata = new File();
            fileMetadata.setTitle("teste2.txt");
            OutputStream out = new FileOutputStream(new java.io.File(parentDir, fileMetadata.getTitle()));

            MediaHttpDownloader downloader
                    = new MediaHttpDownloader(httpTransport, drive.getRequestFactory().getInitializer());
            downloader.setDirectDownloadEnabled(true);
            downloader.download(new GenericUrl(url), out);

            java.io.File arquivo = new java.io.File("src/main/resources/temp/mensagens.txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            result = br.readLine();
            br.close();
            arquivo.delete();
        } catch (IOException ex) {
            Logger.getLogger(DrivePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //recebe uma lista com todos os usuarios no formato json
    public String salvarUsuarios(String usuariosJson) throws RemoteException {
        try {
             deleteFile(usuariosJson); //excluir mensagens antigas

            File fileMetadata = new File();
            fileMetadata.setTitle("mensagens.txt");

            java.io.File fileMenssages = new java.io.File("/src/main/resources/temp/mensagens.txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(fileMenssages, true), true));
            bf.write(usuariosJson);
            bf.close();

            FileContent mediaContent = new FileContent("text/txt", fileMenssages);
            fileMenssages.delete();

            Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
            MediaHttpUploader uploader = insert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(true);

            String url = insert.execute().getDownloadUrl();
            String id = fileMetadata.getId();
            salvarPropriedadesDeAcessoMensagens(ENTIDADE_USUARIOS, id, url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Credential authorize() {
        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(DrivePersistence.class.getResourceAsStream("/client_secrets.json")));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory)
                    .build();
            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        } catch (IOException ex) {
            Logger.getLogger(DrivePersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void salvarPropriedadesDeAcessoMensagens(int tipoEntidade, String id, String url) {
        String path;
        if (tipoEntidade == ENTIDADE_MENSAGENS) {
            path = "src/main/resources/tokens/PropAcessMsgs.txt";
        } else {
            path = "src/main/resources/tokens/PropAcessUsers.txt";
        }
        try {
            java.io.File arquivoAntigo = new java.io.File(path);
            arquivoAntigo.delete();
            java.io.File arquivoNovo = new java.io.File(path);
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(arquivoNovo, true), true));
            bf.write(id);
            bf.newLine();
            bf.write(url);
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] recuperarPropriedadesDeAcessoMensagens(int tipoEntidade) {
        String path;
        if (tipoEntidade == ENTIDADE_MENSAGENS) {
            path = "src/main/resources/tokens/PropAcessMsgs.txt";
        } else {
            path = "src/main/resources/tokens/PropAcessUsers.txt";
        }
        try {
            java.io.File arquivo = new java.io.File(path);
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String[] result = new String[2];
            result[0] = br.readLine();
            result[1] = br.readLine();
            br.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void salvarNotificacao(String token, String notificacao) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarNotificacoesDeUsuario(String token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscarGrupos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
