package com.example.testcalculator.mapper;

import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.entity.Calculation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculationMapper {
    CalculationDTO toDto(Calculation user);

    Calculation toEntity(Calculation userDto);
}