/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.io.IOException;
import static java.lang.System.in;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Marlon Prudente
 */
public class Main {    
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Integer op;
        String ipAddress = "224.42.42.42";
        Integer port = 6789;
        String nomeProcesso = "Process2";
        
        Recurso r = new Recurso(ipAddress,port,nomeProcesso);
        r.setMensagem(nomeProcesso + ":apresentacao");
        ThreadMulticastReceive tmr = new ThreadMulticastReceive(r);     
        ThreadMulticastSend tms = new ThreadMulticastSend(r);    
        
        tmr.start();
        tms.start();

        while(true){
        System.out.println("Digite a opção desejada: ");
        op = scan.nextInt();
            switch(op){
                case 1:
                    r.setMensagem(nomeProcesso + ":apresentacao");
                    break;
                case 2:
                    r.setMensagem(nomeProcesso + ":recurso1");
                    break;
                case 3:
                    r.setMensagem(nomeProcesso + ":recurso2");
                    break;
                case 4:
                    System.out.println(":>" + r.getMensagem());
                    break;
                case 5:
                    List<String> lista = r.getlistaProcessos();
                    for(String l :lista){
                        System.out.println(":>" + l);
                    }
                    break;
                case 6:
                    r.setMensagem("exit()");
                    break;
                default:
                    System.out.println("Opção Inválida");
                    
            }
            
        }
    }
    
}
