package com.icomer.icomers.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.icomer.icomers.model.Figuras;

public interface FigurasRepository extends JpaRepository<Figuras, Integer>{
    
    List<Figuras> findByCategoria_IdCategoria(Integer idCategoria);
    
}
