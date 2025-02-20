INSERT INTO aut_categoria (nombre, ultima_asignacion_semana, semanas_a_planificar)
VALUES
  ('Categoria A', 0, 4),
  ('Categoria B', 0, 4),
  ('Categoria C', 0, 6),
  ('Categoria D', 0, 6);

INSERT INTO aut_rel_producto (nombre, orden, aut_categoria_id) VALUES
  ('Producto 1', 1, 1),
  ('Producto 2', 2, 2),
  ('Producto 3', 3, 3),
  ('Producto 4', 4, 4),
  ('Producto 5', 5, 1),
  ('Producto 6', 6, 2),
  ('Producto 7', 7, 3),
  ('Producto 8', 8, 4),
  ('Producto 9', 9, 1),
  ('Producto 10', 10, 2);

INSERT INTO aut_categoria_tope (cantidad_min, cantidad_max, es_autoridad, aut_categoria_id) VALUES
  (1, 2, 0, 1),
  (1, 1, 1, 1),
  (1, 3, 0, 2),
  (1, 1, 1, 2),
  (1, 2, 0, 3),
  (1, 1, 1, 3),
  (1, 3, 0, 4),
  (1, 1, 1, 4);

INSERT INTO aut_grupo (nombre, orden_grupo, aut_categoria_id) VALUES
  ('Grupo 1', 1, 1),
  ('Grupo 2', 2, 2),
  ('Grupo 3', 3, 3),
  ('Grupo 4', 4, 4),
  ('Grupo 5', 5, 1),
  ('Grupo 6', 6, 2);

INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id) VALUES
  ('Autoridad 1', 1001, 'autoridad', 1),
  ('Autoridad 2', 1002, 'autoridad', 2),
  ('Autoridad 3', 1003, 'autoridad', 3),
  ('Autoridad 4', 1004, 'autoridad', 4),
  ('Autoridad 5', 1005, 'autoridad', 5),
  ('Autoridad 6', 1006, 'autoridad', 6);

INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id) VALUES
  ('Auxiliar 1', 2001, 'auxiliar', 1),
  ('Auxiliar 2', 2002, 'auxiliar', 1),
  ('Auxiliar 3', 2003, 'auxiliar', 2),
  ('Auxiliar 4', 2004, 'auxiliar', 2),
  ('Auxiliar 5', 2005, 'auxiliar', 3),
  ('Auxiliar 6', 2006, 'auxiliar', 3),
  ('Auxiliar 7', 2007, 'auxiliar', 4),
  ('Auxiliar 8', 2008, 'auxiliar', 4),
  ('Auxiliar 9', 2009, 'auxiliar', 5),
  ('Auxiliar 10', 2010, 'auxiliar', 5),
  ('Auxiliar 11', 2011, 'auxiliar', 6),
  ('Auxiliar 12', 2012, 'auxiliar', 6);

INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id) VALUES
  ('Coordinador', 3001, 'coordinador', 1);

INSERT INTO aut_sorteo (fecha, confirmado, dia_descriptivo, aut_rel_producto_id) VALUES
  ('2025-03-01', 0, 'Sábado', 1),
  ('2025-03-01', 1, 'Sábado', 2),
  ('2025-03-02', 0, 'Domingo', 3),
  ('2025-03-03', 1, 'Lunes', 4),
  ('2025-03-04', 0, 'Martes', 5),
  ('2025-03-05', 1, 'Miércoles', 6),
  ('2025-03-06', 0, 'Jueves', 7),
  ('2025-03-07', 1, 'Viernes', 8),
  ('2025-03-08', 0, 'Sábado', 9),
  ('2025-03-09', 1, 'Domingo', 10),
  ('2025-03-10', 0, 'Lunes', 1),
  ('2025-03-11', 1, 'Martes', 2),
  ('2025-03-12', 0, 'Miércoles', 3),
  ('2025-03-13', 1, 'Jueves', 4),
  ('2025-03-14', 0, 'Viernes', 5),
  ('2025-03-15', 1, 'Sábado', 6),
  ('2025-03-16', 0, 'Domingo', 7),
  ('2025-03-17', 1, 'Lunes', 8),
  ('2025-03-18', 0, 'Martes', 9),
  ('2025-03-19', 1, 'Miércoles', 10),
  ('2025-03-20', 0, 'Jueves', 1),
  ('2025-03-21', 1, 'Viernes', 2),
  ('2025-03-22', 0, 'Sábado', 3),
  ('2025-03-23', 1, 'Domingo', 4),
  ('2025-03-24', 0, 'Lunes', 5),
  ('2025-03-25', 1, 'Martes', 6),
  ('2025-03-26', 0, 'Miércoles', 7),
  ('2025-03-27', 1, 'Jueves', 8),
  ('2025-03-28', 0, 'Viernes', 9),
  ('2025-03-29', 1, 'Sábado', 10),
  ('2025-03-30', 0, 'Domingo', 1),
  ('2025-03-31', 1, 'Lunes', 2),
  ('2025-04-01', 0, 'Martes', 3),
  ('2025-04-02', 1, 'Miércoles', 4),
  ('2025-04-03', 0, 'Jueves', 5),
  ('2025-04-04', 1, 'Viernes', 6),
  ('2025-04-05', 0, 'Sábado', 7),
  ('2025-04-06', 1, 'Domingo', 8),
  ('2025-04-07', 0, 'Lunes', 9),
  ('2025-04-08', 1, 'Martes', 10),
  ('2025-04-09', 0, 'Miércoles', 1),
  ('2025-04-10', 1, 'Jueves', 2),
  ('2025-04-11', 0, 'Viernes', 3),
  ('2025-04-12', 1, 'Sábado', 4),
  ('2025-04-13', 0, 'Domingo', 5),
  ('2025-04-14', 1, 'Lunes', 6),
  ('2025-04-15', 0, 'Martes', 7),
  ('2025-04-16', 1, 'Miércoles', 8),
  ('2025-04-17', 0, 'Jueves', 9),
  ('2025-04-18', 1, 'Viernes', 10),
  ('2025-04-19', 0, 'Sábado', 1),
  ('2025-04-20', 1, 'Domingo', 2),
  ('2025-04-21', 0, 'Lunes', 3),
  ('2025-04-22', 1, 'Martes', 4),
  ('2025-04-23', 0, 'Miércoles', 5),
  ('2025-04-24', 1, 'Jueves', 6),
  ('2025-04-25', 0, 'Viernes', 7),
  ('2025-04-26', 1, 'Sábado', 8),
  ('2025-04-27', 0, 'Domingo', 9),
  ('2025-04-28', 1, 'Lunes', 10);

INSERT INTO aut_asignacion (estado, aut_grupo_id, aut_sorteo_id) VALUES
  ('planificado', 1, 1),
  ('planificado', 2, 2),
  ('planificado', 3, 3),
  ('planificado', 4, 4);

INSERT INTO aut_solicitud_reemplazo (nombre, descripcion, fecha_solicitud, sol_estado, aut_empleado_solicitante, aut_empleado_reemplazo, aut_asignacion_solicitante, aut_asignacion_reemplazo)
VALUES
  ('Solicitud 1', 'Reemplazo por turno medico', CURRENT_DATE(), 'pendiente', 1, 7, 1, 2),
  ('Solicitud 2', 'Reemplazo por viaje', CURRENT_DATE(), 'aprobada', 2, 8, 2, 3);