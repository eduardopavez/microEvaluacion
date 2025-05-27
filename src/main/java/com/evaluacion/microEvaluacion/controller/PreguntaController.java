package com.evaluacion.microEvaluacion.controller;

import jakarta.transaction.Transactional;
import com.evaluacion.microEvaluacion.model.Pregunta;
import com.evaluacion.microEvaluacion.repository.PreguntaRepository;
import com.evaluacion.microEvaluacion.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/preguntas")

public class PreguntaController {
    @Autowired
    private PreguntaService preguntaService;

    // Crear nueva pregunta
    @PostMapping("/crearPregunta")
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nueva = preguntaService.guardar(pregunta);
        return ResponseEntity.status(201).body(nueva);
    }

    // Obtener todas las preguntas
    @GetMapping("/listarPreguntas")
    public ResponseEntity<List<Pregunta>> listarPreguntas() {
        List<Pregunta> lista = preguntaService.obtenerTodas();
        return lista.isEmpty()
                ? ResponseEntity.status(404).build()
                : ResponseEntity.ok(lista);
    }

    // Obtener pregunta por ID
    @GetMapping("/obtenerPreguntaId/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Pregunta p = preguntaService.buscarPorId(id);
        if (p == null) return ResponseEntity.status(404).body("Pregunta no encontrada.");
        return ResponseEntity.ok(p);
    }

    // Obtener preguntas por evaluación
    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<Pregunta>> preguntasPorEvaluacion(@PathVariable Integer evaluacionId) {
        List<Pregunta> lista = preguntaService.obtenerPorEvaluacionId(evaluacionId);
        return lista.isEmpty()
                ? ResponseEntity.status(404).build()
                : ResponseEntity.ok(lista);
    }

    // Actualizar pregunta
    @PutMapping("/actualizarPregunta/{id}")
    public ResponseEntity<?> actualizarPregunta(@PathVariable Integer id, @RequestBody Pregunta pregunta) {
        Pregunta existente = preguntaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.status(404).body("Pregunta no encontrada.");
        
        pregunta.setId(id);
        Pregunta actualizada = preguntaService.actualizar(pregunta);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar pregunta
    @DeleteMapping("/eliminarPregunta/{id}")
    public ResponseEntity<?> eliminarPregunta(@PathVariable Integer id) {
        try {
            preguntaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Pregunta no encontrada.");
        }
    }
}