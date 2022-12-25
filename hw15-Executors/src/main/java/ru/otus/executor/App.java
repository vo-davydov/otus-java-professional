package ru.otus.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class App {

    // Два потока печатают числа от 1 до 10, потом от 10 до 1.
    // Надо сделать так, чтобы числа чередовались, т.е. получился такой вывод:
    // Поток 1: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
    // Поток 2: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
    // Всегда должен начинать Поток 1.

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private boolean toggle;

    public static void main(String[] args) {
        new App().demo();
    }

    private void demo() {
        var t1 = new Thread(() -> {
            int value = 0;
            while (true) {
                if (value < 10) {
                    value = increaseTo(value, 10, true);
                } else {
                    value = decreaseTo(value, 0, true);
                }
            }
        });

        var t2 = new Thread(() -> {
            int value = 0;
            while (true) {
                if (value < 10) {
                    value = increaseTo(value, 10, false);
                } else {
                    value = decreaseTo(value, 0, false);
                }
            }
        });
        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

    }

    public int increaseTo(int current, int increaseTo, boolean toggleValue) {
        var result = new AtomicInteger(current);
        while (result.get() < increaseTo) {
            if (toggleValue) {
                increaseTrueLock(result);
            } else {
                increaseFalseLock(result);
            }
        }

        return result.get();
    }

    public int decreaseTo(int current, int decreaseTo, boolean toggleValue) {
        var result = new AtomicInteger(current);
        while (result.get() > decreaseTo) {
            if (toggleValue) {
                decreaseTrueLock(result);
            } else {
                decreaseFalseLock(result);
            }
        }

        return result.get();
    }

    public synchronized void increaseTrueLock(AtomicInteger value) {
        while (toggle) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        toggle = true;
        notifyAll();
        logger.info("value: " + value.incrementAndGet());
    }

    public synchronized void increaseFalseLock(AtomicInteger value) {
        while (!toggle) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        toggle = false;
        notifyAll();
        logger.info("value: " + value.incrementAndGet());
    }

    public synchronized void decreaseTrueLock(AtomicInteger value) {
        while (toggle) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        toggle = true;
        notifyAll();
        logger.info("value: " + value.decrementAndGet());

    }


    public synchronized void decreaseFalseLock(AtomicInteger value) {
        while (!toggle) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        toggle = false;
        notifyAll();
        logger.info("value: " + value.decrementAndGet());
    }
}
