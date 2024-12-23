package com.example.testcalculator;

public class CalculatorFactory {
    public static ICalculator getCalculator(int system) {
        return switch (system) {
            case 2 -> new BinaryCalculator();
            case 8 ->  new OctalCalculator();
            case 10 -> new DecimalCalculator();
            case 16 -> new HexCalculator();
            default -> throw new IllegalArgumentException("Unknown number system: " + system);
        };
    }
}