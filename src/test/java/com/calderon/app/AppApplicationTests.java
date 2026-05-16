package com.calderon.app;

import com.calderon.app.model.Producto;
import com.calderon.app.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AppApplicationTests {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void contextLoads() {
        assertThat(productoRepository).isNotNull();
    }

    @Test
    void crearYRecuperarProducto() {
        Producto p = new Producto("Laptop", 1500.0, "Laptop gaming");
        Producto guardado = productoRepository.save(p);
        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Laptop");
        productoRepository.deleteById(guardado.getId());
    }

    @Test
    void listarProductosRetornaLista() {
        assertThat(productoRepository.findAll()).isNotNull();
    }
}
