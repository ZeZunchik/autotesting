package com.example.testcalculator;

import com.example.testcalculator.dto.CalculationDTO;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorFlywayTest extends AuditVizualizationBaseTest {

    @Autowired
    private MockMvc mockMvc;
    private final String BASE_PATH = "http://localhost:8080";

    @Test
    void computeTest() throws Exception {
        CalculationDTO createRequest = CalculationDTO.builder()
                .firstNumber("16")
                .firstBase(NumeralSystem.DECIMAL)
                .secondNumber("14")
                .secondBase(NumeralSystem.DECIMAL)
                .operationType(OperationType.ADDITION)
                .build();
        mockMvc.perform(post(BASE_PATH + "/compute")
                        .param("firstNumber", createRequest.getFirstNumber())
                        .param("secondNumber", createRequest.getSecondNumber())
                        .param("firstBase", createRequest.getFirstBase().toString())
                        .param("secondBase", createRequest.getSecondBase().toString())
                        .param("operationType", createRequest.getOperationType().toString()))
                .andExpect(status().isOk()).andReturn();
    }

    @FlywayTest(locationsForMigrate = "initial-data/migration")
    @Test
    void historyTest() throws Exception {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMinutes(1);
        CalculationDTO createRequest = CalculationDTO.builder()
                .firstNumber("16")
                .firstBase(NumeralSystem.DECIMAL)
                .secondNumber("14")
                .secondBase(NumeralSystem.BINARY)
                .operationType(OperationType.ADDITION)
                .build();
        mockMvc.perform(get(BASE_PATH + "/history")
                        .param("start", start.toString())
                        .param("end", end.toString())
                        .param("firstBase", createRequest.getFirstBase().toString())
                        .param("secondBase", createRequest.getSecondBase().toString())
                        .param("operationType", createRequest.getOperationType().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

}