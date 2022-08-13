package ru.otus;

import ru.otus.calculator.CalculatorService;
import ru.otus.calculator.CalculatorServiceImpl;
import ru.otus.log.MyLog;

public class App {

    public static void main(String[] args) {
        MyLog<CalculatorService> calculatorServiceMyLog = new MyLog<>();
        CalculatorService c = calculatorServiceMyLog.create(new CalculatorServiceImpl());
        c.calculation(90, 80);
        c.calculation(90);
        c.sum(10, 20);
        c.sum(10, 40, 50);
    }
}
