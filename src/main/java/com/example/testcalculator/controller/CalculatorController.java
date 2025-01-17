package com.example.testcalculator.controller;

import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/compute")
    public ResponseEntity<String> compute(
            @RequestBody CalculationDTO dto) {

        String res = calculatorService.calculate(dto);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CalculationDTO>> getHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam NumeralSystem firstBase,
            @RequestParam NumeralSystem secondBase,
            @RequestParam OperationType operationType) {

        List<CalculationDTO> calculations = calculatorService
                .getCalculationByDatetimeBetweenAndNumeralSystemAndOperationType(
                        start,
                        end,
                        firstBase,
                        secondBase,
                        operationType
                );

        return ResponseEntity.ok(calculations);
    }
}
