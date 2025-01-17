package com.example.testcalculator.mapper;

import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.entity.Calculation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CalculationMapper {
    CalculationDTO toDto(Calculation user);

    @Mapping(target = "calculationDatetime", expression = "java(java.time.LocalDateTime.now())")
    Calculation toEntity(CalculationDTO userDto);
}