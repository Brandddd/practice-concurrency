package com.example.client;

import java.util.*;
import java.util.concurrent.*;

public class NetworkClientMain {

    public static void printMap(Map<RequestResponse, Future<RequestResponse>> callable){
        System.out.println("IMPRESION DE LOS HILOS");
        for (Map.Entry<RequestResponse, Future<RequestResponse>> entry : callable.entrySet()) {
            Future<RequestResponse> Response = entry.getValue();
            try {
                System.out.println(Response.get().port + " -> Result:" + Response.get().response);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        String host = "localhost";
        ExecutorService executorService = Executors.newCachedThreadPool();
        Map<RequestResponse, Future<RequestResponse>> callables = new HashMap<>();

        for (int port = 10000; port < 10010; port++) {
                RequestResponse referencia = new RequestResponse(host, port);
                NetworkClientCallable networkClientCallable = new NetworkClientCallable(referencia);
                Future<RequestResponse> futureResponse = executorService.submit(networkClientCallable) ;
                callables.put(referencia,futureResponse);
        }
        executorService.shutdownNow();
        printMap(callables);
    }
    
}