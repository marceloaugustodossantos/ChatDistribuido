/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.objetosremotos;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Date;

/**
 *
 * @author Marcelo Augusto
 */
public class Mensagem extends RemoteObject {

    private long id;
    private long idUsuario;
    private long idGrupo;
    private String mensagem;
    private Date data;

    public Mensagem(long id, long idUsuario, long idGrupo, String mensagem, Date data) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idGrupo = idGrupo;
        this.mensagem = mensagem;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
