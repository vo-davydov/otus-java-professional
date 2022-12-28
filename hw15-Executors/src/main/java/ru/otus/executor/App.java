package ru.otus.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class App {

    // Два потока печатают числа от 1 до 10, потом от 10 до 1.
    // Надо сделать так, чтобы числа чередовались, т.е. получился такой вывод:
    // Поток 1: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
    // Поток 2: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
    // Всегда должен начинать Поток 1.

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private boolean toggle;

    private final Object lock = new Object();

    public static void main(String[] args) {
        new App().demo();
    }

    private void demo() {
        var t1 = new Thread(() -> {
            AtomicInteger value = new AtomicInteger(0);
            while (true) {
                if (value.get() < 10) {
                    while (value.get() < 10) {
                        execInLock(value, AtomicInteger::incrementAndGet, true);
                    }
                } else {
                    while (value.get() > 1) {
                        execInLock(value, AtomicInteger::decrementAndGet, true);
                    }
                }
            }
        });

        var t2 = new Thread(() -> {
            AtomicInteger value = new AtomicInteger(0);
            while (true) {
                if (value.get() < 10) {
                    while (value.get() < 10) {
                        execInLock(value, AtomicInteger::incrementAndGet, false);
                    }

                } else {
                    while (value.get() > 1) {
                        execInLock(value, AtomicInteger::decrementAndGet, false);
                    }
                }
            }
        });
        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }

    public void execInLock(AtomicInteger value, Consumer<AtomicInteger> consumer, boolean localToggle) {
        synchronized (lock) {
            while (toggle == localToggle) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
            toggle = localToggle;
            lock.notifyAll();
            logger.info("Value: " + value);
            consumer.accept(value);
        }
    }
}
