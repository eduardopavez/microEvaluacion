package com.evaluacion.microEvaluacion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evaluacion.microEvaluacion.model.Evaluacion;
import com.evaluacion.microEvaluacion.repository.EvaluacionRepository;
import java.util.List;

@Service
@Transactional
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public List<Evaluacion> obtenerTodas() {
        return evaluacionRepository.findAll();
    }
    public Evaluacion guardar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }
    public void eliminar(Integer id) {
        if (evaluacionRepository.existsById(id)) {
            evaluacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Evaluación no encontrada");
        }
        evaluacionRepository.deleteById(id);
    }
    public Evaluacion buscarPorId(Integer id) {
        return evaluacionRepository.findById(id).orElse(null);
    }
    public Evaluacion actualizar(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }
    public List<Evaluacion> buscarPorNombre(String nombreEvaluacion) {
        return evaluacionRepository.findByNombreEvaluacion(nombreEvaluacion);
    }

    
}