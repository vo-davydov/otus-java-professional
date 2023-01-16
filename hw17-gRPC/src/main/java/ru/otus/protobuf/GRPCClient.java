package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.client.ClientStreamObserver;
import ru.otus.protobuf.generated.CountMessage;
import ru.otus.protobuf.generated.RemoteCountServiceGrpc;

import java.util.logging.Logger;

public class GRPCClient {

    private static final Logger logger = Logger.getLogger(GRPCClient.class.getSimpleName());
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {

        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteCountServiceGrpc.newStub(channel);

        var firstMessage = CountMessage
                .newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();

        var clientStreamObserver = new ClientStreamObserver();

        logger.info("Client numbers are starting...");

        stub.pushValue(firstMessage, clientStreamObserver);

        var currentValue = 0L;

        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + clientStreamObserver.getFirstOrZero() + 1;
            logger.info("Current value is: " + (currentValue));
            sleep();
        }

        channel.shutdown();
    }

    public static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
