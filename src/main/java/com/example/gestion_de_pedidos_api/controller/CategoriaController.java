package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.dto.CategoriaDto;
import com.example.gestion_de_pedidos_api.dto.CrearCategoriaDto;
import com.example.gestion_de_pedidos_api.model.Categoria;
import com.example.gestion_de_pedidos_api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Map<String, Object>> crearCategoria(@RequestBody CrearCategoriaDto nuevaCategoriaDto) {
        CategoriaDto categoriaDto = categoriaService.guardarCategoria(nuevaCategoriaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Categoría creada correctamente",
                "data", categoriaDto));
    }


    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        List<CategoriaDto> listaCategoriasDto = categoriaService.listarCategorias();
        return ResponseEntity.ok(listaCategoriasDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> obtenerPorId(@PathVariable Long id) {
        CategoriaDto categoriaDto = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaDto);
    }
}


