package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.model.Categoria;
import com.example.gestion_de_pedidos_api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.guardarCategoria(categoria);
        return ResponseEntity.ok(
                Map.of("mensaje", "Categoría creada correctamente",
                        "data", nuevaCategoria));
    }


    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categoria = categoriaService.listarCategorias();
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoria);
    }
}


