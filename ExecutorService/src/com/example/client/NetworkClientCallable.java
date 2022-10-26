package com.example.client;

import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class NetworkClientCallable implements Callable<RequestResponse> {

    RequestResponse referencia ;
    public NetworkClientCallable(RequestResponse referencia) {
        this.referencia = referencia;
    }

    @Override
    public RequestResponse call() throws Exception {
        try {
            Socket sock = new Socket(this.referencia.host,this.referencia.port);
            Scanner scanner = new Scanner(sock.getInputStream());
            this.referencia.response = scanner.next();
            sock.close();
            scanner.close();
            return this.referencia;
        } catch (Exception ex) {
            System.out.println("Error en NetworkClientCallable " + ex);
        }
        return this.referencia;
    }

    public RequestResponse getReferencia() {
        return referencia;
    }

    public void setReferencia(RequestResponse referencia) {
        this.referencia = referencia;
    }
}
