package ru.otus.services;

public class CalculatorServiceImpl implements CalculatorService {

    public CalculatorServiceImpl() {

    }

    @Override
    public long multiply(long x, long y) {
        return Math.multiplyExact(x, y);
    }

    @Override
    public long sum(long x, long y) {
        return x + y;
    }

    @Override
    public long multiply(long[] array) {
        long i = 1;
        for (long l : array) {
            i *= l;
        }

        return i;
    }

    @Override
    public long sum(long[] array) {
        long sum = 0;
        for (long l : array) {
            sum += l;
        }

        return sum;
    }

}
