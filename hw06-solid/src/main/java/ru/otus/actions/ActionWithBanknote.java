package ru.otus.actions;

import ru.otus.banknotes.Banknote;

import java.util.List;

public interface ActionWithBanknote {

    void put(List<Banknote> banknotes);

    List<Banknote> get(int amount);
}
