package ru.otus.banknotes;

public record Banknote(Currency type, Denomination denomination) {

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
}
