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
import java.util.Scanner;

/**
 *
 * @author Marlon Prudente
 */
public class Main {

    public static void main(String[] args) throws IOException {
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

        Recurso r = new Recurso(ipAddress, port, nomeProcesso);
        r.setMensagem(nomeProcesso + ":apresentacao");
        ThreadMulticastReceive tmr = new ThreadMulticastReceive(r);
//        ThreadMulticastSend tms = new ThreadMulticastSend(r);

        tmr.start();
//        tms.start();
        m = mensagem.getBytes();
        boolean enviar = false;
        while (true) {
            System.out.println("Digite a opção desejada: ");
            op = scan.nextInt();
            switch (op) {
                case 1:
                    mensagem = nomeProcesso + ":apresentacao";
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 2:
                    mensagem = nomeProcesso + ":getRecurso1";
                    r.setDesejoRecurso1(true);
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 3:
                    mensagem = nomeProcesso + ":getRecurso2";
                    r.setDesejoRecurso2(true);
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 4:
                    System.out.println(":>" + r.getMensagem());
                    break;
                case 5:
                    List<String> lista = r.getlistaProcessos();
                    lista.forEach((l) -> {
                        System.out.println(":>" + l);
                    });
                    break;
                case 6:
                    r.setMensagem(nomeProcesso + ":exit()");
                    break;
                case 7:
                    mensagem = nomeProcesso + ":estouSaindo";
                    m = mensagem.getBytes();
                    enviar = true;
                    break;
                case 8:
                    System.out.println("Soltar Recurso1");
                    r.setRecurso1(false);
                    break;
                case 9:
                    System.out.println("Soltar Recurso2");
                    r.setRecurso2(false);
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
            if (enviar) {

                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
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
