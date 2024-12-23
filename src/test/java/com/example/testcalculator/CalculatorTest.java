package com.example.testcalculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @ParameterizedTest
    @CsvSource({
            "2, 1010, 10, 1100",
            "8, 12, 5, 17",
            "10, 10, 20, 30",
            "16, a, 1, b"
    })
    @DisplayName("Test addition operation in various numeral systems")
    void testAddition(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.add(num1, num2));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1010, 10, 1000",
            "8, 12, 5, 5",
            "10, 20, 10, 10",
            "16, a, 1, 9"
    })
    @DisplayName("Test subtraction operation in various numeral systems")
    void testSubtraction(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.subtract(num1, num2));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 10, 11, 110",
            "8, 3, 4, 14",
            "10, 5, 6, 30",
            "16, a, 2, 14"
    })
    @DisplayName("Test multiplication operation in various numeral systems")
    void testMultiplication(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.multiply(num1, num2));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 110, 10, 11",
            "8, 14, 2, 6",
            "10, 30, 5, 6",
            "16, 14, 2, a"
    })
    @DisplayName("Test division operation in various numeral systems")
    void testDivision(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.divide(num1, num2));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 0",
            "8, 0",
            "10, 0",
            "16, 0"
    })
    @DisplayName("Test division by zero throws exception")
    void testDivisionByZero(int system, String num2) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide("1", num2);
        });
    }
}
