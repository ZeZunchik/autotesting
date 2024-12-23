package com.example.testcalculator.entity;

import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="calculation", schema = "calculator")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_number", nullable = false)
    private String firstNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "first_base", nullable = false)
    private NumeralSystem firstBase;

    @Column(name = "second_number", nullable = false)
    private String secondNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "second_base", nullable = false)
    private NumeralSystem secondBase;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @Column(name = "calculation_datetime", nullable = false)
    private LocalDateTime calculationDatetime = LocalDateTime.now();

}