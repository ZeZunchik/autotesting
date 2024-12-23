package com.example.testcalculator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorDynamicTest {

    @TestFactory
    Collection<DynamicTest> dynamicTestsFromCsv() throws Exception {
        List<DynamicTest> dynamicTests = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dynamic-test.csv"))))) {

            String[] line;
            boolean isFirstLine = true;

            while ((line = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.length < 4) {
                    throw new IllegalArgumentException("Incorrect CSV format: " + String.join(",", line));
                }

                int system = Integer.parseInt(line[0].trim());
                String num1 = line[1].trim();
                String num2 = line[2].trim();
                String expected = line[3].trim();

                Executable testExecution = () -> {
                    ICalculator calculator = CalculatorFactory.getCalculator(system);
                    String result = calculator.add(num1, num2);
                    assertEquals(expected, result, String.format("Expected: %s, actual: %s", expected, result));
                };

                String testName = String.format("Addition Test: %s system, %s + %s = %s", system, num1, num2, expected);
                dynamicTests.add(DynamicTest.dynamicTest(testName, testExecution));
            }
        } catch (NullPointerException e) {
            throw new IllegalStateException("File test-data.csv was not founded.", e);
        } catch (CsvValidationException e) {
            throw new IllegalStateException("Error reading CSV file.", e);
        }

        return dynamicTests;
    }
}
