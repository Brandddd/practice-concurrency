package com.example.client;

import java.util.concurrent.Callable;
import java.net.Socket;

public class NetworkClientCallable implements Callable<RequestResponse>{

    RequestResponse request = new RequestResponse(null, 0);

    public NetworkClientCallable() {
    }

    public RequestResponse call() throws Exception {
        return null;
    }
}