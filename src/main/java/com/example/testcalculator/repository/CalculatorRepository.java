package com.example.testcalculator.repository;


import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import com.example.testcalculator.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalculatorRepository extends JpaRepository<Calculation, Long> {
    List<Calculation> findAllByCalculationDatetimeBetweenAndFirstBaseAndSecondBaseAndOperationType(
            LocalDateTime start,
            LocalDateTime end,
            NumeralSystem firstBase,
            NumeralSystem secondBase,
            OperationType operationType
    );

    Integer countAllBy();
}
