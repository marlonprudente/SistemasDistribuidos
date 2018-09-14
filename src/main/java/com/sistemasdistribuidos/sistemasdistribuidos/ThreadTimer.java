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
    Recurso r;
    String nomeRecurso;

    public ThreadTimer(Recurso recurso, String nomeRecurso) {
        this.r = recurso;
        this.nomeRecurso = nomeRecurso;
        //create timer task to increment counter
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask executing recurso is: " + nomeRecurso);
            }
        };
    }

    @Override
    public void run() {
        r.limparListaRespostas();
        Timer timer = new Timer("MyTimer");//create a new timer
        timer.scheduleAtFixedRate(timerTask, 0, 60000);//start timer in 30ms to increment  counter
        //cancel after sometime
        try {
            Thread.sleep(5000); //Trinta segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");

        if (r.getlistaProcessos().size() != r.getlistaRespostas().size()) {
            System.out.println("Entrei na comparação das listas.");

            Iterator<String> it = r.getlistaProcessos().iterator();

            while (it.hasNext()) {
                String iterator = it.next();
                System.out.println("Iterator: " + iterator);
                if (!r.getlistaRespostas().contains(iterator)) {
                    try {
                        //não tem o elemento, remover
                        System.out.println("Não tem o elemento ");
                        r.removeListaRecurso1(iterator);
                        r.removeListaRecurso2(iterator);
                        r.removeListaProcesso(iterator);
                        System.out.println("Removi o " + iterator + " por inatividade.");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e);
                    } finally {
                        if (this.nomeRecurso.startsWith("Recurso1")) {
                            r.setThreadRecurso1(false);
                        }
                        if (this.nomeRecurso.startsWith("Recurso2")) {
                            r.setThreadRecurso2(false);
                        }
                        r.limparListaRespostas();
                        this.interrupt();
                    }
                }
            }

        }

//        for (String teste : r.getlistaRespostas()) {
//            System.out.println("respostas de: " + teste);
//        }
        if (this.nomeRecurso.startsWith("Recurso1")) {
            r.setThreadRecurso1(false);
        }
        if (this.nomeRecurso.startsWith("Recurso2")) {
            r.setThreadRecurso2(false);
        }
        r.limparListaRespostas();
        this.interrupt();

    }
}
