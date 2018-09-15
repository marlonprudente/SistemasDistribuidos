/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import static java.lang.System.in;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Marlon Prudente
 */
public class Main {

    public static void main(String[] args) throws IOException {
        /**
         * Inicializaçãoi das variáveis Todas as variáveis são iniciadas e
         * alocadas no <b>"Recurso"</b> que é a variável que armazena todas as
         * informações da aplicação.
         */
        Scanner scan = new Scanner(System.in);
        Integer op;
        String ipAddress = "224.42.42.42";
        Integer port = 6789;
        String nomeProcesso = "Process1";
        String mensagem = "apresentacao";
        byte[] m;
        MulticastSocket ms = null;
        InetAddress group = null;
        group = InetAddress.getByName(ipAddress);
        ms = new MulticastSocket(port);
        ms.joinGroup(group);
        /**
         * Recurso é onde se controla todas as variáveis.
         */
        Recurso r = new Recurso(ipAddress, port, nomeProcesso);
        /**
         * Seta a mensagem inicial como apresentação.
         */
        r.setMensagem(nomeProcesso + ":apresentacao:");
        /**
         * Inicia a Thread que irá ficar escutando mensagens.
         */
        ThreadMulticastReceive tmr = new ThreadMulticastReceive(r);
//        ThreadMulticastSend tms = new ThreadMulticastSend(r);
        tmr.start();
//        tms.start();

        //Variável que irá controlar as mensagens a serem enviadas.
        m = mensagem.getBytes();
        boolean enviar = true;
        //loop para teste de funcionalidades
        while (true) {
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Apresentação ");
            System.out.println("2 - Obter Recurso 1");
            System.out.println("3 - Obter Recurso 2");
            System.out.println("4 - Ver lista de Processos");
            System.out.println("5 - Sair");
            System.out.println("6 - Soltar Recurso 1");
            System.out.println("7 - Soltar Recurso 2");
            op = scan.nextInt();
            switch (op) {
                case 1:
                    mensagem = nomeProcesso + ":apresentacao:";
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 2:
                    mensagem = nomeProcesso + ":getRecurso1:";
                    System.out.println("ThreadR1: " + r.getThreadRecurso1());
                    if (!r.getThreadRecurso1()) {
                        r.setThreadRecurso1(true);
                        ThreadTimer tt = new ThreadTimer(r, "Recurso1");
                        tt.start();
                    }
                    r.setDesejoRecurso1(true);
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 3:
                    mensagem = nomeProcesso + ":getRecurso2:";
                    System.out.println("ThreadR2: " + r.getThreadRecurso2());
                    if (!r.getThreadRecurso2()) {
                        r.setThreadRecurso2(true);
                        ThreadTimer tt = new ThreadTimer(r, "Recurso1");
                        tt.start();
                    }
                    r.setDesejoRecurso2(true);
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 4:
                    List<String> lista = r.getlistaProcessos();
                    lista.forEach((l) -> {
                        System.out.println(":>" + l);
                    });
                    break;
                case 5:
                    mensagem = nomeProcesso + ":estouSaindo:";
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 6:
                    System.out.println("Soltar Recurso1");
                    Map.Entry<String,Boolean> r1 = r.getListaRecurso1().entrySet().iterator().next();
                    r.setRecurso1(false);
                    r.setDesejoRecurso1(false);
                    r.setThreadRecurso1(false);
                    mensagem = nomeProcesso + ":Recurso1Livre:" + r1.getKey();
                     m = mensagem.getBytes();
                    System.out.println("Mensagem: " + mensagem);
                    enviar = true;
                    r.removeListaRecurso1(r1.getKey());
                    break;
                case 7:
                    System.out.println("Soltar Recurso2");
                    Map.Entry<String,Boolean> r2 = r.getListaRecurso2().entrySet().iterator().next();
                    r.setRecurso2(false);
                    r.setDesejoRecurso2(false);
                    r.setThreadRecurso2(false);
                    mensagem = nomeProcesso + ":Recurso2Livre:" + r2.getKey();
                    m = mensagem.getBytes();
                    enviar = true;
                    r.removeListaRecurso1(r2.getKey());
                    break;
                default:
                    System.out.println("Opção Inválida");
            }

            //Enviar mensagem via broadcast.
            if (enviar) {
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, port);
                try {
                    ms.send(messageOut);
                } catch (IOException ex) {
                    System.out.println("Erro: " + ex);
                }
                enviar = false;
            }
        }
    }

}
