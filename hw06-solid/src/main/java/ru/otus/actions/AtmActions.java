package ru.otus.actions;

import ru.otus.banknotes.Banknote;
import ru.otus.exception.NotCorrectSumException;

import java.util.List;

public interface AtmActions extends ActionWithBanknote, ActionShow {

    List<Banknote> getMoney(int amount) throws NotCorrectSumException;
}
