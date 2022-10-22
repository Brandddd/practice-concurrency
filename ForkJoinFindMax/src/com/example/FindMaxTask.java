package com.example;

import java.util.concurrent.RecursiveTask;

public class FindMaxTask extends RecursiveTask<Integer> {
    private final int threshold;
    private final int[] myArray;
    private int start;
    private int end;

    public FindMaxTask(int[] myArray, int start, int end, int threshold) {
        this.threshold = threshold;
        this.myArray = myArray;
        this.start = start;
        this.end = end;
    }

    /*
     * Método compute, no recibe ningún parámetro
     * Retorna un entero
     * 
     */

    protected Integer compute() {
        
        if (end - start < threshold) {
            int max = Integer.MIN_VALUE;
            // * i = 0, n = numero que hay en la matriz[i], si n > max (MIN_VALUE), max = valor de n.
            /* 
             * Con este for se determinará cuál es el número máximo después de partir la matriz myArray en 
             * dos partes iguales.
             */
            for (int i = start; i <= end; i++) {
                int n = myArray[i];
                if (n > max) {
                    max = n;
                }
            }
            // * Cuando sale del ciclo for, se retornará el número máximo que hay dentro de la matriz
            return max;
        } else {
            // * Divide el tamaño de la matriz en dos para distribuirlo en dos procesos u hilos.
            int midway = (end - start) / 2 + start; // * Tecnica divide y vencerás para dividirlo en dos procesos Fork.
            // * Llamando nuevamente la clase FindMaxTask pero esta vez con la primera mitad de la matriz.
            FindMaxTask a1 = new FindMaxTask(myArray, start, midway, threshold);
            a1.fork(); // * Mandando la matriz al proceso 
            // * Llamando nuevamente la clase FindMaxTask pero esta vez con la segunda mitad de la matriz.
            FindMaxTask a2 = new FindMaxTask(myArray, midway + 1, end, threshold);
            // * Retorna el número máximo que hay entre las dos mitades
            return Math.max(a2.compute(), a1.join());
        }
    }
}