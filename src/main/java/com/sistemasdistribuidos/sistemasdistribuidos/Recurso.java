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
    public String ipAddress;
    public Integer port;
    public String nomeProcesso;
    private boolean recurso1 = false;
    private boolean recurso2 = false;
    private final List<String> listaProcessos = new ArrayList<>();
    private String mensagem = ""; 
    
    public Recurso(String ip, Integer porta, String nome){
        this.ipAddress = ip;
        this.port = porta;
        this.nomeProcesso = nome;
    }
    
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
    
    public synchronized String getMensagem(){
        return mensagem;
    }
    public synchronized void setMensagem(String msgm){
        this.mensagem = msgm;
    }
}
