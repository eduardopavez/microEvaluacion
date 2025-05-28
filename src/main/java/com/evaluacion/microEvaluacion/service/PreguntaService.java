package com.evaluacion.microEvaluacion.service;

import com.evaluacion.microEvaluacion.repository.PreguntaRepository;
import com.evaluacion.microEvaluacion.model.Pregunta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional

public class PreguntaService {
    @Autowired
    private PreguntaRepository preguntaRepository; 

    // Obtener preguntas por Evaluacion ID
    public List<Pregunta> obtenerPorEvaluacionId(Integer evaluacionId) {
        return preguntaRepository.findByEvaluacionId(evaluacionId);
    }

    // Buscar pregunta por ID
    public Pregunta buscarPorId(Integer id) {
        return preguntaRepository.findById(id).orElse(null);
    }

    // Guardar nueva pregunta
    public Pregunta guardar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    // Actualizar pregunta existente
    public Pregunta actualizar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    // Obtener todas las preguntas
    public List<Pregunta> obtenerTodas() {
        return preguntaRepository.findAll();
    }

    // Eliminar pregunta por ID
    public void eliminar(Integer id) {
        preguntaRepository.deleteById(id);
    }
    
}