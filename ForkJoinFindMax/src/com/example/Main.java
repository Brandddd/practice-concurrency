package com.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    //We use a lot of memory
    //The project properties should have a Run, VM Option of -Xmx1024m
    public static void main(String[] args) {
        int[] data = new int[1024 * 1024 * 128]; //512MB

        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt();  // * Generador de numeros aleatorios dentro de la matriz data
        }

//        int max = Integer.MIN_VALUE;
//        for (int value : data) {
//            if (value > max) {
//                max = value;
//            }
//        }
//        System.out.println("Max value found:" + max);
        
        ForkJoinPool pool = new ForkJoinPool();     // * Invoca una tarea de tipo forkJoinTask.
        FindMaxTask task = new FindMaxTask(data, 0, data.length-1, data.length/16);  // *Invoca clase FindMaxTask
        Integer result = pool.invoke(task);
        System.out.println("Max value found:" + result);
        
    }
}


/*
 * Para poder invocar múltiples subtareas en paralelo de forma recursiva, invocará a una tarea
 * de tipo RecursiveAction, que extiende de ForkJoinTask.
 * La clase RecursiveACtion contiene el método compute(). la cual será la encargada de de ejecutar
 * nuestra tarea paralelizable.
 */