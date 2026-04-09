package com.example.gestion_de_pedidos_api.dto;

import com.example.gestion_de_pedidos_api.model.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Se utuiliza como datos de entrada en PedidoController
// y poder mandar el estado del pedido al que queremos cambiar
public class EstadoPedidoRequestDto {
    private EstadoPedido estado;
}
