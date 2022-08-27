package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorEvenSecondException;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorEvenSecondExceptionTest {

    @Test
    @DisplayName("Тестируем ежесекундный процессор")
    void handleSecondsProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        Instant instant = Instant.now(Clock.fixed(
                Instant.parse("2018-08-22T10:00:00Z"),
                ZoneOffset.UTC));

        var secondsProcessor = new ProcessorEvenSecondException(instant);

        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> secondsProcessor.process(message));

    }

}
