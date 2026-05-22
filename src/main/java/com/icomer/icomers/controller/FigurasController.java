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

import com.icomer.icomers.DTO.FigurasDTO;
import com.icomer.icomers.model.Figuras;
import com.icomer.icomers.service.FigurasService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/figuras")
public class FigurasController {

    @Autowired
    private FigurasService figurasService;
    
    //metodo para listar todas las figuras
    @GetMapping
    public ResponseEntity<List<FigurasDTO>> obtenerTodos() {
        log.info("Controler: Peticion para listar todas las figuras");
        List<FigurasDTO> figuras = figurasService.obtenerTodos();
        if (figuras.isEmpty()) {
            log.info("Controler: No se encontraron figuras");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(figuras, HttpStatus.OK);
    }

    //metodo para buscar una figura por id  
    @GetMapping("/{id}")
    public ResponseEntity<FigurasDTO> buscarPorId(@PathVariable Integer id) {
        log.info("Controler: Peticion para buscar figura con id {}", id);
        try {
            FigurasDTO figura = figurasService.buscarPorId(id);
            return new ResponseEntity<>(figura, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Controler: Figura no encontrada con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    //buscar una figura por categoria
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<FigurasDTO>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        log.info("Controler: Peticion para buscar figuras por categoria con id {}", idCategoria);
        List<FigurasDTO> figuras = figurasService.buscarPorCategoria(idCategoria);
        if (figuras.isEmpty()) {
            log.info("Controler: No se encontraron figuras para la categoria con id {}", idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Controler: Se encontraron figuras para la categoria con id {}", idCategoria);
        return new ResponseEntity<>(figuras, HttpStatus.OK);
    }

    //actualizar una figura por id
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<FigurasDTO> actualizarFigura(@PathVariable Integer id, @RequestBody FigurasDTO figura) {
        try {
            FigurasDTO figuraActualizada = figurasService.actualizarFiguras(id, figura);
            log.info("Controler: Figura actualizada con id {}", id);
            return new ResponseEntity<>(figuraActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Controler: Error al actualizar figura con id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    //eliminar una figura por id
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarFigura(@PathVariable Integer id) {
        String resultado = figurasService.eliminarFigura(id);
        if (resultado.contains("exitosamente")) {
            log.info("Controler: Figura eliminada con id {}", id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            log.error("Controler: Error al eliminar figura con id {}: {}", id, resultado);
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }

    }

    //guardar una figura
    @PostMapping
    public ResponseEntity<Figuras> guardarFigura(@RequestBody Figuras figura) {
        try {
            Figuras guardado = figurasService.guardarFiguras(figura);
            log.info("Controler: Figura guardada exitosamente");
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Controler: Error al guardar figura: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}