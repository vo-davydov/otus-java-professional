package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorEvenSecondException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorEvenSecondExceptionTest {

    @Test
    @DisplayName("Тестируем ежесекундный процессор")
    void handleSecondsProcessorsTest() {
        var message = new Message.Builder(1L).field7("field7").build();

        var secondsProcessor = new ProcessorEvenSecondException(() -> LocalDateTime.of(1999, 1, 1, 1, 1, 2));

        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> secondsProcessor.process(message));

    }

}
