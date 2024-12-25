package com.example.testcalculator.bddtests;

import com.example.testcalculator.TestCalculatorApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TestCalculatorApplication.class) // Замените YourApplication на ваш главный класс приложения
public class SpringTest {
}

