package com.example.gestion_de_pedidos_api.service;

import com.example.gestion_de_pedidos_api.dto.CrearPedidoDto;
import com.example.gestion_de_pedidos_api.dto.PedidoDto;
import com.example.gestion_de_pedidos_api.dto.PedidoProductoRequestDto;
import com.example.gestion_de_pedidos_api.dto.ProductosPedidoDto;
import com.example.gestion_de_pedidos_api.exception.BadRequestException;
import com.example.gestion_de_pedidos_api.exception.ResourceNotFoundException;
import com.example.gestion_de_pedidos_api.model.*;
import com.example.gestion_de_pedidos_api.repository.PedidoProductoRepository;
import com.example.gestion_de_pedidos_api.repository.PedidoRepository;
import com.example.gestion_de_pedidos_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
        //Aquí se añaden todas las líneas de pedido al Pedido
        nuevoPedido.setLineasPedido(lineasPedido);
        //Cuando el pedido tiene sus productos calculamos el total mediante el metodo
        nuevoPedido.setTotal(calcularTotalDelPedido(nuevoPedido));
        nuevoPedido.setEstadoPedido(EstadoPedido.CREADO);

        //Guardamos el pedido en el repositorio
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        // Este pedidoGuardado es el que mapeamos y devolvemos porque ya tiene id al guardarlo en la bbdd
        return pedidoToPedidoDto(pedidoGuardado);

    }



    // Añadir productos a un pedido (creacion de PedidoProducto)
    public ProductosPedidoDto agregarProductosAPedido(Long idPedido, PedidoProductoRequestDto dto) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("El pedido con id " + idPedido + " no ha sido encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("El producto con id " + dto.getProductoId() + " no ha sido encontrado"));

        // Comprobar si el producto no está activo
        if (!producto.isActivo()) {
            throw new BadRequestException("El producto no está activo");
        }

        // Buscar si ya existe el producto en el pedido
        Optional<PedidoProducto> existente = pedido.getLineasPedido().stream()
                .filter(pp -> pp.getProducto().getId().equals(producto.getId()))
                .findFirst();

        PedidoProducto pedidoProducto;

        if (existente.isPresent()) {
            // Si ya existe se suma la cantidad introducida de producto a lo anterior
            pedidoProducto = existente.get();
            pedidoProducto.setCantidad(pedidoProducto.getCantidad() + dto.getCantidad());
        } else {
            // Si no existe se crea una nueva linea del producto
            pedidoProducto = new PedidoProducto();
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(dto.getCantidad());
            pedidoProducto.setPrecioUnitario(producto.getPrecio()); // Se guarda el precio en el momento del pedido

            pedido.getLineasPedido().add(pedidoProducto);
        }

        pedidoRepository.save(pedido);

        return pedidoProductoToDto(pedidoProducto);
    }

    // Calculo del total del pedido
    public Double calcularTotalDelPedido(Pedido pedido) {
        double total = pedido.getLineasPedido().stream()
                .mapToDouble(pp -> pp.getPrecioUnitario() * pp.getCantidad())
                .sum();

        // Total redondeado a dos decimales de manera que quede realista en terminos de dinero
        return BigDecimal.valueOf(total)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    //Gestion del cambio de estados (PREPARACION -> LISTO -> ENTREGADO)
    public void gestionarEstadosDelPedido() {

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

        // Calculamos el subtotal de esta línea de pedido
        pedidoProductoToDto.setSubtotal(linea.getCantidad() * linea.getPrecioUnitario());

        return pedidoProductoToDto;
    }

}
