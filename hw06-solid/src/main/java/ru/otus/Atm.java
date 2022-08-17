package ru.otus;

import ru.otus.actions.AtmActions;
import ru.otus.banknotes.Banknote;
import ru.otus.banknotes.Denomination;
import ru.otus.cassettes.Cassette;
import ru.otus.exception.NotCorrectSumException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static ru.otus.Atm.Type.OPEN;
import static ru.otus.Atm.Type.SBER;
import static ru.otus.Atm.Type.TINK;
import static ru.otus.banknotes.Banknote.eachOneOf;
import static ru.otus.banknotes.Country.RUB;

public class Atm implements AtmActions {

    private final Logger LOGGER = Logger.getLogger(Atm.class.getName());
    private final Map<Denomination, Cassette> cassettes;
    private final String name;

    private Atm(Builder builder) {
        this.cassettes = builder.cassettes;
        this.name = builder.name;
    }

    @Override
    public void put(List<Banknote> banknotes) {
        Map<Denomination, List<Banknote>> map = new HashMap<>();
        for (var b : banknotes) {
            if (!map.containsKey(b)) {
                map.put(b.denomination(), new ArrayList<>());
            }
            map.get(b.denomination()).add(b);
        }

        for (var b : map.entrySet()) {
            Cassette c = cassettes.get(b.getKey());
            c.put(b.getValue());
        }
    }

    @Override
    public List<Banknote> get(int amount) {
        return getMinAvailableCassette().get(amount);
    }


    private Cassette getMinAvailableCassette() {
        int min = Integer.MAX_VALUE;
        Denomination den = null;
        for (var c : cassettes.entrySet()) {
            int value = c.getValue().getDenominationValue();
            if (min > value) {
                min = value;
                den = c.getKey();
            }
        }

        return cassettes.get(den);
    }

    private int calcTotalBalance() {
        int sum = 0;
        for (var c : cassettes.entrySet()) {
            sum += c.getValue().sum();
        }
        return sum;
    }

    @Override
    public void show() {
        LOGGER.info(String.valueOf(calcTotalBalance()));
    }

    public static Atm atmOf(Type type) {
        switch (type) {
            case SBER -> {
                Builder ab = new Builder(SBER.name);
                for (Banknote b : eachOneOf(RUB)) {
                    Cassette.Builder cb = new Cassette.Builder(b);
                    cb.capacity(2_000_000);

                    ab.addCassette(cb.build());
                }

                return ab.build();
            }
            case OPEN -> {
                Builder ab = new Builder(OPEN.name);
                for (Banknote b : eachOneOf(RUB)) {
                    Cassette.Builder cb = new Cassette.Builder(b);
                    cb.capacity(1_000_000);

                    ab.addCassette(cb.build());
                }

                return ab.build();
            }
            case TINK -> {
                Builder ab = new Builder(TINK.name);
                for (Banknote b : eachOneOf(RUB)) {
                    Cassette.Builder cb = new Cassette.Builder(b);
                    cb.capacity(1_500_000);

                    ab.addCassette(cb.build());
                }

                return ab.build();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public List<Banknote> getMoney(int amount) throws NotCorrectSumException {
        int minValue = getMinAvailableCassette().getDenominationValue();
        if (amount % minValue != 0) {
            throw new NotCorrectSumException("Amount is not correct!");
        }

        return get(amount);
    }

    public enum Type {
        SBER("Sber"),
        TINK("Tink"),
        OPEN("Open");

        private final String name;

        Type(String name) {
            this.name = name;
        }
    }

    private static class Builder {
        private final String name;
        private Map<Denomination, Cassette> cassettes;

        public Builder(String name) {
            this.name = name;
        }

        public Builder addCassette(Cassette cassette) {
            if (cassettes == null) {
                this.cassettes = new HashMap<>();
            }

            cassettes.put(cassette.getDenomination(), cassette);

            return this;
        }

        public Atm build() {
            return new Atm(this);
        }
    }
}
