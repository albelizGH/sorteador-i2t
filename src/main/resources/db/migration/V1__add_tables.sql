CREATE TABLE AUT_CATEGORIA (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(25) NOT NULL,
    ultima_asignacion_semana INT,
    ultima_asignacion_fecha DATE,
    semanas_a_planificar INT NOT NULL,
    CONSTRAINT pk_aut_categoria PRIMARY KEY (id),
    CONSTRAINT un_nombre_categoria UNIQUE(nombre)
);

CREATE TABLE AUT_REL_PRODUCTO (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_categoria_id BIGINT NOT NULL,
    nombre VARCHAR(25) NOT NULL,
    orden INT NOT NULL,
    CONSTRAINT pk_aut_rel_producto PRIMARY KEY (id),
    CONSTRAINT fk_aut_rel_producto_aut_categoria FOREIGN KEY (aut_categoria_id) REFERENCES AUT_CATEGORIA (id),
    CONSTRAINT un_aut_rel_producto_nombre UNIQUE (aut_categoria_id, nombre)
);

CREATE TABLE AUT_SORTEO (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_rel_producto_id BIGINT NOT NULL,
    fecha DATETIME NOT NULL,
    confirmado BOOLEAN NOT NULL,
    dia_descriptivo ENUM(
        'LUNES',
        'MARTES',
        'MIERCOLES',
        'JUEVES',
        'VIERNES',
        'SABADO',
        'DOMINGO'
    ) NOT NULL,
    CONSTRAINT pk_aut_sorteo PRIMARY KEY (id),
    CONSTRAINT fk_aut_sorteo_aut_rel_producto FOREIGN KEY (aut_rel_producto_id) REFERENCES AUT_REL_PRODUCTO (id)
);

CREATE TABLE AUT_GRUPO (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_categoria_id BIGINT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    orden_grupo INT NOT NULL,
    CONSTRAINT pk_aut_grupo PRIMARY KEY (id),
    CONSTRAINT fk_aut_grupo_aut_categoria Foreign Key (aut_categoria_id) REFERENCES AUT_CATEGORIA (id),
    CONSTRAINT un_aut_grupo_nombre UNIQUE (aut_categoria_id, nombre)
);

CREATE TABLE AUT_ASIGNACION (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_grupo_id BIGINT NOT NULL,
    aut_sorteo_id BIGINT NOT NULL,
    estado ENUM('PLANIFICADO','BORRADOR') NOT NULL,
    CONSTRAINT pk_aut_asignacion PRIMARY KEY (id),
    CONSTRAINT fk_aut_asignacion_aut_sorteo FOREIGN KEY (aut_sorteo_id) REFERENCES AUT_SORTEO (id),
    CONSTRAINT fk_aut_asignacion_aut_grupo FOREIGN KEY (aut_grupo_id) REFERENCES AUT_GRUPO (id)
);

CREATE TABLE AUT_CATEGORIA_TOPE (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_categoria_id BIGINT NOT NULL,
    cantidad_min INT NOT NULL,
    cantidad_max INT NOT NULL,
    es_autoridad BOOLEAN NOT NULL,
    CONSTRAINT pk_aut_categoria_tope PRIMARY KEY (id),
    CONSTRAINT fk_aut_categoria_tope_aut_categoria Foreign Key (aut_categoria_id) REFERENCES AUT_CATEGORIA (id)
);

CREATE TABLE AUT_INTEGRANTE (
    id BIGINT NOT NULL AUTO_INCREMENT,
    aut_grupo_id BIGINT NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    legajo INT NOT NULL,
    rol ENUM(
        'AUTORIDAD',
        'AUXILIAR',
        'COORDINADOR'
    ) NOT NULl,
    CONSTRAINT pk_aut_integrante PRIMARY KEY (id),
    CONSTRAINT fk_aut_integrante_aut_grupo Foreign Key (aut_grupo_id) REFERENCES AUT_GRUPO (id),
    CONSTRAINT un_aut_integrante_legajo UNIQUE (legajo)
);

CREATE TABLE AUT_SOLICITUD_REEMPLAZO (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fecha_solicitud DATE NOT NULL,
    aut_empleado_solicitante BIGINT NOT NULL,
    aut_empleado_reemplazo BIGINT NOT NULL,
    sol_estado ENUM(
        'PENDIENTE',
        'APROBADA',
        'CANCELADA'
    ),
    aut_asignacion_solicitante BIGINT NOT NULL,
    aut_asignacion_reemplazo BIGINT NOT NULL,
    CONSTRAINT pk_aut_solicitud_reemplazo PRIMARY KEY (id),
    CONSTRAINT fk_aut_solicitud_reemplazo_aut_empleado_solicitante FOREIGN KEY (aut_empleado_solicitante) REFERENCES AUT_INTEGRANTE (id),
    CONSTRAINT fk_aut_solicitud_reemplazo_aut_empleado_reemplazo FOREIGN KEY (aut_empleado_reemplazo) REFERENCES AUT_INTEGRANTE (id),
    CONSTRAINT fk_aut_solicitud_reemplazo_aut_asignacion_solicitante FOREIGN KEY (aut_asignacion_solicitante) REFERENCES AUT_ASIGNACION (id),
    CONSTRAINT fk_aut_solicitud_reemplazo_aut_asignacion_reemplazo FOREIGN KEY (aut_asignacion_reemplazo) REFERENCES AUT_ASIGNACION (id),
    CONSTRAINT un_aut_solicitud_solicitante UNIQUE (aut_empleado_solicitante, aut_asignacion_solicitante, fecha_solicitud)

);