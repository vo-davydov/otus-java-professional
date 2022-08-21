package ru.otus.banknotes;

import java.util.HashSet;
import java.util.Set;

import static ru.otus.banknotes.Denomination.FIVE;
import static ru.otus.banknotes.Denomination.FIVE_HUNDRED;
import static ru.otus.banknotes.Denomination.FIVE_THOUSAND;
import static ru.otus.banknotes.Denomination.HUNDRED;
import static ru.otus.banknotes.Denomination.ONE;
import static ru.otus.banknotes.Denomination.TEN;
import static ru.otus.banknotes.Denomination.THOUSAND;

public enum Currency {
    USD,
    RUB;

    public static Set<Banknote> eachOneOf(Currency c) {
        Set<Banknote> result = new HashSet<>();
        switch (c) {
            case USD -> {
                result.add(new Banknote(USD, ONE));
                result.add(new Banknote(USD, FIVE));
                result.add(new Banknote(USD, TEN));
                result.add(new Banknote(USD, HUNDRED));
                return result;
            }
            case RUB -> {
                result.add(new Banknote(RUB, HUNDRED));
                result.add(new Banknote(RUB, FIVE_HUNDRED));
                result.add(new Banknote(RUB, THOUSAND));
                result.add(new Banknote(RUB, FIVE_THOUSAND));
                return result;
            }
            default -> {
                return result;
            }
        }
    }
}
