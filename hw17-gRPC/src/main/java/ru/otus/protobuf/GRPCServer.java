package ru.otus.protobuf;

import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RemoteCountServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

public class GRPCServer {

    private final static Logger logger = Logger.getLogger(GRPCServer.class.getName());
    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var remoteCountService = new RemoteCountServiceImpl();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteCountService)
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Received shutdown request");
            server.shutdown();
            logger.info("Server stopped");
        }));

        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
