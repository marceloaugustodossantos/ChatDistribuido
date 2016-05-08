/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pod.gerenciadordepersistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marcelo Augusto
 */
public class GruposRepository {
    
    List<Grupo> grupos = new ArrayList<>();

    public GruposRepository() {
    }
    
    public void adicionarGrupo(Grupo grupo){
        grupos.add(grupo);
    }
    
    public void removerGrupo(Grupo grupo){
        grupos.remove(grupo);
    }

    @Override
    public String toString() {
        return "GruposRepository{" + "grupos=" + grupos + '}';
    }
    
}
