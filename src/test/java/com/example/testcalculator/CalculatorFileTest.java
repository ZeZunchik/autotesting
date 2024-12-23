package com.example.testcalculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorFileTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/addition.csv")
    @DisplayName("Test addition operation from CSV file")
    void testAdditionFromCsv(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.add(num1, num2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/subtraction.csv", numLinesToSkip = 1)
    @DisplayName("Test subtraction operation from CSV file")
    void testSubtractionFromCsv(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.subtract(num1, num2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/multiplication.csv", numLinesToSkip = 1)
    @DisplayName("Test multiplication operation from CSV file")
    void testMultiplicationFromCsv(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.multiply(num1, num2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/division.csv", numLinesToSkip = 1)
    @DisplayName("Test division operation from CSV file")
    void testDivisionFromCsv(int system, String num1, String num2, String expected) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertEquals(expected, calculator.divide(num1, num2));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/division_by_zero.csv", numLinesToSkip = 1)
    @DisplayName("Test subtraction operation from CSV file")
    void testDivisionByZeroFromCsv(int system, String num2) {
        ICalculator calculator = CalculatorFactory.getCalculator(system);
        assertThrows(ArithmeticException.class, () -> calculator.divide("1", num2));
    }
}
