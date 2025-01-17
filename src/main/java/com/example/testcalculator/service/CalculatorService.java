package com.example.testcalculator.service;

import com.example.testcalculator.CalculatorFactory;
import com.example.testcalculator.ICalculator;
import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.entity.Calculation;
import com.example.testcalculator.mapper.CalculationMapper;
import com.example.testcalculator.repository.CalculatorRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CalculatorService {

    private final CalculatorRepository calculationRepository;
    private final CalculationMapper calculationMapper;

    @Transactional
    public String calculate(CalculationDTO dto) {
        Calculation calculation = calculationMapper.toEntity(dto);
        calculationRepository.save(calculation);
        String num1 = calculation.getFirstNumber();
        String num2 = calculation.getSecondNumber();
        NumeralSystem base1 = calculation.getFirstBase();
        NumeralSystem base2 = calculation.getSecondBase();
        OperationType operationType = calculation.getOperationType();
        if (base1 != base2) {
            return "Числа имеют разные системы счисления!";
        }
        ICalculator calculator = CalculatorFactory.getCalculator(base1.getBase());
        return switch (operationType) {
            case ADDITION -> calculator.add(num1, num2);
            case SUBTRACTION -> calculator.subtract(num1, num2);
            case MULTIPLICATION -> calculator.multiply(num1, num2);
            case DIVISION -> calculator.divide(num1, num2);
        };
    }

    public List<CalculationDTO> getCalculationByDatetimeBetweenAndNumeralSystemAndOperationType(LocalDateTime start, LocalDateTime end, NumeralSystem firstBase, NumeralSystem secondBase, OperationType operationType) {
        List<Calculation> calculations = calculationRepository.findAllByCalculationDatetimeBetweenAndFirstBaseAndSecondBaseAndOperationType(start, end, firstBase, secondBase, operationType);
        return calculations.stream().map(calculation -> calculationMapper.toDto(calculation)).toList();
    }

    @Transactional
    public void clearCalculationsTable() {
        calculationRepository.deleteAll();
    }

    @Transactional
    public void saveCalculations(ArrayList<Calculation> calculations) {
        calculationRepository.saveAllAndFlush(calculations);
    }

}
