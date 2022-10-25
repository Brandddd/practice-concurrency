package com.example;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

// Se amplía la clase de RandomArrayAction con RecursiveAction la cual se usa para tareas que se pueden
// dividir y ejecutar en paralelo. Es una subtarea del ForkJoinPool sin valores de retorno.
public class RandomArrayAction extends RecursiveAction {

    // Campos necesarios.
    private final int threshold;
    private final int[] myArray;
    private int start;
    private int end;
    private int max;

    // Metodo que va retornar el numero maximo, ya que desde el void no se retorna
    // nada
    public int getMax() {
        return max;
    }

    // Constructor con sus campos para almacenar:
    public RandomArrayAction(int[] myArray, int start, int end, int threshold) {
        this.myArray = myArray;
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    // Metodo compute(), en este caso devuelve un void y no un integer como lo es
    // para el caso de RecursiveTask
    public void compute() {

        for (int i = start; i <= end; i++) {
            /*
             * Se usa ThreadLocalRandom en lugar de Math.Random() porque Math.Random() No se
             * escala cuando se ejecuta simultaneamente con varios threads y eliminaría
             * cualquier
             * ventaja de aplicar el marco Fork-join.
             */
            myArray[i] = ThreadLocalRandom.current().nextInt(); // Se inicializa la matriz
        }

        // Si el numero de elementos que procesar está por debajo del umbral de la
        // matriz debe inicializar la matriz
        if (end - start < threshold) {
            int max = Integer.MIN_VALUE;
            for (int i = start; i <= end; i++) {
                int n = myArray[i];
                if (n > max) {
                    max = n;
                }
            }
            this.max = max; // Así igualaremos el max para retornarlo con el metodo getMax()
        } else {
            // Cuando es mayor al umbral se debe encontrar un punto intermedio como se
            // verá
            // A continuación
            int midway = (end - start) / 2 + start;
            // Nueva instancia para cada una de las secciones que procesar.
            RandomArrayAction r1 = new RandomArrayAction(myArray, start, midway, threshold);
            // Nueva instancia 2 para la segunda parte de la matriz a procesar.
            RandomArrayAction r2 = new RandomArrayAction(myArray, midway + 1, end, threshold);
            // Con la función invokeAll en lugar de la combinación de fork/join/compute
            // del antiguo caso se pueden ejecutar los dos procesos simultaneamente.
            invokeAll(r1, r2); // Recibe como parametros las dos tareas a ejecutar
            this.max = Math.max(r1.max, r2.max);
        }
    }
}