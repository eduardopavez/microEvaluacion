package com.evaluacion.microEvaluacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import com.evaluacion.microEvaluacion.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
       List<Evaluacion> findByNombreEvaluacion(String nombreEvaluacion);

        // Buscar evaluaciones por duración mayor a un valor dado
        @Query("SELECT e FROM Evaluacion e WHERE e.duracion > :minDuracion")
        List<Evaluacion> findEvaluacionesDuracionMayorQue(Integer minDuracion);
}
    
