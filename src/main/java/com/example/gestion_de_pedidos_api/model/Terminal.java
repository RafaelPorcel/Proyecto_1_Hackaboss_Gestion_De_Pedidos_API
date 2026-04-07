package com.example.gestion_de_pedidos_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "terminal")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    @OneToMany (mappedBy = "terminal")
    private List<Pedido> listaPedidos;

}
