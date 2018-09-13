/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistemasdistribuidos.sistemasdistribuidos;

import java.util.Iterator;
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
    String nomeRecurso;

    public ThreadTimer(Recurso recurso, String nomeRecurso) {
        this.r = recurso;
        this.nomeRecurso = nomeRecurso;
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

        if (r.getlistaProcessos().size() != r.getlistaRespostas().size()) {
            
            Iterator<String> it = r.getlistaProcessos().iterator();
            while (it.hasNext()) {
                if (!r.getlistaRespostas().contains(it.next())) {
                    //não tem o elemento, remover
                    r.removeListaProcesso(it.next());
                    r.removeListaRecurso1(it.next());
                    r.removeListaRecurso2(it.next());
                    System.out.println("Removerei o " + it.next() + "por inatividade.");
                    if (this.nomeRecurso.startsWith("Recurso1")) {
                        r.setThreadRecurso1(false);
                    }
                    if (this.nomeRecurso.startsWith("Recurso2")) {
                        r.setThreadRecurso2(false);
                    }

                }
            }
        }
        this.interrupt();
    }
}
