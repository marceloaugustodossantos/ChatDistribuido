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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo Augusto
 */
public class TXTPersistence implements Persistence{

    public void salvarMensgem(String token, String mensagem) {
        try {
            File arquivo = new File("src/main/resources/tokens/" + token + "txt");
            BufferedWriter bf = new BufferedWriter(new PrintWriter(new FileWriter(arquivo, true), true));
            bf.write(mensagem);
            bf.newLine();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMensagem(String token, int indice) {
        try {
            File arquivo = new File("src/main/resources/tokens/" + token + "txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            for (int k = 0; k < indice; k++) {
                br.readLine();
            }
            return br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getMensagens(String token) {
        try {
            File arquivo = new File("src/main/resources/tokens/" + token + "txt");
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            char[] buffer = new char[1024];
            br.read(buffer);
            return new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
