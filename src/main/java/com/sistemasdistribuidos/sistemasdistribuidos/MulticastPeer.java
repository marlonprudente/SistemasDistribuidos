package com.sistemasdistribuidos.sistemasdistribuidos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1562339
 */
public class MulticastPeer {

    public static void main(String args[]) throws Exception {
        // args give message contents and destination multicast group (e.g. "228.5.6.7")
        MulticastSocket s = null;
        InetAddress group = InetAddress.getByName("224.42.42.42");
        s = new MulticastSocket(6789);
        s.joinGroup(group);
        try {
            String mensagem = "Teste";
            Scanner scan = new Scanner(System.in);
            while (!mensagem.equals("exit()")) {
                System.out.println("Me: ");
                mensagem = scan.nextLine(); 
                byte[] m = mensagem.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                s.send(messageOut);
                System.out.println("main");
            }            
            scan.close();
            //t1.interrupt();
            s.leaveGroup(group);

        } catch (Exception e) {
            System.out.println("Socket: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
                
            }
        }
    }
}
