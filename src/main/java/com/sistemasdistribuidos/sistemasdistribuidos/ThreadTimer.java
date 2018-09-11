/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Marlon
 */
public class ThreadTimer extends Thread {

    TimerTask timerTask;
    Integer counter = 0;
    Recurso r;

    public ThreadTimer(Recurso recurso) {
        this.r = recurso;
        
        //create timer task to increment counter
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask executing counter is: " + counter);
                counter++;
            }
        };
    }

    @Override
    public void run() {
        
        Timer timer = new Timer("MyTimer");//create a new timer
        timer.scheduleAtFixedRate(timerTask, 0, 60000);//start timer in 30ms to increment  counter
                //cancel after sometime
        try {
            Thread.sleep(30000); //Trinta segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");       
        
        if(r.getlistaProcessos().size() != r.getlistaRespostas().size()){
            for(String l : r.getlistaProcessos()){
                if(!r.getlistaRespostas().contains(l)){
                    //não tem o elemento, remover
                    r.removeListaProcesso(l);
                    r.removeListaRecurso1(l);
                    r.removeListaRecurso2(l);
                    System.out.println("Removi o " + l + "por inatividade.");
                }
            }
        }
        this.interrupt();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
