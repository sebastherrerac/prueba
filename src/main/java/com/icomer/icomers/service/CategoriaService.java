package com.icomer.icomers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icomer.icomers.model.Categoria;
import com.icomer.icomers.repository.CategoriaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //listar todas las categorias
    public List<Categoria> obtenerTodos() {
        log.info("SERVICE: Listando todas las categorias");
        return categoriaRepository.findAll();
    }
    
    //guardar una categoria
    public Categoria guardarCategoria(Categoria categoria) {
        log.info("SERVICE: Guardando nueva categoria: {}", categoria.getNombreCategoria());
        return categoriaRepository.save(categoria);
    }

    //actualizar una categoria id
    public Categoria actualizarCategoria(Integer id, Categoria categoria) {
        log.info("SERVICE: Actualizando categoria ID: {}", id);
        Categoria categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, La categoria con ID " + id + " no existe."));
        if(categoria.getNombreCategoria() != null){
            log.info("SERVICE: Actualizando nombre de categoria ID: {}", id);
            categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        }
        log.info("SERVICE: Categoria actualizada ID: {}", id);
        return categoriaRepository.save(categoriaExistente);
    }

    //eliminar una categoria
    public String eliminarPorId(Integer id) {
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La categoria con id " + id + " no existe."));
            log.info("SERVICE: Eliminando categoria ID: {}", id);
            categoriaRepository.delete(categoria);
            return "La categoria " + categoria.getNombreCategoria() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
