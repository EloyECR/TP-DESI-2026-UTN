INSERT IGNORE INTO provincia (id, nombre) VALUES
  (1, 'Santa Fe'),
  (2, 'Buenos Aires');

INSERT IGNORE INTO ciudad (id, nombre, provincia_id) VALUES
  (1, 'Santa Fe', 1),
  (2, 'Rosario', 1),
  (3, 'Buenos Aires', 2);

INSERT IGNORE INTO persona (id, nombre, apellido, dni_cuit, telefono, email, domicilio, ciudad_id, eliminada) VALUES
  (1, 'Marcos', 'Pereyra', '30111222', '3424111222', 'mpereyra@mail.com', 'Bv. Pellegrini 1234', 1, false),
  (2, 'Lucia', 'Fernandez', '28999111', '3424222333', 'lfernandez@mail.com', 'San Martin 456', 1, false),
  (3, 'Diego', 'Alvarez', '32555666', '3415333444', 'dalvarez@mail.com', 'Cordoba 789', 2, false),
  (4, 'Carla', 'Gimenez', '27444555', '1144556677', 'cgimenez@mail.com', 'Av. Rivadavia 2000', 3, false);

INSERT IGNORE INTO propiedad (id, direccion, ciudad_id, tipo, cantidad_ambientes, metros_cuadrados, descripcion, comodidades, estado_disponibilidad, propietario_id, eliminada) VALUES
  (1, 'San Martin 1234', 1, 'CASA', 3, 95.50, 'Casa familiar en zona residencial.', 'Patio, cochera y lavadero', 'DISPONIBLE', 1, false),
  (2, 'Av. Pellegrini 2500', 2, 'DEPARTAMENTO', 2, 60.00, 'Departamento centrico cercano a facultades.', 'Balcon y ascensor', 'ALQUILADA', 2, false),
  (3, 'Rivadavia 800', 3, 'LOCAL', 1, 45.00, 'Local comercial a la calle.', 'Vidriera y deposito', 'INACTIVA', 3, false);

INSERT IGNORE INTO historial_estado_propiedad (id, propiedad_id, estado, fecha_hora) VALUES
  (1, 1, 'DISPONIBLE', '2026-06-01 10:00:00'),
  (2, 2, 'DISPONIBLE', '2026-06-01 10:15:00'),
  (3, 2, 'ALQUILADA', '2026-06-10 09:30:00'),
  (4, 3, 'INACTIVA', '2026-06-05 11:00:00');

INSERT IGNORE INTO publicacion (id, propiedad_id, precio_mensual, condiciones, descripcion, fecha_publicacion, estado, eliminada) VALUES
  (1, 1, 250000.00, 'Mes de deposito y garantia propietaria.', 'Casa disponible para alquiler familiar.', '2026-06-02', 'ACTIVA', false),
  (2, 2, 180000.00, 'Contrato por 24 meses.', 'Publicacion historica del departamento.', '2026-06-03', 'FINALIZADA', false);

INSERT IGNORE INTO historial_estado_publicacion (id, publicacion_id, estado, fecha_hora) VALUES
  (1, 1, 'ACTIVA', '2026-06-02 12:00:00'),
  (2, 2, 'ACTIVA', '2026-06-03 12:00:00'),
  (3, 2, 'FINALIZADA', '2026-06-10 09:30:00');

INSERT IGNORE INTO contrato (id, propiedad_id, propietario_id, inquilino_id, fecha_inicio, duracion_meses, importe_mensual, dia_vencimiento_mensual, descripcion, estado, eliminado) VALUES
  (1, 2, 2, 4, '2026-06-10', 24, 180000.00, 10, 'Contrato de alquiler del departamento de Rosario.', 'ACTIVO', false),
  (2, 1, 1, 3, '2026-07-01', 12, 250000.00, 5, 'Borrador de contrato para casa en Santa Fe.', 'BORRADOR', false);

INSERT IGNORE INTO historial_estado_contrato (id, contrato_id, estado, fecha_hora) VALUES
  (1, 1, 'BORRADOR', '2026-06-08 15:00:00'),
  (2, 1, 'ACTIVO', '2026-06-10 09:30:00'),
  (3, 2, 'BORRADOR', '2026-06-20 10:00:00');

INSERT IGNORE INTO factura (id, contrato_id, concepto_facturado, fecha_emision, fecha_vencimiento, importe, estado, fecha_pago, medio, importe_pagado, interes, eliminada) VALUES
  (1, 1, 'Alquiler junio 2026', '2026-06-10', '2026-06-20', 180000.00, 'PAGADA', '2026-06-18', 'TRANSFERENCIA', 180000.00, 0.00, false),
  (2, 1, 'Alquiler julio 2026', '2026-07-01', '2026-07-10', 180000.00, 'PENDIENTE', null, null, null, null, false);

INSERT IGNORE INTO historial_estado_factura (id, factura_id, estado, fecha_hora) VALUES
  (1, 1, 'PENDIENTE', '2026-06-10 10:00:00'),
  (2, 1, 'PAGADA', '2026-06-18 16:00:00'),
  (3, 2, 'PENDIENTE', '2026-07-01 10:00:00');
