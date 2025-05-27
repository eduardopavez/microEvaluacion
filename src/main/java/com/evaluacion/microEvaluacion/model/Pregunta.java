package com.evaluacion.microEvaluacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "PREGUNTA")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor

public class Pregunta {
    @Id  // Identifica la columna primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pregunta_seq")
    @SequenceGenerator(name = "pregunta_seq", sequenceName = "PREGUNTA_SEQ", allocationSize = 1)  // Genera automáticamente el valor de la columna.
    private Integer id;  // Identificador único de la pregunta.
    
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String pregunta;  // Texto de la pregunta.
   
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String respuesta;  // Respuesta correcta a la pregunta.

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion;
    
}