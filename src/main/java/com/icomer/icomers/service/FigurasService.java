package com.icomer.icomers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icomer.icomers.DTO.FigurasDTO;
import com.icomer.icomers.model.Figuras;
import com.icomer.icomers.repository.FigurasRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class FigurasService {
    
    @Autowired
    private FigurasRepository figurasRepository;

    //metodo para listar todas las figuras
    public List<FigurasDTO> obtenerTodos() {
        log.info("SERVICE: Listando todas las figuras");
        return figurasRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    //metodo para guardar una figura
    public Figuras guardarFiguras(Figuras figura) {
        log.info("SERVICE: Guardando nueva figura: {}", figura.getNombre());
        return figurasRepository.save(figura);
    }

    //metodo para eliminar una figura id
    public String eliminarFigura(Integer id) {
        log.info("SERVICE: Eliminando figura ID: {}", id);
        try {
            log.info("SERVICE: Buscando figura ID: {}", id);
            Figuras figura = figurasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La figura con ID " + id + " no existe."));
            figurasRepository.delete(figura);
            log.info("SERVICE: Figura eliminada exitosamente: {}", figura.getNombre());
            return "La figura " + figura.getNombre() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            log.error("SERVICE: Error al eliminar figura: {}", e.getMessage());
            return e.getMessage();
        }
    }
    //metodo para actualizar una figura id
    public FigurasDTO actualizarFiguras(Integer id, FigurasDTO figura) {
        log.info("SERVICE: Actualizando figura ID: {}", id);
        Figuras figuraExistente = figurasRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, La figura con ID " + id + " no existe."));
        if(figura.getNombre() != null){
            figuraExistente.setNombre(figura.getNombre());
            log.info("SERVICE: Actualizando nombre de figura ID: {}", id);
        }
        if(figura.getPrecio() != null){
            figuraExistente.setPrecio(figura.getPrecio());
            log.info("SERVICE: Actualizando precio de figura ID: {}", id);
        }
        if(figura.getStock() != null){
            figuraExistente.setStock(figura.getStock());
            log.info("SERVICE: Actualizando stock de figura ID: {}", id);
        }
        if(figura.getCategoria() != null){
            figuraExistente.setCategoria(figura.getCategoria());
            log.info("SERVICE: Actualizando categoria de figura ID: {}", id);
        }
        return convertirADTO(figurasRepository.save(figuraExistente));  
    }

    //metodo para convertir una figura a DTO
    public FigurasDTO convertirADTO(Figuras figura) {
        log.info("SERVICE: Convirtiendo figura a DTO ID: {}", figura.getIdFigura());
        FigurasDTO dto = new FigurasDTO();
        dto.setIdFigura(figura.getIdFigura());
        dto.setNombre(figura.getNombre());
        dto.setPrecio(figura.getPrecio());
        dto.setCategoria(figura.getCategoria());
        dto.setDescripcion(figura.getDescripcion());
        return dto;
    }

    //metodo para buscar una figura por id
    public FigurasDTO buscarPorId(Integer id) {
        log.info("SERVICE: Buscando figura por ID: {}", id);
        Figuras figura = figurasRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, La figura con ID " + id + " no existe."));
        return convertirADTO(figura);
    }

    //metodo para buscar figuras por categoria
    public List<FigurasDTO> buscarPorCategoria(Integer idCategoria) {
        log.info("SERVICE: Buscando figuras por categoria ID: {}", idCategoria);
        return figurasRepository.findByCategoria_IdCategoria(idCategoria).stream().map(this::convertirADTO).toList();
    }

}
