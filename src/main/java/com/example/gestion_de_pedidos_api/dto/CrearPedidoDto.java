package com.example.gestion_de_pedidos_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoDto {
    private Long terminalId;

    //Necesitamos también saber qué productos quiere el cliente y qué cantidad
    //Creamos un Map en el que la key es el id del producto y el value es la cantidad
    private Map<Long, Integer> productosComprados;
}
