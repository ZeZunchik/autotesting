package com.example.testcalculator;

public enum NumeralSystem {
    BINARY(2),
    OCTAL(8),
    DECIMAL(10),
    HEXADECIMAL(16);

    private final int base;

    NumeralSystem(int base) {
        this.base = base;
    }

    public int getBase() {
        return base;
    }
}
