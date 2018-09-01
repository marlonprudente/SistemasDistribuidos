/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;

/**
 *
 * @author Marlon Prudente
 */
public class Main {    
    public static void main(String[] args) throws IOException {        
        String ipAddress = "224.42.42.42";
        Integer port = 6789;
        String nomeProcesso = "Process1";
        
        Recurso r = new Recurso(ipAddress,port,nomeProcesso);        
        ThreadMulticastReceive tmr = new ThreadMulticastReceive(r);     
        ThreadMulticastSend tms = new ThreadMulticastSend(r);
        
        
        tmr.start();
        tms.start();
    }
    
}
