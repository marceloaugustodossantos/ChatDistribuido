package br.com.pod.objetosremotos;

import java.io.Serializable;
import java.rmi.server.RemoteObject;
import java.util.List;

public class Grupo extends RemoteObject implements Serializable {

    private long id;
    private String nome;
    private List<Long> idUsuarios;

    public Grupo() {
    }

    public Grupo(long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.idUsuarios = idUsuarios;
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

    public List<Long> getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(List<Long> idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

}
