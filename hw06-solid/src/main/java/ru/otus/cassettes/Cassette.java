package ru.otus.cassettes;

import ru.otus.actions.ActionWithBanknote;
import ru.otus.banknotes.Banknote;
import ru.otus.banknotes.Denomination;
import ru.otus.exception.WrongBanknoteException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Cassette implements ActionWithBanknote {
    private final Deque<Banknote> money;
    private final Banknote type;

    private Cassette(Builder builder) {
        this.money = builder.money;
        this.type = builder.type;
    }

    public Denomination getDenomination() {
        return type.denomination();
    }

    @Override
    public void put(List<Banknote> banknotes) {
        for (var b : banknotes) {
            validateBanknotes(b);
            money.addLast(b);
        }
    }

    private void validateBanknotes(Banknote banknote) {
        if (!banknote.equals(this.type)) {
            throw new WrongBanknoteException(banknote.denomination() + " is not correct for this cassette");
        }
    }

    @Override
    public List<Banknote> get(int amount) {
        List<Banknote> result = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            result.add(money.peekFirst());
        }

        return result;
    }

    public int getDenominationValue() {
        return type.denomination().getValue();
    }

    public int sum() {
        return money.stream()
                .mapToInt(b -> b.denomination().getValue())
                .sum();
    }


    public static class Builder {
        private Deque<Banknote> money;
        private final Banknote type;

        public Builder(Banknote type) {
            this.type = type;
        }

        public Builder capacity(int capacity) {
            this.money = new ArrayDeque<>(capacity);
            return this;
        }

        public Cassette build() {
            return new Cassette(this);
        }

    }
}
