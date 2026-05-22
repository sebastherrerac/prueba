package com.icomer.icomers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icomer.icomers.model.region;
import com.icomer.icomers.service.regionservice;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/region")
public class regioncontroller {

    @Autowired
    private regionservice regionservice;

    @GetMapping
    public ResponseEntity<?> listartodos(){
        log.info("CONTROLLER: Peticion para listar todas las regiones");
        List<region> lista = regionservice.listartodos();
        if(!lista.isEmpty()){
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(regionservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarregion(@Valid @RequestBody region objeto){
        try {
            log.info("CONTROLLER: Creando nueva region");
            return new ResponseEntity<>(regionservice.guardarregion(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("CONTROLLER: Error al crear region: {}", e.getMessage());
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarregion(@PathVariable Integer id, @Valid @RequestBody region objeto){
        try {
            log.info("CONTROLLER: Actualizando region ID: {}", id);
            return new ResponseEntity<>(regionservice.actualizarregion(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al actualizar region: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarregion(@PathVariable Integer id){
        try {
            log.info("CONTROLLER: Eliminando region ID: {}", id);
            regionservice.eliminarregion(id);
            return new ResponseEntity<>("region eliminada", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al eliminar region: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}