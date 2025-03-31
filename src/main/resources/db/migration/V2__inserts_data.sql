-- ============================================================
-- Inserts de datos de ejemplo para la base de datos "sorteador"
-- ============================================================

-- ------------------------------------------------------------
-- 1) Inserción en AUT_CATEGORIA (4 registros)
-- ------------------------------------------------------------
INSERT INTO aut_categoria (nombre, ultima_asignacion_semana, semanas_a_planificar)
VALUES
  ('Categoria A', 0, 4),
  ('Categoria B', 0, 4),
  ('Categoria C', 0, 6),
  ('Categoria D', 0, 6);

-- ------------------------------------------------------------
-- 2) Inserción en AUT_REL_PRODUCTO (10 productos)
--    Se distribuyen entre las categorías (IDs 1 a 4)
-- ------------------------------------------------------------
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

-- ------------------------------------------------------------
-- 3) Inserción en AUT_CATEGORIA_TOPE (8 registros: 2 por cada categoría)
--    Un registro para auxiliar (es_autoridad=0) y otro para autoridad (es_autoridad=1)
-- ------------------------------------------------------------
INSERT INTO aut_categoria_tope (cantidad_min, cantidad_max, es_autoridad, aut_categoria_id) VALUES
  (1, 2, 0, 1),  -- Categoria A - Auxiliar
  (1, 1, 1, 1),   -- Categoria A - Autoridad
  (1, 3, 0, 2),  -- Categoria B - Auxiliar
  (1, 1, 1, 2),   -- Categoria B - Autoridad
  (1, 2, 0, 3),  -- Categoria C - Auxiliar
  (1, 1, 1, 3),   -- Categoria C - Autoridad
  (1, 3, 0, 4),  -- Categoria D - Auxiliar
  (1, 1, 1, 4);   -- Categoria D - Autoridad

-- ------------------------------------------------------------
-- 4) Inserción en AUT_GRUPO (6 grupos)
--    Se asigna cada grupo a alguna categoría (para efecto de ejemplo)
-- ------------------------------------------------------------
INSERT INTO aut_grupo (nombre, orden_grupo, aut_categoria_id) VALUES
  ('Grupo 1', 1, 1),
  ('Grupo 2', 2, 2),
  ('Grupo 3', 3, 3),
  ('Grupo 4', 4, 4),
  ('Grupo 5', 5, 1),
  ('Grupo 6', 6, 2);

-- ------------------------------------------------------------
-- 5) Inserción en AUT_INTEGRANTE (19 registros)
--    - 6 autoridades
--    - 12 auxiliares
--    - 1 coordinador
--    Se asignan a distintos grupos
-- ------------------------------------------------------------
-- Autoridades (6)
-- Autoridades
INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id, email, password) VALUES
  ('Autoridad 1', 1001, 'AUTORIDAD', 1, 'email1@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Autoridad 2', 1002, 'AUTORIDAD', 2, 'email2@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Autoridad 3', 1003, 'AUTORIDAD', 3, 'email3@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Autoridad 4', 1004, 'AUTORIDAD', 4, 'email4@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Autoridad 5', 1005, 'AUTORIDAD', 5, 'email5@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Autoridad 6', 1006, 'AUTORIDAD', 6, 'email6@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES');

-- Auxiliares
INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id, email, password) VALUES
  ('Auxiliar 1', 2001, 'AUXILIAR', 1, 'email7@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 2', 2002, 'AUXILIAR', 1, 'email8@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 3', 2003, 'AUXILIAR', 2, 'email9@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 4', 2004, 'AUXILIAR', 2, 'email10@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 5', 2005, 'AUXILIAR', 3, 'email11@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 6', 2006, 'AUXILIAR', 3, 'email12@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 7', 2007, 'AUXILIAR', 4, 'email13@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 8', 2008, 'AUXILIAR', 4, 'email14@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 9', 2009, 'AUXILIAR', 5, 'email15@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 10', 2010, 'AUXILIAR', 5, 'email16@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 11', 2011, 'AUXILIAR', 6, 'email17@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES'),
  ('Auxiliar 12', 2012, 'AUXILIAR', 6, 'email18@email.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES');


-- Coordinador (1) (Corrección de orden de columnas)
INSERT INTO aut_integrante (nombre, legajo, rol, aut_grupo_id, email, password) VALUES
  ('Alejo Beliz', 3001, 'COORDINADOR', 1, 'alejobeliz@hotmail.com', '$2a$10$wwK1UV2ClPk3WeeBosc/6ubMO1TJrnZEB6oUTG4PONys6m68pwsES');

