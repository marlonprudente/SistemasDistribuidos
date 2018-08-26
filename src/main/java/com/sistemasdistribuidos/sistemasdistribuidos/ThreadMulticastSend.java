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
    boolean enviarMensagem = false;
    
    public ThreadMulticastSend(String IPAddress, Integer port, String processoNome, boolean enviar) throws IOException {
        nomeProcesso = processoNome;
        enviarMensagem = enviar;
        group = InetAddress.getByName(IPAddress);
        ms = new MulticastSocket(port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        String mensagem = "";
        while (!mensagem.equals("exit()")) {
            //System.out.println(nomeProcesso + "=> Digite:");
            mensagem = scan.nextLine();
            if(enviarMensagem){
                mensagem = "flagMensagemAlterada";
                enviarMensagem = false;
            }
                
            byte[] m = mensagem.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
            try {                
                ms.send(messageOut);
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        }
        try {
            scan.close();
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
