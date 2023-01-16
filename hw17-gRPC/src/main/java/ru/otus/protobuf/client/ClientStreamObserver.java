package ru.otus.protobuf.client;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountMessage;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

public class ClientStreamObserver implements StreamObserver<CountMessage> {

    private final Logger logger = Logger.getLogger(ClientStreamObserver.class.getSimpleName());

    private final ConcurrentLinkedDeque<Long> concurrentLinkedDeque;

    public ClientStreamObserver() {
        this.concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void onNext(CountMessage value) {
        concurrentLinkedDeque.add(value.getCurrentValue());
        logger.info("New value:" + value.getCurrentValue());
    }

    @Override
    public void onError(Throwable t) {
        logger.info(t.getMessage());
        concurrentLinkedDeque.clear();
    }

    @Override
    public void onCompleted() {
        logger.info("Completed!");
        concurrentLinkedDeque.clear();
    }

    public long getFirstOrZero() {
        var result = 0L;

        if (!concurrentLinkedDeque.isEmpty()) {
            result = concurrentLinkedDeque.getFirst();
        }
        concurrentLinkedDeque.clear();

        return result;
    }
}
