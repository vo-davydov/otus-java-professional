package ru.otus.banknotes;

import java.util.HashSet;
import java.util.Set;

import static ru.otus.banknotes.Country.RUB;
import static ru.otus.banknotes.Country.USD;
import static ru.otus.banknotes.Denomination.FIVE;
import static ru.otus.banknotes.Denomination.FIVE_HUNDRED;
import static ru.otus.banknotes.Denomination.FIVE_THOUSAND;
import static ru.otus.banknotes.Denomination.HUNDRED;
import static ru.otus.banknotes.Denomination.ONE;
import static ru.otus.banknotes.Denomination.TEN;
import static ru.otus.banknotes.Denomination.THOUSAND;

public record Banknote(Country type, Denomination denomination) {

    public Banknote copy() {
        return new Banknote(this.type, this.denomination);
    }

    @Override
    public String toString() {
        return "Banknote{" +
                "type=" + type +
                ", denomination=" + denomination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return type == banknote.type && denomination == banknote.denomination;
    }

    public static Set<Banknote> eachOneOf(Country country) {
        Set<Banknote> result = new HashSet<>();
        switch (country) {
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
