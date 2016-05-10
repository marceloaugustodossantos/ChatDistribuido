/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.unidadesdepersistencia;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class MainTeste {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static FileDataStoreFactory dataStoreFactory;
    private static Drive drive;
    private static final int ENTIDADE_MENSAGENS = 1;
    private static final int ENTIDADE_USUARIOS = 2;

    private static Credential authorize() {
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

    public static void main(String[] args) {

        try {

            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(new java.io.File(System.getProperty("user.home"), ".store/drive_sample"));
            Credential credential = authorize();
            System.out.println("credencial " + credential == null);
            drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName("chat-pod").build();
            System.out.println("dataStor " + dataStoreFactory == null);
            System.out.println("http.. " + httpTransport == null);

            File fileMetadata = new File();
            fileMetadata.setTitle("usuarios.txt");

            java.io.File fileMenssages = new java.io.File("src/main/resources/temp/usuarios.txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(fileMenssages, true), true));
            bf.write("");
            bf.close();

            FileContent mediaContent = new FileContent("text/txt", fileMenssages);

            System.out.println(drive == null);
            Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
            System.out.println(insert == null);

            MediaHttpUploader uploader = insert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(true);

             File f = insert.execute();
             String url = f.getDownloadUrl();
             String id  = f.getId();
            System.out.println("id " + id + "  url " + url);

            salvarPropriedadesDeAcesso(ENTIDADE_USUARIOS, id, url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    private static File uploadFile(boolean useDirectUpload) throws IOException {
//        File fileMetadata = new File();
//        fileMetadata.setTitle(UPLOAD_FILE.getName());
//
//        FileContent mediaContent = new FileContent("image/jpeg", UPLOAD_FILE);
//
//        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
//        MediaHttpUploader uploader = insert.getMediaHttpUploader();
//        uploader.setDirectUploadEnabled(useDirectUpload);
//        uploader.setProgressListener(new FileUploadProgressListener());
//        return insert.execute();
//    }
    private static void salvarPropriedadesDeAcesso(int tipoEntidade, String id, String url) {

        try {
            java.io.File arquivoNovo = new java.io.File("src/main/resources/repository/PropAcessUsers.txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(arquivoNovo, true), true));
            System.out.println(" br " + bf == null + id+"  "  +url);
            bf.write(id);
            bf.newLine();
            bf.write(url);
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
