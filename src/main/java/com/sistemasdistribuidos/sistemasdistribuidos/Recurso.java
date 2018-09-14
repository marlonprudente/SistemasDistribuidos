/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marlon
 */
public class Recurso implements Serializable {

    public String ipAddress;
    public Integer port;
    public String nomeProcesso;
    private boolean recurso1 = false;
    private boolean recurso2 = false;
    private boolean desejoRecurso1 = false;
    private boolean desejoRecurso2 = false;
    private final List<String> listaProcessos = new ArrayList<>();
    private final List<String> listaRespostas = new ArrayList<>();
    private boolean threadRecurso1 = false;
    private boolean threadRecurso2 = false;
    private final Map<String, Boolean> processosRecurso1 = new HashMap<String, Boolean>();
    private final Map<String, Boolean> processosRecurso2 = new HashMap<String, Boolean>();
    private String mensagem = "";

    public Recurso(String ip, Integer porta, String nome) {
        this.ipAddress = ip;
        this.port = porta;
        this.nomeProcesso = nome;
    }

    public boolean getDesejoRecurso1() {
        return this.desejoRecurso1;
    }

    public boolean getDesejoRecurso2() {
        return this.desejoRecurso2;
    }

    public void setDesejoRecurso1(boolean recurso1) {
        this.desejoRecurso1 = recurso1;
    }

    public void setDesejoRecurso2(boolean recurso2) {
        this.desejoRecurso2 = recurso2;
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

    public List<String> getlistaProcessos() {
        return listaProcessos;
    }

    public void setlistaProcessos(String processo) {
        if (!listaProcessos.contains(processo) && !processo.startsWith(this.nomeProcesso)) {
            listaProcessos.add(processo);
            processosRecurso1.put(processo, false);
            processosRecurso2.put(processo, false);
        }
    }

    public void setlistaRespostas(String processo) {
        if (!listaRespostas.contains(processo) && !processo.startsWith(this.nomeProcesso)) {
            listaRespostas.add(processo);
        }
    }
    
    public synchronized void limparListaRespostas(){
        this.listaRespostas.clear();
    }

    public List<String> getlistaRespostas() {
        return listaRespostas;
    }  

    public Map<String, Boolean> getListaRecurso1() {
        return this.processosRecurso1;
    }

    public Map<String, Boolean> getListaRecurso2() {
        return this.processosRecurso2;
    }

    public void setListaRecurso1(String processo, Boolean value) {
        if (listaProcessos.contains(processo)) {
            processosRecurso1.put(processo, value);
        }
    }

    public void removeListaRecurso1(String processo) {
        if (listaProcessos.contains(processo)) {
            processosRecurso1.remove(processo);
        }
    }

    public void removeListaRecurso2(String processo) {
        if (listaProcessos.contains(processo)) {
            processosRecurso2.remove(processo);
        }
    }

    public void setListaRecurso2(String processo, Boolean value) {
        if (listaProcessos.contains(processo)) {
            processosRecurso2.put(processo, value);
        }
    }

    public void removeListaProcesso(String processo) {
        listaProcessos.remove(processo);
    }

    public synchronized String getMensagem() {
        return mensagem;
    }

    public synchronized void setMensagem(String msgm) {
        this.mensagem = msgm;
    }

    public synchronized boolean getThreadRecurso1() {
        return threadRecurso1;
    }

    public synchronized void setThreadRecurso1(boolean threadRecurso) {
        threadRecurso1 = threadRecurso;
    }

    public synchronized boolean getThreadRecurso2() {
        return threadRecurso2;
    }

    public synchronized void setThreadRecurso2(boolean threadRecurso) {
        threadRecurso2 = threadRecurso;
    }

}
