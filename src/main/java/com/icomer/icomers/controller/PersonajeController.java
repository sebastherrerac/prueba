package com.icomer.icomers.controller;

import com.icomer.icomers.model.Personaje;
import com.icomer.icomers.service.PersonajeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping
    public List<Personaje> obtenerTodos() {
        log.info("Controler: Peticion para listar todos los personajes");
        return personajeService.listarTodos();
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Personaje> buscarPorNombre(@PathVariable String nombre) {
        log.info("Controler: Peticion para buscar personaje por nombre: {}", nombre);
        return ResponseEntity.ok(personajeService.buscarPorNombre(nombre));
    }


    @PostMapping
    public Personaje guardar(@RequestBody Personaje personaje) {
        log.info("Controler: Peticion para guardar un personaje");
        return personajeService.guardar(personaje);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizar(@PathVariable Long id, @RequestBody Personaje personajeDetalles) {
        log.info("Controler: Peticion para actualizar el personaje con id {}", id);
        return ResponseEntity.ok(personajeService.actualizar(id, personajeDetalles));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Controler: Peticion para eliminar el personaje con id {}", id);
        personajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}