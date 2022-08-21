package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.banknotes.Banknote;
import ru.otus.banknotes.Currency;
import ru.otus.cassettes.Cassette;
import ru.otus.exception.NotCorrectSumException;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.Atm.Type.SBER;
import static ru.otus.banknotes.Currency.RUB;

class AtmTest {

    private Atm sber;

    @BeforeEach
    void setUp() {
        Atm.Builder ab = new Atm.Builder("1").type(SBER);

        for (Banknote b : Currency.eachOneOf(RUB)) {
            Cassette.Builder cb = new Cassette.Builder(b);
            cb.capacity(1_500_000);

            ab.addCassette(cb.build());
        }
        sber = ab.build();
    }

    @Test
    @DisplayName("Check ATM for default function")
    void atmTest() {
        List<Banknote> money = makeMoney(1_000_000);
        sber.put(money);

        Assertions.assertThrows(NotCorrectSumException.class, () -> sber.getMoney(110));

        sber.show();
    }

    private List<Banknote> makeMoney(int amount) {
        List<Banknote> banknotes = new ArrayList<>();
        for (var b : Currency.eachOneOf(RUB)) {
            for (int i = 0; i < amount; i++) {
                banknotes.add(b.copy());
            }
        }

        return banknotes;
    }
}