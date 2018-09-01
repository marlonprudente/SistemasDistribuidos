/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marlon
 */
public class Recurso {

    private volatile boolean recurso1 = false;
    private volatile boolean recurso2 = false;
    private volatile List<String> listaProcessos = new ArrayList<String>();
    private volatile String mensagem = "";
    
    public boolean getRecurso1() {
        return recurso1;
    }
    public void setRecurso1(boolean status) {
        recurso1 = status;
    }
    public boolean getRecurso2() {
        return recurso2;
    }
    public void setRecurso2(boolean status) {
        recurso2 = status;
    }
    
    public List<String> getlistaProcessos(){
        return listaProcessos;
    }
    public void setlistaProcessos(String processo){
        listaProcessos.add(processo);
    }
    
    public String getMensagem(){
        return this.mensagem;
    }
    public void setMensagem(String msgm){
        System.out.println("mensagem " + msgm);
        this.mensagem = msgm;
         System.out.println("mensagem " + mensagem);
    }
}
