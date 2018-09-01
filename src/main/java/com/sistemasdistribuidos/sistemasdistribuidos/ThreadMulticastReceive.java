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
    
    public ThreadMulticastReceive(Recurso r) throws IOException {
        this.r = r;
        nomeProcesso = r.nomeProcesso;
        group = InetAddress.getByName(r.ipAddress);
        ms = new MulticastSocket(r.port);
        ms.joinGroup(group);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];
        String mensagem = "";
        String[] decode;
        do {           
            if (!r.getMensagem().isEmpty()) {
                System.out.println("Rm: " + r.getMensagem());

                decode = r.getMensagem().split(":");
                if (decode[1].startsWith("apresentacao")) {
                    if (decode[0].startsWith(nomeProcesso)) {
                        r.setlistaProcessos(decode[0]);
                    }
                    r.setMensagem("");
                }
            }
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);            
            try {                
                ms.receive(messageIn);
                mensagem = new String(messageIn.getData());
//                decode = mensagem.split(":");
//                if(decode[1] != null){
//                if(decode[1].startsWith("apresentacao")){
//                    if(!decode[0].startsWith(nomeProcesso)){
//                        r.setlistaProcessos(decode[0]);
//                    }
//                }
//                }
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
