package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Instant;

public class ProcessorEvenSecondException implements Processor {

    private final Instant instant;

    public ProcessorEvenSecondException(Instant instant) {
        this.instant = instant;
    }

    @Override
    public Message process(Message message) {

        if (isEvenSecond()) {
            throw new EvenSecondException("It is even second!");
        }

        return message;
    }

    private boolean isEvenSecond() {
        return instant.getEpochSecond() % 2 == 0;
    }

}
