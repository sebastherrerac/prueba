package com.icomer.icomers.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.icomer.icomers.model.tipopago;
import com.icomer.icomers.repository.tipopagorepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class tipopagoservice {

    @Autowired
    private tipopagorepository tipopagorepository;

    public List<tipopago> listartodos(){
        log.info("SERVICE: Listando todos los tipos de pago");
        return tipopagorepository.findAll();
    }

    public tipopago buscarporid(Integer id){
        log.info("SERVICE: Buscando tipo de pago ID: {}", id);
        return tipopagorepository.findById(id).orElseThrow(() -> {
            log.error("SERVICE: No se encontro el ID: {}", id);
            return new RuntimeException("Error, El tipo de pago con ID " + id + " no existe.");
        });
    }

    public tipopago guardartipopago(tipopago nuevo){
        log.info("SERVICE: Guardando nuevo pago: {}", nuevo.getNombreTipoPago());
        return tipopagorepository.save(nuevo);
    }

    public tipopago actualizartipopago(Integer id, tipopago datos) {
        log.info("SERVICE: Actualizando tipo pago ID: {}", id);
        
        
        if(datos.getNombreTipoPago() != null){
            tipopagorepository.findById(id).orElseThrow(() -> new RuntimeException("No existe")).setNombreTipoPago(datos.getNombreTipoPago());
            log.info("SERVICE: Actualizando nombre de pago ID: {}", id);
        }
        
        
        
        return tipopagorepository.save(tipopagorepository.findById(id).get());
    }

    public void eliminartipopago(Integer id){
        log.info("SERVICE: Eliminando tipo de pago ID: {}", id);
        // Eliminamos directo usando el findById dentro del delete
        tipopagorepository.delete(tipopagorepository.findById(id).orElseThrow(() -> new RuntimeException("ID no encontrado para eliminar")));
    }
}