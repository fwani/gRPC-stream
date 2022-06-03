package com.fwani.client;

import com.fwani.grpc.HelloServiceGrpc;
import com.fwani.grpc.RequestMessage;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class ClientBlockingApp {
    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", 5555)
                .usePlaintext()
                .build();

        var stub = HelloServiceGrpc.newBlockingStub(channel);
        var response = stub.helloServerStream(
                RequestMessage.newBuilder()
                        .setData("this is blocking request")
                        .build()
        );
        while (response.hasNext()) {
            Thread.sleep(4000);
            System.out.println(response.next().getData());
        }
        channel.shutdown();
    }
}
