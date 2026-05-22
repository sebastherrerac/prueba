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

import com.icomer.icomers.model.Marca;
import com.icomer.icomers.service.MarcaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    //listar marcas
    @GetMapping
    public ResponseEntity<List<Marca>> obtenerTodas() {
        log.info("Controler: Peticion para listar todas las marcas");
        List<Marca> marcas = marcaService.obtenerTodos();
        if (marcas.isEmpty()) {
            log.info("Controler: No se encontraron marcas");
            return ResponseEntity.noContent().build();
        }
        log.info("Controler: Marcas encontradas");
        return ResponseEntity.ok(marcas);
    }

    //guardar marca
    @PostMapping
    public ResponseEntity<Marca> agregarMarca(@RequestBody Marca marca) {
        try {
            Marca guardada = marcaService.guardarMarca(marca);
            log.info("Controler: Marca agregada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (Exception e) {
            log.error("Controler: Error al agregar marca: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    //actualizar marca por id
    @PutMapping("{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            Marca actualizada = marcaService.actualizarMarca(id, marca);
            log.info("Controler: Marca actualizada exitosamente");
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            log.error("Controler: Error al actualizar marca: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    //eliminar marca por id
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Integer id) {
        String resultado = marcaService.eliminarMarca(id);
        if (resultado.equals("Marca eliminada exitosamente")) {
            log.info("Controler: Marca eliminada exitosamente");
            return ResponseEntity.ok().build();
        } else {
            log.error("Controler: Error al eliminar marca: {}", resultado);
            return ResponseEntity.notFound().build();
        }
    }
}
