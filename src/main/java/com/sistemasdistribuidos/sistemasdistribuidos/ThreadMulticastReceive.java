/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marlon
 */
public class ThreadMulticastReceive extends Thread {

    MulticastSocket ms = null;
    InetAddress group = null;
    String nomeProcesso = "";
    Recurso r;
    Integer counter;

    public ThreadMulticastReceive(Recurso r) throws IOException {
        this.r = r;
        this.counter = 1;
        nomeProcesso = r.nomeProcesso;
        group = InetAddress.getByName(r.ipAddress);
        ms = new MulticastSocket(r.port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        String mensagem = "";
        do {
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try {
                ms.receive(messageIn);
                mensagem = new String(messageIn.getData());
                System.out.println("Recebi: " + mensagem);
                MessageManager mm = new MessageManager(r,mensagem);
                mm.start();
            } catch (IOException e) {
                System.out.println("" + e);
            }

        } while (!mensagem.trim().equalsIgnoreCase("exit()"));
        try {
            ms.leaveGroup(group);
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        } finally {
            if (ms != null) {
                ms.close();
                this.interrupt();
            }
        }
    }

}
