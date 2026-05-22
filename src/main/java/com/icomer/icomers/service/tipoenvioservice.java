package com.icomer.icomers.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icomer.icomers.model.tipoenvio;
import com.icomer.icomers.repository.tipoenviorepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class tipoenvioservice {

    @Autowired
    private tipoenviorepository tipoenviorepository;

    public List<tipoenvio> listartodos(){
        log.info("SERVICE: Listando todos los tipos de envio");
        return tipoenviorepository.findAll();
    }

    public tipoenvio buscarporid(Integer id){
        log.info("SERVICE: Buscando tipo de envio ID: {}", id);
        return tipoenviorepository.findById(id).orElseThrow(() -> {
            log.error("SERVICE: No se encontro el ID de envio: {}", id);
            return new RuntimeException("Error, El envio con ID " + id + " no existe.");
        });
    }

    public tipoenvio guardartipoenvio(tipoenvio nuevo){
        log.info("SERVICE: Guardando nuevo envio: {}", nuevo.getNombreEnvio());
        return tipoenviorepository.save(nuevo);
    }

    public tipoenvio actualizartipoenvio(Integer id, tipoenvio datos) {
        log.info("SERVICE: Actualizando envio ID: {}", id);
        
        if(datos.getNombreEnvio() != null){
            tipoenviorepository.findById(id).orElseThrow(() -> new RuntimeException("No existe")).setNombreEnvio(datos.getNombreEnvio());
            log.info("SERVICE: Actualizando nombre de envio ID: {}", id);
        }
        
        if(datos.getCostoEnvio() != null){
            tipoenviorepository.findById(id).orElseThrow(() -> new RuntimeException("No existe")).setCostoEnvio(datos.getCostoEnvio());
            log.info("SERVICE: Actualizando costo de envio ID: {}", id);
        }
        
        return tipoenviorepository.save(tipoenviorepository.findById(id).get());
    }

    public void eliminartipoenvio(Integer id){
        log.info("SERVICE: Eliminando envio ID: {}", id);
        tipoenviorepository.delete(tipoenviorepository.findById(id).orElseThrow(() -> new RuntimeException("ID de envio no encontrado para eliminar")));
    }
}