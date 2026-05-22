package com.icomer.icomers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icomer.icomers.model.Marca;
import com.icomer.icomers.repository.MarcaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    //listar todas las marcas
    public List<Marca> obtenerTodos() {
        log.info("SERVICE: Listando todas las marcas");
        return marcaRepository.findAll();
    }

    //guardar una marca
    public Marca guardarMarca(Marca marca) {
        log.info("SERVICE: Guardando nueva marca: {}", marca.getNombreMarca());
        return marcaRepository.save(marca);
    }

    //actualizar una marca por id
    public Marca actualizarMarca(Integer id, Marca marca) {
        log.info("SERVICE: Actualizando marca ID: {}", id);
        Marca marcaExistente = marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, La marca con ID " + id + " no existe."));
        if(marca.getNombreMarca() != null){
            log.info("SERVICE: Actualizando nombre de marca ID: {}", id);
            marcaExistente.setNombreMarca(marca.getNombreMarca());
        }
        log.info("SERVICE: Marca actualizada ID: {}", id);
        return marcaRepository.save(marcaExistente);
    }

    //eliminar una marca por id
    public String eliminarMarca(Integer id) {
        try {
            Marca marca = marcaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La marca con id " + id + " no existe."));
            log.info("SERVICE: Eliminando marca ID: {}", id);
            marcaRepository.delete(marca);
            return "La marca " + marca.getNombreMarca() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
