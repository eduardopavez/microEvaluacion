package com.evaluacion.microEvaluacion.controller;
import com.evaluacion.microEvaluacion.model.Evaluacion;
import com.evaluacion.microEvaluacion.service.EvaluacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/evaluaciones")

public class EvaluacionController {

    @Autowired EvaluacionService evaluacionService;
    @GetMapping
    public ResponseEntity<?> listar() {
        List<String> evaluaciones = List.of(
            "GET       /api/v1/evaluaciones/buscarEvaluacion/{id}",
            "GET       /api/v1/evaluaciones/mostrarEvaluacion", 
            "POST      /api/v1/evaluaciones/guardarEvaluacion", 
            "DELETE    /api/v1/evaluaciones/eliminarEvaluacion/{id}",
            "PUT       /api/v1/evaluaciones/actualizarEvaluacion/{id}");

        return ResponseEntity.ok(evaluaciones);
    }
    @PostMapping("/guardarEvaluacion")
    public ResponseEntity<?> guardarEvaluacion(@RequestBody Evaluacion evaluacion) {
        Evaluacion evaluacionNueva = evaluacionService.guardar(evaluacion);
        return ResponseEntity.status(201).body(evaluacionNueva);
    }
    // Buscar Evaluacion por ID
    @GetMapping("/buscarEvaluacion/{id}")
    public ResponseEntity<?> buscarEvaluacion(@PathVariable Integer id) {
        Evaluacion evaluacion = evaluacionService.buscarPorId(id);
        if (evaluacion == null) {
            return ResponseEntity.badRequest().body("Evaluación no encontrada.");
        }
        return ResponseEntity.ok(evaluacion);
    }

    // Eliminar Evaluacion
    @DeleteMapping("/eliminarEvaluacion/{id}")
    public ResponseEntity<?> eliminarEvaluacion(@PathVariable Integer id) {
        try {
            evaluacionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Evaluación no encontrada.");
        }
    }
    // Mostrar Evaluaciones
    @GetMapping("/mostrarEvaluacion")
    public ResponseEntity<List<Evaluacion>> mostrarEvaluacion() {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(evaluaciones);
        
       }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Evaluacion evaluacion) {
        Evaluacion existente = evaluacionService.buscarPorId(id);
        if (existente == null) return ResponseEntity.status(404).body("Evaluación no encontrada.");

        evaluacion.setId(id); 
        Evaluacion actualizada = evaluacionService.actualizar(evaluacion);
        return ResponseEntity.ok(actualizada);
    }
        
}