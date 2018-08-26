/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;

/**
 *
 * @author a1562339
 */
public class Main {
    //private static final Integer RECURSO1 = 0;
    //private static final Integer RECURSO2 = 0;
    public static boolean enviarMensagem = false;
    
    public static void main(String[] args) throws IOException {        
        String nomeProcesso = "P1";
        Recurso r = new Recurso();
        ThreadMulticastReceive tmr = new ThreadMulticastReceive("224.42.42.42",6789,nomeProcesso, r);
        tmr.start();        
       // ThreadMulticastSend tms = new ThreadMulticastSend("224.42.42.42",6789,nomeProcesso, enviarMensagem);
        //tms.start();
        
        //Processos p1 = new Processos("224.42.42.42",6789,"P1");
        //Processos p2 = new Processos("224.42.42.42",6789,"P2");
        //Processos p3 = new Processos("224.42.42.42",6789,"P3");
        
        //p1.start();
        //p2.start();
        //p3.start();
    }
    
}
