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
    String nomeProcesso = "";
    Recurso recursoCompartilhado = null;

    public ThreadMulticastReceive(String IPAddress, Integer port, String processoNome, Recurso recurso) throws IOException {
        nomeProcesso = processoNome;
        recursoCompartilhado = recurso;
        group = InetAddress.getByName(IPAddress);
        ms = new MulticastSocket(port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        String mensagem = "";
        do {
            System.out.println("Estou Escutando: ");
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try {                
                ms.receive(messageIn);
                mensagem = new String(messageIn.getData());
                System.out.println("=>" + mensagem);
                if(mensagem.equalsIgnoreCase("teste1"))
                    recursoCompartilhado.recurso1 = true;
                buffer = new byte[1000];
            } catch (Exception e) {
                System.out.println("" + e);
            }
        }while(!mensagem.equals("exit()"));
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
