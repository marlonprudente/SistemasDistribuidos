/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;

/**
 *
 * @author Marlon
 */
public class MessageManager extends Thread {

    Recurso r;
    String mensagem;
    MulticastSend enviarMensagem;

    public MessageManager(Recurso recurso, String mensagem) throws IOException {
        this.r = recurso;
        this.mensagem = mensagem;
        enviarMensagem = new MulticastSend(recurso);
    }

    @Override
    public void run() {
        //protecao para evitar nullPointerException
        if (this.mensagem == null) {
            this.mensagem = "";
        }
        String[] decode;
        String nomeProcesso;
        String nomeComando;
        decode = mensagem.split(":");
        nomeProcesso = decode[0];
        nomeComando = decode[1];

        if (nomeComando == null) {
            nomeComando = "apresentacao";
        }

        if (nomeComando.startsWith("apresentacao")) {
            System.out.println("Adicionei Processo");
            r.setlistaProcessos(nomeProcesso);
        } else if (nomeComando.startsWith("getRecurso1")) {
            if (r.getRecurso1()) {
                System.out.println("R1Preso");
                enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso1Preso");
            } else {
                System.out.println("R1Livre");
                enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso1Livre");
            }
        } else if (nomeComando.startsWith("getRecurso2")) {
            if (r.getRecurso2()) {
                System.out.println("R2Preso");
                enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso2Preso");
            } else {
                System.out.println("R2Livre");
                enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso2Livre");
            }
        } else if (nomeComando.startsWith("estouSaindo")) {
            System.out.println("RemovendoDaLista");
            r.removeListaProcesso(nomeProcesso);
            r.removeListaRecurso1(nomeProcesso);
            r.removeListaRecurso2(nomeProcesso);

        } else if (nomeComando.startsWith("Recurso1Livre")) {
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                if (r.getDesejoRecurso1()) {
                    r.setListaRecurso1(nomeProcesso, Boolean.FALSE);
                    r.setlistaRespostas(nomeProcesso);
//                    ThreadTimer tt = new ThreadTimer(r);
//                    tt.start();
                    if (!r.getListaRecurso1().values().contains(true)) {
                        r.setRecurso1(true);
                    }
                }
            }
        } else if (nomeComando.startsWith("Recurso1Preso")) {
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                r.setListaRecurso1(nomeProcesso, Boolean.TRUE);
            }

        } else if (nomeComando.startsWith("Recurso2Livre")) {
            //tratar se é solicitação do processo ou não
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                if (r.getDesejoRecurso2()) {
                    r.setListaRecurso2(nomeProcesso, Boolean.FALSE);
                    if (!r.getListaRecurso2().values().contains(true)) {
                        r.setRecurso2(true);
                    }
                }
            }
        } else if (nomeComando.startsWith("Recurso2Preso")) {
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                r.setListaRecurso2(nomeProcesso, Boolean.TRUE);
            }

        } else {
            System.out.println("Não entrei em nenhuma opcao");
        }

        this.interrupt();
    }
}
