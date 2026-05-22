package com.icomer.icomers.controller;

import com.icomer.icomers.model.Cliente;
import com.icomer.icomers.service.ClienteService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerTodos() {
        log.info("Controler: Peticion para listar todos los clientes");
        return clienteService.listarTodos();
    }

    @PostMapping
    public Cliente guardar(@RequestBody Cliente cliente) {
        log.info("Controler: Peticion para guardar un cliente");
        return clienteService.guardar(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente clienteDetalles) {
        log.info("Controler: Peticion para actualizar el cliente con id {}", id);
        return ResponseEntity.ok(clienteService.actualizar(id, clienteDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controler: Peticion para eliminar el cliente con id {}", id);
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}