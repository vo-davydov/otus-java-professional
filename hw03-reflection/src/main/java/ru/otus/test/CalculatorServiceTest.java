package ru.otus.test;

import ru.otus.mytest.annotations.After;
import ru.otus.mytest.annotations.Before;
import ru.otus.mytest.annotations.Test;
import ru.otus.services.CalculatorService;
import ru.otus.services.CalculatorServiceImpl;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;
    private long x;
    private long y;
    private long sum;
    private long multiply;
    private long[] array;
    private long sumResult;
    private long multiplyResult;


    @Before
    public void setUp() {
        x = 100;
        y = 200;
        sum = 300;
        multiply = 20_000;
        array = new long[]{10, 20, 30};
        sumResult = 60;
        multiplyResult = 6000;
        calculatorService = new CalculatorServiceImpl();
        System.out.println("done set up");
    }

    @Test
    public void testSum() {
        if (x == 0 || y == 0 || sum == 0 || array == null || sumResult == 0) {
            throw new RuntimeException("Variables should be initialized");
        }

        long result = calculatorService.sum(x, y);

        if (result != sum) {
            throw new RuntimeException("Result is not equals");
        }

        result = calculatorService.sum(array);

        if (result != sumResult) {
            throw new RuntimeException("Result is not equals");
        }
        System.out.println("done sum");
        throw new RuntimeException();
    }

    @Test
    public void testMultiply() {
        if (x == 0 || y == 0 || multiply == 0 || array == null || multiplyResult == 0) {
            throw new RuntimeException("Variables should be initialized");
        }

        long result = calculatorService.multiply(x, y);

        if (result != multiply) {
            throw new RuntimeException("Result is not equals");
        }

        result = calculatorService.multiply(array);

        if (result != multiplyResult) {
            throw new RuntimeException("Result is not equals");
        }

        System.out.println("done multiply");
    }

    @After
    public void destroy() {
        x = 0;
        y = 0;
        sum = 0;
        multiply = 0;
        array = null;
        sumResult = 0;
        multiplyResult = 0;
        calculatorService = null;

        System.out.println("done after");
    }

}
