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
        String[] decode;
        do {
            if (!r.getMensagem().startsWith("")) {
                System.out.println("Rm: " + r.getMensagem());
                decode = r.getMensagem().split(":");
                r.setMensagem("");
                if (!decode[0].isEmpty()) {
                    if (decode[1].startsWith("apresentacao")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            if (!r.getlistaProcessos().contains(decode[0])) {
                                r.setlistaProcessos(decode[0]);
                            }
                        }
                    } else if (decode[1].startsWith("getRecurso1")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            if (r.getRecurso1()) {
                                r.setMensagem(nomeProcesso + ":Recurso1Ocupado");
                            } else {
                                r.setMensagem(nomeProcesso + ":Recurso1Livre");
                            }
                        }
                    } else if (decode[1].startsWith("getRecurso2")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            if (r.getRecurso1()) {
                                r.setMensagem(nomeProcesso + ":Recurso2Ocupado");
                            } else {
                                r.setMensagem(nomeProcesso + ":Recurso2Livre");
                            }
                        }
                    } else if (decode[1].startsWith("Recurso1Livre")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            if (counter == r.getlistaProcessos().size()) {
                                r.setRecurso2(true);
                                r.setMensagem(nomeProcesso + ":Recurso1Ocupado");
                            } else {
                                this.counter++;
                            }
                        }
                    } else if (decode[1].startsWith("Recurso2Livre")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            if (counter == r.getlistaProcessos().size()) {
                                r.setRecurso2(true);
                                r.setMensagem(nomeProcesso + ":Recurso2Ocupado");
                            } else {
                                this.counter++;
                            }
                        }
                    } else if (decode[1].startsWith("exit()")) {
                        if (!decode[0].startsWith(nomeProcesso)) {
                            r.removeListaProcesso(decode[0]);
                        } else {
                            mensagem = decode[0];
                        }
                    }
                }
            }
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try {
                ms.receive(messageIn);
                mensagem = new String(messageIn.getData());
                if (!mensagem.isEmpty()) {
                    r.setMensagem(mensagem);
                }
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
