package ru.otus.calculator;

public interface CalculatorService {
    void calculation(int x);

    void calculation(int x, int... y);

    void sum(int x, int y);

    void sum(int x, int... y);
}

