package com.evaluacion.microEvaluacion.repository;

import com.evaluacion.microEvaluacion.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer>{
    List<Pregunta> findByPregunta(String pregunta);

    // Buscar preguntas por Evaluacion id
    @Query("SELECT p FROM Pregunta p WHERE p.evaluacion.id = :evaluacionId")
    List<Pregunta> findByEvaluacionId(Integer evaluacionId);
}