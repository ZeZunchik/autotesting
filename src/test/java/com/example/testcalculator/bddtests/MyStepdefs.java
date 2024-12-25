package com.example.testcalculator.bddtests;

import com.example.testcalculator.NumeralSystem;
import com.example.testcalculator.OperationType;
import com.example.testcalculator.dto.CalculationDTO;
import com.example.testcalculator.entity.Calculation;
import com.example.testcalculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MyStepdefs {

    private final CalculatorService calculationService;
    private final ObjectMapper mapper;

    private final int PORT = 8080;

    private ResponseEntity<CalculationDTO[]> getCalculationsResponse;
    private ResponseEntity<String> addCalculationResponse;

    @Дано("таблица calculations содержит")
    public void таблица_calculations_содержит(DataTable dataTable) {
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

        var calculations = new ArrayList<Calculation>();
        for (Map<String, String> row : table) {
            var calculation = new Calculation();
            calculation.setFirstNumber(row.get("first_number"));
            calculation.setFirstBase(NumeralSystem.valueOf(row.get("first_base")));
            calculation.setSecondNumber(row.get("second_number"));
            calculation.setSecondBase(NumeralSystem.valueOf(row.get("second_base")));
            calculation.setOperationType(OperationType.valueOf(row.get("operation_type")));
            calculation.setCalculationDatetime(LocalDateTime.parse(row.get("calculation_datetime")));

            calculations.add(calculation);
        }
        calculationService.saveCalculations(calculations);
    }

    @After
    public void clearDb() {
        calculationService.clearCalculationsTable();
    }

    /////////////////////////////////////////////////////

    // Успешное получение вычислений

    @Когда("запрашиваем вычисления с параметрами")
    public void запрашиваем_вычисления_с_параметрами(Map<String, String> queryParams) {

        String str = "http://localhost:" + PORT + "/history"
                + "?start=" + queryParams.get("start")
                + "&end=" + queryParams.get("end")
                + "&firstBase=" + queryParams.get("firstBase")
                + "&secondBase=" + queryParams.get("secondBase")
                + "&operationType=" + queryParams.get("operationType");
        getCalculationsResponse = new RestTemplate().exchange(
                "http://localhost:" + PORT + "/history"
                        + "?start=" + queryParams.get("start")
                        + "&end=" + queryParams.get("end")
                        + "&firstBase=" + queryParams.get("firstBase")
                        + "&secondBase=" + queryParams.get("secondBase")
                        + "&operationType=" + queryParams.get("operationType"),
                HttpMethod.GET,
                null,
                CalculationDTO[].class);
    }

    @Тогда("ответ получения вычислений")
    public void ответ(String responseJson) throws Exception {
        assertThat(mapper.readValue(responseJson, Calculation[].class))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(getCalculationsResponse.getBody());
        /*Assertions.assertArrayEquals(
                mapper.readValue(responseJson, Calculation[].class),
                getCalculationsResponse.getBody());*/
    }

    // Успешное получение вычислений (дата)

    @ParameterType("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")
    public LocalDateTime myDatetime(String s) {
        return LocalDateTime.parse(s);
    }

    @Когда("запрашиваем вычисления с параметрами \\(дата) {myDatetime}, {myDatetime}")
    public void запрашиваем_вычисления_с_параметрами_дата(LocalDateTime start, LocalDateTime end, Map<String, String> queryParams) {
        getCalculationsResponse = new RestTemplate().exchange(
                "http://localhost:" + PORT + "/history"
                        + "?start=" + start
                        + "&end=" + end
                        + "&firstBase=" + queryParams.get("firstBase")
                        + "&secondBase=" + queryParams.get("secondBase")
                        + "&operationType=" + queryParams.get("operationType"),
                HttpMethod.GET,
                null,
                CalculationDTO[].class);
    }

    @Тогда("ответ получения вычислений \\(дата)")
    public void ответ_получения_вычислений_дата(String responseJson) throws Exception {
        /*Assertions.assertArrayEquals(
                mapper.readValue(responseJson, Calculation[].class),
                getCalculationsResponse.getBody());*/

        assertThat(mapper.readValue(responseJson, Calculation[].class))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(getCalculationsResponse.getBody());
    }

    // Успешное добавление вычисления

    @Когда("добавляем вычисление")
    public void добавляем_вычисление(String requestJson) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<String>(requestJson, headers);

        addCalculationResponse = new RestTemplate().exchange(
                "http://localhost:" + PORT + "/compute",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }


    @Тогда("ответ добавления вычисления {string}")
    public void ответ_добавления_вычисления(String response) {
        Assertions.assertEquals(response, addCalculationResponse.getBody());
    }

    // Успешное добавление вычисления (разделитель)

    @ParameterType(".+")
    public String[] myStringArr(String s) {
        return s.split(" & ");
    }

    @Когда("добавляем вычисление \\(разделитель) {myStringArr}")
    public void добавляем_вычисление_разделитель(String[] list) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CalculationDTO request = new CalculationDTO();
        request.setFirstNumber(list[0]);
        request.setSecondNumber(list[1]);
        request.setFirstBase(NumeralSystem.valueOf(list[2]));
        request.setSecondBase(NumeralSystem.valueOf(list[3]));
        request.setOperationType(OperationType.valueOf(list[4]));

        var requestEntity = new HttpEntity<CalculationDTO>(request, headers);

        addCalculationResponse = new RestTemplate().exchange(
                "http://localhost:" + PORT + "/compute",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Тогда("ответ добавления вычисления \\(разделитель) {string}")
    public void ответ_добавления_вычисления_разделитель(String response) {
        Assertions.assertEquals(response, addCalculationResponse.getBody());
    }

    // Успешное добавление вычисления (класс)

    @ParameterType(".+")
    public CalculationDTO addCalculationRequest(String s) {
        var params = s.split(" & ");

        CalculationDTO request = new CalculationDTO();
        request.setFirstNumber(params[0]);
        request.setSecondNumber(params[1]);
        request.setFirstBase(NumeralSystem.valueOf(params[2]));
        request.setSecondBase(NumeralSystem.valueOf(params[3]));
        request.setOperationType(OperationType.valueOf(params[4]));

        return request;
    }

    @Когда("добавляем вычисление \\(класс) {addCalculationRequest}")
    public void добавляем_вычисление_класс(CalculationDTO request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<CalculationDTO>(request, headers);

        addCalculationResponse = new RestTemplate().exchange(
                "http://localhost:" + PORT + "/compute",
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    @Тогда("ответ добавления вычисления \\(класс) {string}")
    public void ответ_добавления_вычисления_класс(String response) {
        Assertions.assertEquals(response, addCalculationResponse.getBody());
    }
}
