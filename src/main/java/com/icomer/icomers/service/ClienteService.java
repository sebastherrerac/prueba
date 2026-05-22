package com.icomer.icomers.service;

import com.icomer.icomers.model.Cliente;
import com.icomer.icomers.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        log.info("SERVICE: Listando todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        log.info("SERVICE: Guardando nuevo cliente: {}", cliente.getNombre());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Integer id) {
        log.info("SERVICE: Eliminando cliente ID: {}", id);
        clienteRepository.deleteById(id);
    }

    public Cliente actualizar(Integer id, Cliente nuevosDatos) {
    log.info("SERVICE: Actualizando cliente ID: {}", id);
    Cliente clienteExistente = clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No encontrado"));
    
    clienteExistente.setNombre(nuevosDatos.getNombre());
    clienteExistente.setEmail(nuevosDatos.getEmail());
    return clienteRepository.save(clienteExistente);
}
}