package com.example.gestion_de_pedidos_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_producto")
public class PedidoProducto {
    @Id
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Producto producto;

}
