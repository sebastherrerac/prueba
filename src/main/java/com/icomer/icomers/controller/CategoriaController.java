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

import com.icomer.icomers.model.Categoria;
import com.icomer.icomers.service.CategoriaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    //metodo para listar todas las categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodos() {
        log.info("Controler: Peticion para listar todas las categorias");
        List<Categoria> categorias = categoriaService.obtenerTodos();
        if (categorias.isEmpty()) {
            log.info("Controler: No se encontraron categorias");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Controler: Categorias encontradas");
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
    
    //metodo para guardar una categoria
    @PostMapping
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria guardada = categoriaService.guardarCategoria(categoria);
            log.info("Controler: Categoria agregada exitosamente");
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Controler: Error al agregar categoria: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //metodo para actualizar una categoria por id
    @PutMapping("{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        try {
            Categoria actualizada = categoriaService.actualizarCategoria(id, categoria);
            log.info("Controler: Categoria actualizada exitosamente");
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Controler: Error al actualizar categoria: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //metodo para eliminar una categoria por id
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        String resultado = categoriaService.eliminarPorId(id);
        if (resultado.contains("exitosamente")) {
            log.info("Controler: Categoria eliminada exitosamente");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.error("Controler: Error al eliminar categoria: {}", resultado);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