-- ------------------------------------------------------------
-- 6) Inserción en AUT_SORTEO (60 registros)
--    Se generan 60 sorteos distribuidos en los próximos 45 días.
--    Se asigna de forma cíclica un producto (IDs 1 a 10).
--    Para variar se alterna el campo "confirmado" y se indica un día descriptivo.
-- ------------------------------------------------------------
INSERT INTO aut_sorteo (fecha, confirmado, dia_descriptivo, aut_rel_producto_id) VALUES
  ('2025-03-01', 0, 'SABADO', 1),
  ('2025-03-01', 1, 'SABADO', 2),
  ('2025-03-02', 0, 'DOMINGO', 3),
  ('2025-03-03', 1, 'LUNES', 4),
  ('2025-03-04', 0, 'MARTES', 5),
  ('2025-03-05', 1, 'MIERCOLES', 6),
  ('2025-03-06', 0, 'JUEVES', 7),
  ('2025-03-07', 1, 'VIERNES', 8),
  ('2025-03-08', 0, 'SABADO', 9),
  ('2025-03-09', 1, 'DOMINGO', 10),
  ('2025-03-10', 0, 'LUNES', 1),
  ('2025-03-11', 1, 'MARTES', 2),
  ('2025-03-12', 0, 'MIERCOLES', 3),
  ('2025-03-13', 1, 'JUEVES', 4),
  ('2025-03-14', 0, 'VIERNES', 5),
  ('2025-03-15', 1, 'SABADO', 6),
  ('2025-03-16', 0, 'DOMINGO', 7),
  ('2025-03-17', 1, 'LUNES', 8),
  ('2025-03-18', 0, 'MARTES', 9),
  ('2025-03-19', 1, 'MIERCOLES', 10),
  ('2025-03-20', 0, 'JUEVES', 1),
  ('2025-03-21', 1, 'VIERNES', 2),
  ('2025-03-22', 0, 'SABADO', 3),
  ('2025-03-23', 1, 'DOMINGO', 4),
  ('2025-03-24', 0, 'LUNES', 5),
  ('2025-03-25', 1, 'MARTES', 6),
  ('2025-03-26', 0, 'MIERCOLES', 7),
  ('2025-03-27', 1, 'JUEVES', 8),
  ('2025-03-28', 0, 'VIERNES', 9),
  ('2025-03-29', 1, 'SABADO', 10),
  ('2025-03-30', 0, 'DOMINGO', 1),
  ('2025-03-31', 1, 'LUNES', 2),
  ('2025-04-01', 0, 'MARTES', 3),
  ('2025-04-02', 1, 'MIERCOLES', 4),
  ('2025-04-03', 0, 'JUEVES', 5),
  ('2025-04-04', 1, 'VIERNES', 6),
  ('2025-04-05', 0, 'SABADO', 7),
  ('2025-04-06', 1, 'DOMINGO', 8),
  ('2025-04-07', 0, 'LUNES', 9),
  ('2025-04-08', 1, 'MARTES', 10),
  ('2025-04-09', 0, 'MIERCOLES', 1),
  ('2025-04-10', 1, 'JUEVES', 2),
  ('2025-04-11', 0, 'VIERNES', 3),
  ('2025-04-12', 1, 'SABADO', 4),
  ('2025-04-13', 0, 'DOMINGO', 5),
  ('2025-04-14', 1, 'LUNES', 6),
  ('2025-04-15', 0, 'MARTES', 7),
  ('2025-04-16', 1, 'MIERCOLES', 8),
  ('2025-04-17', 0, 'JUEVES', 9),
  ('2025-04-18', 1, 'VIERNES', 10),
  ('2025-04-19', 0, 'SABADO', 1),
  ('2025-04-20', 1, 'DOMINGO', 2),
  ('2025-04-21', 0, 'LUNES', 3),
  ('2025-04-22', 1, 'MARTES', 4),
  ('2025-04-23', 0, 'MIERCOLES', 5),
  ('2025-04-24', 1, 'JUEVES', 6),
  ('2025-04-25', 0, 'VIERNES', 7),
  ('2025-04-26', 1, 'SABADO', 8),
  ('2025-04-27', 0, 'DOMINGO', 9),
  ('2025-04-28', 1, 'LUNES', 10);

-- ------------------------------------------------------------
-- 7) Inserción en AUT_ASIGNACION (4 registros)
--    Estos registros permitirán que las solicitudes de reemplazo referencien asignaciones existentes.
-- ------------------------------------------------------------
INSERT INTO aut_asignacion (estado, aut_grupo_id, aut_sorteo_id) VALUES
  ('PLANIFICADO', 1, 1),
  ('PLANIFICADO', 2, 2),
  ('PLANIFICADO', 3, 3),
  ('PLANIFICADO', 4, 4);

-- ------------------------------------------------------------
-- 8) Inserción en AUT_SOLICITUD_REEMPLAZO (2 registros)
--    Se relacionan con integrantes y asignaciones existentes.
-- ------------------------------------------------------------
INSERT INTO aut_solicitud_reemplazo (nombre, descripcion, fecha_solicitud, sol_estado, aut_empleado_solicitante, aut_empleado_reemplazo, aut_asignacion_solicitante, aut_asignacion_reemplazo)
VALUES
  ('Solicitud 1', 'Reemplazo por turno medico', CURRENT_DATE(), 'PENDIENTE', 1, 7, 1, 2),
  ('Solicitud 2', 'Reemplazo por viaje', CURRENT_DATE(), 'APROBADA', 2, 8, 2, 3);