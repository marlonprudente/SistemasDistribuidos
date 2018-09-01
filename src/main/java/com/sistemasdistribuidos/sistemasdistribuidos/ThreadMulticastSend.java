/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marlon
 */
public class ThreadMulticastSend extends Thread {

    MulticastSocket ms = null;
    InetAddress group = null;
    String nomeProcesso = "";
    Recurso r;
    
    public ThreadMulticastSend(Recurso r) throws IOException {
        this.r = r;
        nomeProcesso = r.nomeProcesso;
        group = InetAddress.getByName(r.ipAddress);
        ms = new MulticastSocket(r.port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        
        String mensagem  = "";
        do {
            if (!r.getMensagem().startsWith("")) {
                System.out.println("Sm: " + r.getMensagem());
                //mensagem = r.getMensagem();
                r.setMensagem("");
            }
            byte[] m = mensagem.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
            try {
                ms.send(messageOut);
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        }while (!mensagem.trim().equalsIgnoreCase("exit()"));
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
