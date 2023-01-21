package ru.otus.protobuf.client;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountMessage;

import java.util.logging.Logger;

public class ClientStreamObserver implements StreamObserver<CountMessage> {

    private final Logger logger = Logger.getLogger(ClientStreamObserver.class.getSimpleName());

    private long lastValue;

    public ClientStreamObserver() {

    }

    @Override
    public void onNext(CountMessage value) {
        logger.info(String.format("New value: %s", value.getCurrentValue()));
        setLastValue(value.getCurrentValue());
    }

    @Override
    public void onError(Throwable t) {
        logger.info(t.getMessage());
    }

    @Override
    public void onCompleted() {
        logger.info("Completed!");
    }

    private synchronized void setLastValue(long value) {
        this.lastValue = value;
    }

    public synchronized long getLastValueAndReset() {
       var lastValuePrev = this.lastValue;
       this.lastValue = 0;
       return lastValuePrev;
    }

}
