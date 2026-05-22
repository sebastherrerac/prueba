package com.icomer.icomers.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.icomer.icomers.model.comuna;
import com.icomer.icomers.service.comunaservice;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/comuna")
public class comunacontroller {

    @Autowired
    private comunaservice comunaservice;

    @GetMapping
    public ResponseEntity<?> listartodos(){
        log.info("CONTROLLER: Peticion para listar todas las comunas");
        List<comuna> lista = comunaservice.listartodos();
        if(!lista.isEmpty()){
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(comunaservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarcomuna(@Valid @RequestBody comuna objeto){
        try {
            log.info("CONTROLLER: Creando nueva comuna");
            return new ResponseEntity<>(comunaservice.guardarcomuna(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("CONTROLLER: Error al crear comuna: {}", e.getMessage());
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarcomuna(@PathVariable Integer id, @Valid @RequestBody comuna objeto){
        try {
            log.info("CONTROLLER: Actualizando comuna ID: {}", id);
            return new ResponseEntity<>(comunaservice.actualizarcomuna(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al actualizar comuna: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarcomuna(@PathVariable Integer id){
        try {
            log.info("CONTROLLER: Eliminando comuna ID: {}", id);
            comunaservice.eliminarcomuna(id);
            return new ResponseEntity<>("comuna eliminada", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al eliminar comuna: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}