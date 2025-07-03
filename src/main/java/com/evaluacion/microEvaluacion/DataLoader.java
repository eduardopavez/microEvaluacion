package com.evaluacion.microEvaluacion;

import java.util.List;
import java.util.ArrayList;

import com.evaluacion.microEvaluacion.model.Evaluacion;
import com.evaluacion.microEvaluacion.model.Pregunta;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run (String... args) throws Exception{
        Faker faker = new Faker();


        for (int i = 0; i < 3; i++) {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setNombreEvaluacion("Evaluación " + (i + 1));
            evaluacion.setDescripcion(faker.lorem().sentence());

            // Crear preguntas para cada evaluación
            List<Pregunta> preguntas = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Pregunta pregunta = new Pregunta();
                pregunta.setRespuesta(faker.lorem().sentence());
                pregunta.setEvaluacion(evaluacion);
                preguntas.add(pregunta);
            }
            evaluacion.setPreguntas(preguntas);

            entityManager.persist(evaluacion); 
        }
    }

}
