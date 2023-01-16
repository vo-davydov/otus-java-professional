package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountMessage;
import ru.otus.protobuf.generated.RemoteCountServiceGrpc;

import java.util.logging.Logger;

public class RemoteCountServiceImpl extends RemoteCountServiceGrpc.RemoteCountServiceImplBase {

    Logger logger = Logger.getLogger(RemoteCountServiceImpl.class.getSimpleName());

    @Override
    public void pushValue(CountMessage request, StreamObserver<CountMessage> responseObserver) {

        for (long i = request.getFirstValue(); i < request.getLastValue() + 1; i++) {
            responseObserver.onNext(buildCountMessage(i, request));
            logger.info("Current value: " + i);
            sleep();
        }

        responseObserver.onCompleted();
    }

    private void sleep() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private CountMessage buildCountMessage(long currentValue, CountMessage request) {
        return CountMessage.newBuilder()
                .setCurrentValue(currentValue)
                .setFirstValue(request.getFirstValue())
                .setLastValue(request.getLastValue())
                .build();
    }

}
