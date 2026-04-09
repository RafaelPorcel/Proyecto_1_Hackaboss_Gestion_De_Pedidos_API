package com.example.gestion_de_pedidos_api.controller;

import com.example.gestion_de_pedidos_api.dto.CrearPedidoDto;
import com.example.gestion_de_pedidos_api.dto.PedidoDto;
import com.example.gestion_de_pedidos_api.dto.PedidoProductoRequestDto;
import com.example.gestion_de_pedidos_api.dto.ProductosPedidoDto;
import com.example.gestion_de_pedidos_api.model.EstadoPedido;
import com.example.gestion_de_pedidos_api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDto> registrarNuevoPedido(@RequestBody CrearPedidoDto crearPedidoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.registrarPedido(crearPedidoDto));
    }

    @PostMapping("/{pedidoId}/productos")
    public ResponseEntity<ProductosPedidoDto> agregarProductoAPedido(@PathVariable Long pedidoId, @RequestBody PedidoProductoRequestDto dto) {
        return ResponseEntity.ok(pedidoService.agregarProductosAPedido(pedidoId, dto));
    }

    @DeleteMapping("/{pedidoId}/productos/{productoId}")
    public ResponseEntity<String> eliminarProductoDePedido(@PathVariable Long pedidoId, @PathVariable Long productoId) {
        pedidoService.eliminarProductoDePedido(pedidoId, productoId);
        return ResponseEntity.ok("Producto eliminado del pedido con éxito");
    }

    @PatchMapping("/{pedidoId}/estado")
    public ResponseEntity<PedidoDto> cambiarEstadoDelPedido(@PathVariable Long pedidoId, EstadoPedido nuevoEstado) {
        return ResponseEntity.ok(pedidoService.gestionarEstadoDelPedido(pedidoId, nuevoEstado));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PedidoDto> obtenerPedidoPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(pedidoService.obtenerPedidoPorCodigo(codigo));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> listarPedidosYPorEstado(@RequestParam(required = false) EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.listarPedidos(estado));
    }
}
