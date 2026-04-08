package com.example.gestion_de_pedidos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {
    private Long id;
    private String codigo;
    private LocalDateTime fechaCreacion;
    private String estado;
    private Double total;
    private Long terminalId;
    List<ProductosPedidoDto> productos;
}
