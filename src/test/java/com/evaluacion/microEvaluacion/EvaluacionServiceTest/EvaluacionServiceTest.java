package com.evaluacion.microEvaluacion.EvaluacionServiceTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.evaluacion.microEvaluacion.service.EvaluacionService;
import com.evaluacion.microEvaluacion.model.Evaluacion;

@SpringBootTest
public class EvaluacionServiceTest {

    @Autowired
    private EvaluacionService evaluacionService;

    @Test
    public void GuardarEvaluacion() {
        Evaluacion evaluacionTest = new Evaluacion();
        evaluacionTest.setNombreEvaluacion("Evaluación de Prueba");
        evaluacionTest.setDescripcion("Descripción de prueba");

        Evaluacion savedEvaluacion = evaluacionService.guardar(evaluacionTest);
        assertTrue(savedEvaluacion.getId() != null, "La evaluación debe ser guardada con un ID válido");
    }

    @Test
    public void EliminarEvaluacion() {
        Evaluacion evaluacionTest = new Evaluacion();
        evaluacionTest.setNombreEvaluacion("Evaluación a Eliminar");
        evaluacionTest.setDescripcion("Descripción de eliminación");

        Evaluacion savedEvaluacion = evaluacionService.guardar(evaluacionTest);
        Integer id = savedEvaluacion.getId();

        evaluacionService.eliminar(id);
        assertFalse(evaluacionService.buscarPorId(id) != null, "La evaluación debe ser eliminada correctamente");
    }
}
