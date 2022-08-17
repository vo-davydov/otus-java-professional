package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.banknotes.Banknote;
import ru.otus.exception.NotCorrectSumException;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.Atm.Type.OPEN;
import static ru.otus.Atm.Type.SBER;
import static ru.otus.Atm.Type.TINK;
import static ru.otus.banknotes.Country.RUB;

class AtmTest {

    private Atm sber;
    private Atm tin;
    private Atm op;

    @BeforeEach
    void setUp() {
        sber = Atm.atmOf(SBER);
        tin = Atm.atmOf(TINK);
        op = Atm.atmOf(OPEN);
    }

    @Test
    @DisplayName("Check ATM for default function")
    void atmTest() {
        List<Banknote> money = makeMoney(1_000_000);
        sber.put(money);
        tin.put(money);
        op.put(money);

        Assertions.assertThrows(NotCorrectSumException.class, () -> sber.getMoney(110));
        tin.getMoney(1_000_000);
        op.getMoney(1_000_000);

        sber.show();
    }

    private List<Banknote> makeMoney(int amount) {
        List<Banknote> banknotes = new ArrayList<>();
        for (var b : Banknote.eachOneOf(RUB)) {
            for (int i = 0; i < amount; i++) {
                banknotes.add(b.copy());
            }
        }

        return banknotes;
    }
}