package com.icomer.icomers.repository;
import com.icomer.icomers.model.Personaje;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    Optional<Personaje> findByNombre(String nombre);
}