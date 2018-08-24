/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marlon
 */
public class ThreadMulticastReceive extends Thread {

    MulticastSocket ms = null;
    InetAddress group = null;

    public ThreadMulticastReceive(String IPAddress, Integer port) throws IOException {
        InetAddress group = InetAddress.getByName(IPAddress);
        ms = new MulticastSocket(port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        String mensagem = "teste";
        while (!mensagem.equals("exit()")) {
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try {
                System.out.println("Estou Escutando: ");
                ms.receive(messageIn);
                mensagem = new String(messageIn.getData());
                System.out.println("=>" + mensagem);
                buffer = new byte[1000];
            } catch (Exception e) {
                System.out.println("" + e);
            }
        }
        try {
            ms.leaveGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(ThreadMulticastSend.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ms != null) {
                ms.close();
            }
        }
    }

}
