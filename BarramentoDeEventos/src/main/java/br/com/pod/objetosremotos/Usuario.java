/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.objetosremotos;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

/**
 *
 * @author Marcelo Augusto
 */
public class Usuario extends RemoteObject {

    private long id;
    private String nome;
    private String email;
    private String ip;
    private int port;

    public Usuario(long id, String nome, String email, String ip, int port) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ip = ip;
        this.port = port;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
     
}
