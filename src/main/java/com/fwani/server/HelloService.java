package com.fwani.server;

import com.fwani.grpc.HelloServiceGrpc;
import com.fwani.grpc.RequestMessage;
import com.fwani.grpc.ResponseMessage;
import io.grpc.stub.StreamObserver;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void helloServerStream(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {
        System.out.println(request.getData());
        for (int i=0; i<10; i++){
            System.out.println("send data = " + i);
            responseObserver.onNext(
                    ResponseMessage.newBuilder()
                            .setData("num = " + i)
                            .build()
            );
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}
