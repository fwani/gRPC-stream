package com.fwani.client;

import com.fwani.grpc.HelloServiceGrpc;
import com.fwani.grpc.RequestMessage;
import com.fwani.grpc.ResponseMessage;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class ClientNonBlockingApp {
    static class ResponseObserver implements StreamObserver<ResponseMessage> {
        List<String> buffer = new ArrayList<>();
        @Override
        public void onNext(ResponseMessage responseMessage) {
            buffer.add(responseMessage.getData());
            System.out.println(responseMessage.getData());
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println(throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("completed");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", 5555)
                .usePlaintext()
                .build();

        var stub = HelloServiceGrpc.newStub(channel);
        var obs = new ResponseObserver();
        stub.helloServerStream(
                RequestMessage.newBuilder()
                        .setData("this is async request")
                        .build(),
                obs
        );
        Thread.sleep(10000);

        System.out.println(obs.buffer);

//        while (response.hasNext()){
//            System.out.println(response.next().getData());
//        }
//        channel.shutdown();
    }
}
