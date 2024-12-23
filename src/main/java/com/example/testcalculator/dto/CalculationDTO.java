package com.example.testcalculator.dto;

import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import lombok.*;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CalculationDTO {
    private Long id;
    private String firstNumber;
    private NumeralSystem firstBase;
    private String secondNumber;
    private NumeralSystem secondBase;
    private OperationType operationType;
    private LocalDateTime calculationDatetime;
}