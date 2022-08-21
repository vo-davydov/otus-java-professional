package ru.otus.cassettes;

import ru.otus.banknotes.Denomination;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ClusterCassette {
    private final TreeMap<Denomination, Cassette> cassettes;

    public ClusterCassette() {
        this.cassettes = new TreeMap<>(Comparator.comparingInt(Denomination::getValue));
    }

    public void put(Cassette cassette) {
        cassettes.put(cassette.getDenomination(), cassette);
    }

    public Cassette get(Denomination denomination) {
        return cassettes.get(denomination);
    }


    public Set<Map.Entry<Denomination, Cassette>> getEntry() {
        return this.cassettes.entrySet();
    }

    public Cassette getMinCassette() {
        return cassettes.firstEntry().getValue();
    }

}
