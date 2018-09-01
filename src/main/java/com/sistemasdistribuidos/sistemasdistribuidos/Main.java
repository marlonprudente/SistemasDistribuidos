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
    
    public static void main(String[] args) throws IOException {        
        String nomeProcesso = "P1";
        
        ThreadMulticastReceive tmr = new ThreadMulticastReceive("224.42.42.42",6789,nomeProcesso);
        tmr.start();        
        ThreadMulticastSend tms = new ThreadMulticastSend("224.42.42.42",6789,nomeProcesso);
        tms.start();
    }
    
}
