package ru.otus.calculator;

import ru.otus.log.annotations.Log;
import ru.otus.log.handler.LogHandler;

import java.util.Arrays;
import java.util.logging.Logger;

public class CalculatorServiceImpl implements CalculatorService {

    private final Logger logger = Logger.getLogger(LogHandler.class.getName());

    @Log
    @Override
    public void calculation(int x) {
        logger.info(String.valueOf(x * x));
    }

    @Log
    @Override
    public void calculation(int x, int... y) {
        logger.info(String.valueOf(x * Arrays.stream(y).sum()));
    }

    @Override
    public void sum(int x, int y) {
        logger.info(String.valueOf(x + y));
    }

    @Log
    @Override
    public void sum(int x, int... y) {
        logger.info(String.valueOf(x + Arrays.stream(y).sum()));
    }

}
