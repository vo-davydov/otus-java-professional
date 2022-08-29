package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecondException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {

        if (isEvenSecond()) {
            throw new EvenSecondException("It is even second!");
        }

        return message;
    }

    private boolean isEvenSecond() {
        var date = dateTimeProvider.getDate();
        return date.getSecond() % 2 == 0;
    }

}
