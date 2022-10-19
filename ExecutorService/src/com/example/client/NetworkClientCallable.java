package com.example.client;

import java.util.concurrent.Callable;
import java.net.Socket;

public class NetworkClientCallable implements Callable<RequestResponse>{

    public RequestResponse reference;   // * Tipo y nombre del campo.
    
    public NetworkClientCallable(RequestResponse reference) {    // * En el constructor se le envía el tipo de referencia y la referencia 
        this.reference = reference;
    }

    public RequestResponse call() throws Exception {

        Socket socket = new Socket(this.reference.host, this.reference.port);  // * Se crea el socket con la ref de RequestResponse
        System.out.println("Hola soy un cliente en el socket: ");
        System.out.println(socket);

        socket.close();

        return null;

    }
}