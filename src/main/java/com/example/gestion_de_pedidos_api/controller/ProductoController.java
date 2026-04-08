package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.dto.CrearProductoDto;
import com.example.gestion_de_pedidos_api.dto.ProductoDto;
import com.example.gestion_de_pedidos_api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Crear
    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody CrearProductoDto dto) {
        ProductoDto creado = productoService.crearProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // List con filtros
    @GetMapping
    public ResponseEntity<List<ProductoDto>> listarProductos(
            // Es un filtro optcional del  estado del producto
            // Si es null devuelve activos e inactivos
            // Si es true solo activos
            // Si es false solo inactivos
            @RequestParam(required = false) Boolean activo,
            // Es un filtro opcional de la categoría del producto
            // Se usa el ID de la categoría
            // Si es null no filtra por categoría
            @RequestParam(required = false) Long categoriaId,
            // El orden es opcional:
            //Se puede ordenar porprecio, nombre o categoria
            // Si es null ordena por nombre por defecto
            @RequestParam(required = false) String orden,
            // Se puede elegir el tipo de ordenacion:
            // ASC  ascendente
            // DESC descendente
            // Si es null ASC por defecto
            @RequestParam(required = false) String tipoOrden
    ) {
        List<ProductoDto> lista = productoService.listarProductos(activo, categoriaId, orden, tipoOrden);
        return ResponseEntity.ok(lista);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(
            @PathVariable Long id,
            @RequestBody CrearProductoDto dto
    ) {
        ProductoDto actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Desactivar producto. Borrado logico.
    // Patch por que no estamosa ctualizando todo el producto, solo un campo, el Activo.
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoDto> desactivarProducto(@PathVariable Long id) {
        ProductoDto desactivado = productoService.desactivarProducto(id);
        return ResponseEntity.ok(desactivado);
    }
}

