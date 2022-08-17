package ru.otus.exception;

public class WrongBanknoteException extends RuntimeException {
    public WrongBanknoteException(String msg) {
        super(msg);
    }
}
