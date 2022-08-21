package ru.otus.banknotes;

public enum Denomination {
    ONE(1),
    FIVE(5),
    TEN(10),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
