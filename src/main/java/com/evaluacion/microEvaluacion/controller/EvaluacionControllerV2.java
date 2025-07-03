package com.evaluacion.microEvaluacion.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.microEvaluacion.Assemblers.EvaluacionModelAssembler;
import com.evaluacion.microEvaluacion.model.Evaluacion;
import com.evaluacion.microEvaluacion.repository.EvaluacionRepository;
import com.evaluacion.microEvaluacion.service.EvaluacionService;

@RestController
@RequestMapping("api/v2/evaluacion")
public class EvaluacionControllerV2 {

    @Autowired
    EvaluacionService evaluacionService;

    @Autowired
    EvaluacionModelAssembler assembler;

    @Autowired
    EvaluacionRepository evaluacionRepository;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Evaluacion>> listarEvaluaciones(){
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionRepository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(evaluaciones,
            linkTo(methodOn(EvaluacionControllerV2.class).listarEvaluaciones()).withSelfRel());
    }

    @GetMapping(value = "/buscarPorid/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Evaluacion> buscarPorId(@PathVariable int id){
        Evaluacion evaluacion = evaluacionService.buscarPorId(id);
        return assembler.toModel(evaluacion);
    }

    @GetMapping(value = "/buscarPorNombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Evaluacion>> buscarPorNombre(@PathVariable String nombre){
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionService.buscarPorNombre(nombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(evaluaciones,
            linkTo(methodOn(EvaluacionControllerV2.class).buscarPorNombre(nombre)).withSelfRel());
    }


}
