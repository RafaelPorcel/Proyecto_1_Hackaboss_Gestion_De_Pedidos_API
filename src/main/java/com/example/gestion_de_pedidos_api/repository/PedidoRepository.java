package com.example.gestion_de_pedidos_api.repository;

import com.example.gestion_de_pedidos_api.model.EstadoPedido;
import com.example.gestion_de_pedidos_api.model.Pedido;
import com.example.gestion_de_pedidos_api.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByOrderByFechaAsc();

    List<Pedido> findByEstadoPedidoOrderByFechaAsc(EstadoPedido estado);
}
