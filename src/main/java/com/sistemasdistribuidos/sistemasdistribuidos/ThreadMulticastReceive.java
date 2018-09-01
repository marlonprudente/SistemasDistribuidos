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
    String msgm = "";
    Recurso recursoCompartilhado = null;    
    boolean rec1 = false;
    boolean rec2 = false;

    public ThreadMulticastReceive(String IPAddress, Integer port, String processoNome) throws IOException {
        nomeProcesso = processoNome;
        //recursoCompartilhado = recurso;
        //msgm = recurso;
        group = InetAddress.getByName(IPAddress);
        ms = new MulticastSocket(port);
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
                System.out.println("Received: " + mensagem);
            } catch (IOException e) {
                System.out.println("" + e);
            }
        }while(!mensagem.trim().equalsIgnoreCase("exit()"));
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
