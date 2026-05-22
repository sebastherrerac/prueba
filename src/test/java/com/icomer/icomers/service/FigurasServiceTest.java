package com.icomer.icomers.service;

import com.icomer.icomers.DTO.FigurasDTO;
import com.icomer.icomers.model.Categoria;
import com.icomer.icomers.model.Figuras;
import com.icomer.icomers.repository.FigurasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FigurasServiceTest {

    @Mock
    private FigurasRepository figurasRepository;

    @InjectMocks
    private FigurasService figurasService;

    private Figuras figura;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        figura = Figuras.builder()
                .idFigura(1)
                .nombre("Naruto")
                .precio(15000.0)
                .stock(10)
                .descripcion("Figura de Naruto Uzumaki")
                .categoria(categoria)
                .build();
    }

    @Test
    void obtenerTodos_retornaListaDTOs() {
        when(figurasRepository.findAll()).thenReturn(List.of(figura));

        List<FigurasDTO> resultado = figurasService.obtenerTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Naruto");
        verify(figurasRepository).findAll();
    }

    @Test
    void obtenerTodos_listaVacia_retornaVacio() {
        when(figurasRepository.findAll()).thenReturn(List.of());

        List<FigurasDTO> resultado = figurasService.obtenerTodos();

        assertThat(resultado).isEmpty();
    }

    @Test
    void guardarFiguras_retornaFiguraGuardada() {
        when(figurasRepository.save(any(Figuras.class))).thenReturn(figura);

        Figuras resultado = figurasService.guardarFiguras(figura);

        assertThat(resultado.getNombre()).isEqualTo("Naruto");
        assertThat(resultado.getPrecio()).isEqualTo(15000.0);
        verify(figurasRepository).save(figura);
    }

    @Test
    void eliminarFigura_existente_retornaMensajeExito() {
        when(figurasRepository.findById(1)).thenReturn(Optional.of(figura));
        doNothing().when(figurasRepository).delete(figura);

        String resultado = figurasService.eliminarFigura(1);

        assertThat(resultado).contains("exitosamente");
        verify(figurasRepository).delete(figura);
    }

    @Test
    void eliminarFigura_noExiste_retornaMensajeError() {
        when(figurasRepository.findById(99)).thenReturn(Optional.empty());

        String resultado = figurasService.eliminarFigura(99);

        assertThat(resultado).contains("no existe");
        verify(figurasRepository, never()).delete(any());
    }

    @Test
    void buscarPorId_existente_retornaDTO() {
        when(figurasRepository.findById(1)).thenReturn(Optional.of(figura));

        FigurasDTO resultado = figurasService.buscarPorId(1);

        assertThat(resultado.getIdFigura()).isEqualTo(1);
        assertThat(resultado.getNombre()).isEqualTo("Naruto");
    }

    @Test
    void buscarPorId_noExiste_lanzaExcepcion() {
        when(figurasRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> figurasService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe");
    }

    @Test
    void actualizarFiguras_actualizaNombre() {
        FigurasDTO dto = new FigurasDTO();
        dto.setNombre("Goku");

        Figuras figuraActualizada = Figuras.builder()
                .idFigura(1).nombre("Goku").precio(15000.0).stock(10).build();

        when(figurasRepository.findById(1)).thenReturn(Optional.of(figura));
        when(figurasRepository.save(any())).thenReturn(figuraActualizada);

        FigurasDTO resultado = figurasService.actualizarFiguras(1, dto);

        assertThat(resultado.getNombre()).isEqualTo("Goku");
    }

    @Test
    void actualizarFiguras_noExiste_lanzaExcepcion() {
        when(figurasRepository.findById(99)).thenReturn(Optional.empty());

        FigurasDTO dto = new FigurasDTO();
        assertThatThrownBy(() -> figurasService.actualizarFiguras(99, dto))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void buscarPorCategoria_retornaFigurasDeCategoria() {
        when(figurasRepository.findByCategoria_IdCategoria(1)).thenReturn(List.of(figura));

        List<FigurasDTO> resultado = figurasService.buscarPorCategoria(1);

        assertThat(resultado).hasSize(1);
        verify(figurasRepository).findByCategoria_IdCategoria(1);
    }

    @Test
    void convertirADTO_mapeaCorrectamente() {
        FigurasDTO dto = figurasService.convertirADTO(figura);

        assertThat(dto.getIdFigura()).isEqualTo(figura.getIdFigura());
        assertThat(dto.getNombre()).isEqualTo(figura.getNombre());
        assertThat(dto.getPrecio()).isEqualTo(figura.getPrecio());
        assertThat(dto.getDescripcion()).isEqualTo(figura.getDescripcion());
    }
}
