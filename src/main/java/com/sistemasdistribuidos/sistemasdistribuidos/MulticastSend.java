/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author Marlon
 */
public class MulticastSend {
    MulticastSocket ms = null;
    InetAddress group = null;
    Recurso r;
    /**
     * Classe responsável por enviar mensagem via broadcast
     * @param recurso
     * @throws UnknownHostException
     * @throws IOException 
     */
    public MulticastSend(Recurso recurso) throws UnknownHostException, IOException {
        this.r = recurso;
        group = InetAddress.getByName(r.ipAddress);
        ms = new MulticastSocket(r.port);
        ms.joinGroup(group);
    }
    /**
     * Método de enviar mensagem
     * @param mensagem 
     */
    public void EnviarMensagem(String mensagem){
            r.setMensagem(mensagem);
            byte[] m = mensagem.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, r.port);
            try {
                ms.send(messageOut);
                ms.leaveGroup(group);
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            } finally {
            if (ms != null) {
                ms.close();
            }
        }
    }
}
