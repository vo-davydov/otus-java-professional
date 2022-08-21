package ru.otus;

import ru.otus.actions.AtmActions;
import ru.otus.banknotes.Banknote;
import ru.otus.banknotes.Denomination;
import ru.otus.cassettes.Cassette;
import ru.otus.cassettes.ClusterCassette;
import ru.otus.exception.NotCorrectSumException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Atm implements AtmActions {

    private final Logger LOGGER = Logger.getLogger(Atm.class.getName());
    private final ClusterCassette cluster;
    private final String name;

    private final Type type;

    private Atm(Builder builder) {
        this.cluster = builder.cluster;
        this.type = builder.type;
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
            Cassette c = cluster.get(b.getKey());
            c.put(b.getValue());
        }
    }

    @Override
    public List<Banknote> get(int amount) {
        return getMinAvailableCassette().get(amount);
    }


    private Cassette getMinAvailableCassette() {
        return cluster.getMinCassette();
    }

    private int calcTotalBalance() {
        int sum = 0;
        for (var c : cluster.getEntry()) {
            sum += c.getValue().sum();
        }
        return sum;
    }

    @Override
    public void show() {
        LOGGER.info(String.valueOf(calcTotalBalance()));
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

    public static class Builder {

        private String name;
        private Type type;
        private ClusterCassette cluster = new ClusterCassette();

        public Builder(String name) {
            this.name = name;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder addCassette(Cassette cassette) {
            cluster.put(cassette);
            return this;
        }

        public Atm build() {
            return new Atm(this);
        }
    }
}
