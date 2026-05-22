package com.icomer.icomers.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icomer.icomers.model.comuna;
import com.icomer.icomers.repository.comunarepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class comunaservice {

    @Autowired
    private comunarepository comunarepository;

    public List<comuna> listartodos(){
        log.info("SERVICE: Listando todas las comunas");
        return comunarepository.findAll();
    }

    public comuna buscarporid(Integer id){
        log.info("SERVICE: Buscando comuna ID: {}", id);
        return comunarepository.findById(id).orElseThrow(() -> {
            log.error("SERVICE: No se encontro la comuna ID: {}", id);
            return new RuntimeException("Error, La comuna con ID " + id + " no existe.");
        });
    }

    public comuna guardarcomuna(comuna nueva){
        log.info("SERVICE: Guardando nueva comuna: {}", nueva.getNombrecomuna());
        return comunarepository.save(nueva);
    }

    public comuna actualizarcomuna(Integer id, comuna datos) {
        log.info("SERVICE: Actualizando comuna ID: {}", id);
        
        if(datos.getNombrecomuna() != null){
            comunarepository.findById(id).orElseThrow(() -> new RuntimeException("No existe")).setNombrecomuna(datos.getNombrecomuna());
            log.info("SERVICE: Actualizando nombre de comuna ID: {}", id);
        }
        
        return comunarepository.save(comunarepository.findById(id).get());
    }

    public void eliminarcomuna(Integer id){
        log.info("SERVICE: Eliminando comuna ID: {}", id);
        comunarepository.delete(comunarepository.findById(id).orElseThrow(() -> new RuntimeException("ID de comuna no encontrado para eliminar")));
    }
}