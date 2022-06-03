package com.fwani.server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        var server = ServerBuilder
                .forPort(5555)
                .addService(new HelloService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
