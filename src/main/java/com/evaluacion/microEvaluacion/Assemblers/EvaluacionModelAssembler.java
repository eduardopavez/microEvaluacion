package com.evaluacion.microEvaluacion.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.evaluacion.microEvaluacion.controller.EvaluacionControllerV2;
import com.evaluacion.microEvaluacion.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
            linkTo(methodOn(EvaluacionControllerV2.class).listarEvaluaciones()).withRel("evaluaciones"),
            linkTo(methodOn(EvaluacionControllerV2.class).listarEvaluaciones()).withSelfRel()
        
        );
    }
}
