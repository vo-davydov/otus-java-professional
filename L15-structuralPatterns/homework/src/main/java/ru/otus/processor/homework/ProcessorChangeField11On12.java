package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorChangeField11On12 implements Processor {
    @Override
    public Message process(Message message) {
        var field11 = message.getField11();
        var filed12 = message.getField12();
        return message.toBuilder()
                .field11(filed12)
                .field12(field11)
                .build();
    }
}
