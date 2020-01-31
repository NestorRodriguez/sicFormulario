CREATE DATABASE Tramites;
USE Tramites;

/*Creación de la tabla rol*/
CREATE TABLE rol (
	id numeric(3) primary key not null, 
    descripcion varchar(20) not null );
/*Insertar datos de la tabla rol*/
INSERT INTO rol VALUES (1, 'Empleado');
INSERT INTO rol VALUES (2, 'Usuario');

/*Creación de la tabla usuario*/
CREATE TABLE usuario ( 
	id numeric(3) primary key not null, 
    idrol numeric(3) not null, 
    identificacion varchar(20) not null, 
    nombre varchar(20) not null, 
    apellido varchar(20) not null, 
    telefono numeric(20), 
    direccion varchar(100) not null, 
    correo varchar(100) not null, 
    dependencia varchar(20), 
    fechaingreso date, 
    FOREIGN KEY (idrol) REFERENCES rol(id));
/*Insertar datos de la tabla personas*/
INSERT INTO usuario VALUES (1, 1, '11111256365', 'Néstor','Rodríguez', 3157428264, 'Carrera 13 # 27 - 00', 'nestor.rodriguez@sic.gov.co', 'Sitemas', '2020-01-31');

/*Creación de tabla marcapc*/
CREATE TABLE marcapc (
	id numeric(3) primary key not null, 
    descripción varchar(20) not null);
/*Insertar datos de marca pc*/
INSERT INTO marcapc VALUES (1, 'HP');
INSERT INTO marcapc VALUES (2, 'Asus');
    
/*Creación nde tabla formulario*/
CREATE TABLE formulario (
	id numeric(3) primary key not null, 
	correo varchar(50) not null, 
    comentarios varchar(255),  
    MarcaPc numeric(3), 
    fechaRespuesta date,
    idpersona numeric(3),
    FOREIGN KEY (MarcaPc) REFERENCES marcapc(id),
    FOREIGN KEY (idpersona) REFERENCES usuario(id));    
/*Insertar datos formulario*/
INSERT INTO formulario VALUES (1,'nes.rod@sic.gov.co','Llevo un mes con la queja', 1, '2020-01-31',1);
INSERT INTO formulario VALUES (2,'pecosa@gamil.com','Ya sw termino la primera', 1, '2019-10-25',2);


drop table formulario;