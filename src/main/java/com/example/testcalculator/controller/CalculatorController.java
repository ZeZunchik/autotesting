package com.example.testcalculator.controller;

import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.entity.Calculation;
import com.example.testcalculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/compute")
    public ResponseEntity<String> compute(
            @RequestParam String firstNumber,
            @RequestParam NumeralSystem firstBase,
            @RequestParam String secondNumber,
            @RequestParam NumeralSystem secondBase,
            @RequestParam OperationType operationType) {

        String res = calculatorService.calculate(firstNumber, secondNumber, firstBase, secondBase, operationType);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CalculationDTO>> getHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam NumeralSystem firstBase,
            @RequestParam NumeralSystem secondBase,
            @RequestParam OperationType operationType) {

        List<CalculationDTO> calculations = calculatorService.getCalculationByDatetimeBetweenAndNumeralSystemAndOperationType(start, end, firstBase, secondBase, operationType);

        return ResponseEntity.ok(calculations);
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuantity() {

        Integer quantity = calculatorService.getCountBy();
        System.out.println(quantity);
        return ResponseEntity.ok(quantity);
    }
}
