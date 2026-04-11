-- TERMINALES
INSERT INTO terminal (nombre) VALUES ('Caja Principal');
INSERT INTO terminal (nombre) VALUES ('Caja Secundaria');

-- CATEGORIAS
INSERT INTO categorias (nombre) VALUES ('Comida');
INSERT INTO categorias (nombre) VALUES ('Bebida');

-- PRODUCTOS
INSERT INTO productos (nombre, precio, activo, categoria_id) VALUES ('Hamburguesa', 8.50, true, 1);
INSERT INTO productos (nombre, precio, activo, categoria_id) VALUES ('Pizza', 10.00, true, 1);
INSERT INTO productos (nombre, precio, activo, categoria_id) VALUES ('Refresco', 2.50, true, 2);
INSERT INTO productos (nombre, precio, activo, categoria_id) VALUES ('Agua', 1.50, true, 2);
INSERT INTO productos (nombre, precio, activo, categoria_id) VALUES ('Cerveza', 3.00, false, 2);

-- PEDIDOS
INSERT INTO pedidos (codigo, fecha, total, estado_pedido, terminal_id)
VALUES ('PED-AAAA1111', NOW(), 19.50, 'CREADO', 1);

INSERT INTO pedidos (codigo, fecha, total, estado_pedido, terminal_id)
VALUES ('PED-BBBB2222', NOW(), 12.50, 'PREPARACION', 2);

INSERT INTO pedidos (codigo, fecha, total, estado_pedido, terminal_id)
VALUES ('PED-CCCC3333', NOW(), 5.00, 'LISTO', 1);

-- PEDIDO_PRODUCTO (líneas)

-- Pedido 1
INSERT INTO pedidos_productos (cantidad, precio_unitario, pedido_id, producto_id)
VALUES (2, 8.50, 1, 1); -- 2 hamburguesas

INSERT INTO pedidos_productos (cantidad, precio_unitario, pedido_id, producto_id)
VALUES (1, 2.50, 1, 3); -- 1 refresco

-- Pedido 2
INSERT INTO pedidos_productos (cantidad, precio_unitario, pedido_id, producto_id)
VALUES (1, 10.00, 2, 2); -- 1 pizza

INSERT INTO pedidos_productos (cantidad, precio_unitario, pedido_id, producto_id)
VALUES (1, 2.50, 2, 3); -- 1 refresco

-- Pedido 3
INSERT INTO pedidos_productos (cantidad, precio_unitario, pedido_id, producto_id)
VALUES (2, 2.50, 3, 3); -- 2 refrescos