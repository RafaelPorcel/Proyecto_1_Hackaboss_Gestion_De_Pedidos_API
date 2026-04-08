package com.example.gestion_de_pedidos_api.service;

import com.example.gestion_de_pedidos_api.dto.CrearPedidoDto;
import com.example.gestion_de_pedidos_api.dto.PedidoDto;
import com.example.gestion_de_pedidos_api.dto.ProductosPedidoDto;
import com.example.gestion_de_pedidos_api.exception.ResourceNotFoundException;
import com.example.gestion_de_pedidos_api.model.*;
import com.example.gestion_de_pedidos_api.repository.PedidoProductoRepository;
import com.example.gestion_de_pedidos_api.repository.PedidoRepository;
import com.example.gestion_de_pedidos_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    // Para poder acceder al método de encontrar terminal por Id
    @Autowired
    private TerminalService terminalService;
    // Para poder acceder a los productos y ver su precio
    @Autowired
    private ProductoRepository productoRepository;
    //Para poder guardar cada linea de pedido
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    //Método listar todos los pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    //Método registrar un pedido
    //Devuelve PedidoDto para no mostrar todos los datos sensibles
    //Recibe CrearPedidoDto
    public PedidoDto registrarPedido(CrearPedidoDto crearPedidoDto) {
        Pedido nuevoPedido = new Pedido();//Creamos un nuevo pedido y ahora lo armamos con los Dto
        //Asi sabemos el id de la terminal usada
        Terminal terminalUsada = terminalService.buscarTerminalPorId(crearPedidoDto.getTerminalId());
        nuevoPedido.setCodigo("PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());//Genera un código único
        nuevoPedido.setFecha(LocalDateTime.now());
        nuevoPedido.setTerminal(terminalUsada);

        List<PedidoProducto> lineasPedido = new ArrayList<>();//creamos la lista de lineas de pedido
        double precioTotal = 0.0;

        //Recorremos el Map que contiene la info de qué productos y cuantos compra el cliente
        for (Map.Entry<Long, Integer> entry : crearPedidoDto.getProductosComprados().entrySet()) {
            Long productoCompradoId = entry.getKey();//Obtenemos el id
            Integer cantidadCompradaProducto = entry.getValue();//Obtenemos la cantidad

            //Nos cercioramos de que el producto con ese Id exista
            Producto productoComprado = productoRepository.findById(productoCompradoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con id: " + productoCompradoId + " no encontrado"));

            //Creamos una lineaPedido que hay que agregarle a la lista de líneas de pedido
            PedidoProducto lineaPedido = new PedidoProducto();

            //Sacamos los datos para añadirlos a la lineaPedido
            lineaPedido.setCantidad(cantidadCompradaProducto);
            lineaPedido.setPrecioUnitario(productoComprado.getPrecio());
            lineaPedido.setPedido(nuevoPedido);
            lineaPedido.setProducto(productoComprado);

            //Calculamos el precio total multiplicandolo por cantidad de productos
            precioTotal += productoComprado.getPrecio()*cantidadCompradaProducto;

            //Añadimos la lineaPedido a la lista de lineasPedido creada arriba
            lineasPedido.add(lineaPedido);

        }

        nuevoPedido.setTotal(precioTotal);
        nuevoPedido.setEstadoPedido(EstadoPedido.CREADO);
        //Aquí se añaden todas las líneas de pedido al Pedido
        nuevoPedido.setLineasPedido(lineasPedido);

        //Guardamos el pedido en el repositorio
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        // Este pedidoGuardado es el que mapeamos y devolvemos porque ya tiene id al guardarlo en la bbdd
        return pedidoToPedidoDto(pedidoGuardado);

    }


    // *** MÉTODOS DE MAPEO ***

    //Método para transformar un Pedido en PedidoDto
    private PedidoDto pedidoToPedidoDto (Pedido pedido) {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(pedido.getId());
        pedidoDto.setCodigo(pedido.getCodigo());
        pedidoDto.setFechaCreacion(pedido.getFecha());
        pedidoDto.setEstado(pedido.getEstadoPedido().name());//así convertimos el estado a String
        pedidoDto.setTotal(pedido.getTotal());
        pedidoDto.setTerminalId(pedido.getTerminal().getId());

        // Convertimos la lista de lineasPedido de la entidad Pedido a la lista de productos del PedidoDto
        List<ProductosPedidoDto> productosDto = pedido.getLineasPedido().stream()
                    .map(this::pedidoProductoToDto) // Llamamos al segundo método de mapeo
                    .toList();
            pedidoDto.setProductos(productosDto);


        return pedidoDto;
    }

    private ProductosPedidoDto pedidoProductoToDto (PedidoProducto linea) {
        ProductosPedidoDto pedidoProductoToDto = new ProductosPedidoDto();
        pedidoProductoToDto.setProductoId(linea.getProducto().getId());
        pedidoProductoToDto.setNombreProducto(linea.getProducto().getNombre());
        pedidoProductoToDto.setCantidad(linea.getCantidad());
        pedidoProductoToDto.setPrecioUnitario(linea.getPrecioUnitario());

        // Calculamos el subtotal de esta linea de pedido
        pedidoProductoToDto.setSubtotal(linea.getCantidad() * linea.getPrecioUnitario());

        return pedidoProductoToDto;
    }



}
