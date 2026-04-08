package com.example.gestion_de_pedidos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Este DTO se utiliza como datos de entrada que suprime algunos datos innecesarios de ProductosPedidoDto
public class PedidoProductoRequestDto {
        private Long productoId;
        private Integer cantidad;
}
