package com.evaluacion.microEvaluacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "EVALUACION")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor // Constructor, getters y setters generados automáticamente por Lombok
public class Evaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_seq")
    @SequenceGenerator(name = "evaluacion_seq", sequenceName = "EVALUACION_SEQ", allocationSize = 1)
    private Integer id;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String nombreEvaluacion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String descripcion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private Integer duracion; // Duración en minutos

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("evaluacion")  // Para evitar loops recursivos
    @JsonIgnore  // Ignora esta propiedad al serializar a JSON
    private List<Pregunta> preguntas; // Relación uno a muchos con Pregunta

}

