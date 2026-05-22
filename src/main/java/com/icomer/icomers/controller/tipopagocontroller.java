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

import com.icomer.icomers.model.tipopago;
import com.icomer.icomers.service.tipopagoservice;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tipopago")
public class tipopagocontroller {

    @Autowired
    private tipopagoservice tipopagoservice;

    @GetMapping
    public ResponseEntity<?> listartodos(){
        log.info("CONTROLLER: Peticion para listar todos los pagos");
        List<tipopago> lista = tipopagoservice.listartodos();
        if(!lista.isEmpty()){
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(tipopagoservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardartipopago(@Valid @RequestBody tipopago objeto){
        try {
            log.info("CONTROLLER: Creando nuevo tipo de pago");
            return new ResponseEntity<>(tipopagoservice.guardartipopago(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("CONTROLLER: Error al crear: {}", e.getMessage());
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizartipopago(@PathVariable Integer id, @Valid @RequestBody tipopago objeto){
        try {
            log.info("CONTROLLER: Actualizando tipo de pago ID: {}", id);
            return new ResponseEntity<>(tipopagoservice.actualizartipopago(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al actualizar: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminartipopago(@PathVariable Integer id){
        try {
            log.info("CONTROLLER: Eliminando tipo de pago ID: {}", id);
            tipopagoservice.eliminartipopago(id);
            return new ResponseEntity<>("pago eliminado", HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("CONTROLLER: Error al eliminar: {}", e.getMessage());
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}