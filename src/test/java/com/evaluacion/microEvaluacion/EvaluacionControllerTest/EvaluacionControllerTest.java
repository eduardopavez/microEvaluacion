package com.evaluacion.microEvaluacion.EvaluacionControllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
public class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ListarEvaluaciones() throws Exception {
        mockMvc.perform(get("/api/v1/evaluaciones"))
                .andExpect(status().isOk());
    }

    @Test
    public void GuardarEvaluacion() throws Exception {
        Map<String, Object> evaluacion = new HashMap<>();
        evaluacion.put("nombre", "Evaluación de Prueba");
        evaluacion.put("descripcion", "Descripción de prueba");

        mockMvc.perform(put("/api/v1/evaluaciones/guardarEvaluacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isCreated());
    }

}
