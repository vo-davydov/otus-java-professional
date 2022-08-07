package ru.otus.services;

public interface CalculatorService {
    long multiply(long x, long y);

    long sum(long x, long y);

    long multiply(long[] array);

    long sum(long[] array);
}
