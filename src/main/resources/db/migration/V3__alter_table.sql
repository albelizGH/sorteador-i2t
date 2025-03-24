-- Se modifica la columna aut_categoria_id para aceptar valores nulos
-- debido a requerimientos de flexibilidad en la creación de grupos
-- sin categoría asignada inicialmente.
ALTER TABLE aut_integrante
MODIFY COLUMN aut_grupo_id BIGINT NULL;

