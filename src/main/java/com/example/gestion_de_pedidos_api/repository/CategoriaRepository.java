package com.example.gestion_de_pedidos_api.repository;

import com.example.gestion_de_pedidos_api.model.Categoria;
import com.example.gestion_de_pedidos_api.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
