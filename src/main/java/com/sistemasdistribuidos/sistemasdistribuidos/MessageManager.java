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

    /**
     * Gerenciamento de mensagens Verifica as mensagens recebidas e faz o devido
     * tratamento das mesmas
     *
     * @param recurso
     * @param mensagem
     * @throws IOException
     */
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
        //Apresentacao: Todas as aplicações devem enviar mensagem de apresentação
        //Quando a aplicação não tem o registro da mesma, é inserido na lista de processos
        if (nomeComando.startsWith("apresentacao")) {
            System.out.println("Adicionei Processo");
            r.setlistaProcessos(nomeProcesso);
            //Trata o pedido de recurso, se a aplicação não possui o recurso, informa que o mesmo está livre
            //Caso contrário, informa que está preso.
        } else if (nomeComando.startsWith("getRecurso1")) {
                if (r.getRecurso1()) {
                    System.out.println("R1Preso");
                    enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso1Preso");
                } else {
                    System.out.println("R1Livre");
                    enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso1Livre");
                }

            //Trata o pedido de recurso, se a aplicação não possui o recurso, informa que o mesmo está livre
            //Caso contrário, informa que está preso.
        } else if (nomeComando.startsWith("getRecurso2")) {

                if (r.getRecurso2()) {
                    System.out.println("R2Preso");
                    enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso2Preso");
                } else {
                    System.out.println("R2Livre");
                    enviarMensagem.EnviarMensagem(r.nomeProcesso + ":Recurso2Livre");
                }
            //Trata informação de que está saindo, removendo o mesmo das listas.
        } else if (nomeComando.startsWith("estouSaindo")) {
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                System.out.println("RemovendoDaLista");
                r.removeListaRecurso1(nomeProcesso);
                r.removeListaRecurso2(nomeProcesso);
                r.removeListaProcesso(nomeProcesso);
            }
            //Trata informação de que recurso está livre, e caso o pedido tenha partido
            //da própria aplicação, aguarda as outras aplicações responderem
        } else if (nomeComando.startsWith("Recurso1Livre")) {
            r.setlistaRespostas(nomeProcesso);
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                if (r.getDesejoRecurso1()) {
                    r.setListaRecurso1(nomeProcesso, Boolean.FALSE);
                    for(String teste  : r.getListaRecurso1().keySet()){
                        System.out.println("Lista procR1 ==> " + teste);
                    }
                    if (!r.getListaRecurso1().values().contains(true)) {
                        r.setRecurso1(true);
                    }
                }
            }else if(r.getlistaProcessos().isEmpty()){
                r.setRecurso1(true);
            }
            //Trata a informação de que outra aplicação está com o recurso, 
            //atualizando a lista de recursos
        } else if (nomeComando.startsWith("Recurso1Preso")) {
            r.setlistaRespostas(nomeProcesso);
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                r.setListaRecurso1(nomeProcesso, Boolean.TRUE);
            }
            //Trata informação de que recurso está livre, e caso o pedido tenha partido
            //da própria aplicação, aguarda as outras aplicações responderem
        } else if (nomeComando.startsWith("Recurso2Livre")) {
            r.setlistaRespostas(nomeProcesso);
            //tratar se é solicitação do processo ou não
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                if (r.getDesejoRecurso2()) {
                    r.setListaRecurso2(nomeProcesso, Boolean.FALSE);
                    for(String teste  : r.getListaRecurso2().keySet()){
                        System.out.println("Lista procR1 ==> " + teste);
                    }
                    if (!r.getListaRecurso2().values().contains(true)) {
                        r.setRecurso2(true);
                    }
                }
            }else if(r.getlistaProcessos().isEmpty()){
                r.setRecurso2(true);
            }
            //Trata a informação de que outra aplicação está com o recurso, 
            //atualizando a lista de recursos
        } else if (nomeComando.startsWith("Recurso2Preso")) {
            r.setlistaRespostas(nomeProcesso);
            if (!nomeProcesso.startsWith(r.nomeProcesso)) {
                r.setListaRecurso2(nomeProcesso, Boolean.TRUE);
            }

        } else {
            System.out.println("Não entrei em nenhuma opcao");
        }

        this.interrupt();
    }
}
