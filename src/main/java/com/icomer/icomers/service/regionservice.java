package com.icomer.icomers.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icomer.icomers.model.region;
import com.icomer.icomers.repository.regionrepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class regionservice {

    @Autowired
    private regionrepository regionrepository;

    public List<region> listartodos(){
        log.info("SERVICE: Listando todas las regiones");
        return regionrepository.findAll();
    }

    public region buscarporid(Integer id){
        log.info("SERVICE: Buscando region ID: {}", id);
        return regionrepository.findById(id).orElseThrow(() -> {
            log.error("SERVICE: No se encontro la region ID: {}", id);
            return new RuntimeException("Error, La region con ID " + id + " no existe.");
        });
    }

    public region guardarregion(region nueva){
        log.info("SERVICE: Guardando nueva region: {}", nueva.getNombreRegion());
        return regionrepository.save(nueva);
    }

    public region actualizarregion(Integer id, region datos) {
        log.info("SERVICE: Actualizando region ID: {}", id);
        
        if(datos.getNombreRegion() != null){
            regionrepository.findById(id).orElseThrow(() -> new RuntimeException("No existe")).setNombreRegion(datos.getNombreRegion());
            log.info("SERVICE: Actualizando nombre de region ID: {}", id);
        }
        
        return regionrepository.save(regionrepository.findById(id).get());
    }

    public void eliminarregion(Integer id){
        log.info("SERVICE: Eliminando region ID: {}", id);
        regionrepository.delete(regionrepository.findById(id).orElseThrow(() -> new RuntimeException("ID de region no encontrado para eliminar")));
    }
}