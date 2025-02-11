-- database: ../database/Hormiguero.sqlite
/*
CopyRight 2K25 EPN
autor: pat_mic
fecha: febrero
*/


DROP TABLE IF EXISTS Hormiga;
DROP TABLE IF EXISTS Catalogo; 

CREATE TABLE Catalogo (
     IdCatalogo         INTEGER PRIMARY KEY AUTOINCREMENT
    ,IdCatalogoPadre    INTEGER 
    ,Nombre             VARCHAR(20) NOT NULL  UNIQUE
    ,Detalle            VARCHAR(20)  
    
    ,Estado             VARCHAR(1) NOT NULL DEFAULT ('A')
    ,FechaCreacion      DATETIME NOT NULL  DEFAULT (datetime('now','localtime'))
    ,FechaModifica      DATETIME 
);

CREATE TABLE Hormiga (
     IdHormiga         INTEGER PRIMARY KEY AUTOINCREMENT
    ,IdCatalogoTipo    INTEGER  NOT NULL REFERENCES Catalogo(IdCatalogo)
    ,IdCatalogoSexo    INTEGER  NOT NULL REFERENCES Catalogo(IdCatalogo)
    ,IdCatalogoEstado  INTEGER  NOT NULL REFERENCES Catalogo(IdCatalogo)
    ,Nombre            VARCHAR(20) 
    
    ,Estado            VARCHAR(1) NOT NULL DEFAULT ('A')
    ,FechaCreacion     DATETIME NOT NULL  DEFAULT (datetime('now','localtime'))
    ,FechaModifica     DATETIME 
);

DROP VIEW IF EXISTS vwHormigaTipo;
CREATE   VIEW vwHormigaTipo AS
SELECT   h.IdCatalogo  
        ,h.IdCatalogoPadre      
        ,h.Nombre            
        ,h.Detalle           
        ,h.Estado            
        ,h.FechaCreacion     
        ,h.FechaModifica     
FROM    Catalogo p
JOIN    Catalogo h on h.IdCatalogoPadre = p.IdCatalogo 
WHERE   p.Nombre = 'HormigaTipo';

DROP VIEW IF EXISTS vwHormigaSexo;

CREATE   VIEW vwHormigaSexo AS
SELECT   h.IdCatalogo  
        ,h.IdCatalogoPadre      
        ,h.Nombre            
        ,h.Detalle           
        ,h.Estado            
        ,h.FechaCreacion     
        ,h.FechaModifica     
FROM    Catalogo p
JOIN    Catalogo h on h.IdCatalogoPadre = p.IdCatalogo 
WHERE   p.Nombre = 'HormigaSexo';

DROP VIEW IF EXISTS vwHormigaEstado;
CREATE   VIEW vwHormigaEstado AS
SELECT   h.IdCatalogo  
        ,h.IdCatalogoPadre      
        ,h.Nombre            
        ,h.Detalle           
        ,h.Estado            
        ,h.FechaCreacion     
        ,h.FechaModifica     
FROM    Catalogo p
JOIN    Catalogo h on h.IdCatalogoPadre = p.IdCatalogo 
WHERE   p.Nombre = 'HormigaEstado';


INSERT INTO Catalogo 
(IdCatalogoPadre  ,Nombre           ,Detalle          )                 VALUES               
 (NULL,     'HormigaTipo',          'tipos de hormigas')                --1
,(NULL,     'HormigaSexo',                 'tipos de sexo de las hormigas')    --2  
,(NULL,     'HormigaEstado',               'Estado de las hormigas');          --3  

INSERT INTO Catalogo 
(IdCatalogoPadre ,Nombre           ,Detalle          )     VALUES               
 (1,            'Larva',          'tipos de hormigas')      -- 4
,(1,            'Soldado',        'tipos de hormigas')      -- 5 
,(1,            'Zangano',        'tipos de hormigas')      -- 6 
,(2,            'Macho',          'sexo de hormiga')        -- 7
,(2,            'Hembra',         'sexo de hormiga')        -- 8 
,(3,            'Viva',           'sexo de hormiga')        -- 9
,(3,            'Muerta',         'sexo de hormiga');       -- 10


INSERT INTO Hormiga ( IdCatalogoTipo   ,IdCatalogoSexo   ,IdCatalogoEstado ,Nombre   ) VALUES     (4,  7, 9, 'Juana' );  
INSERT INTO Hormiga ( IdCatalogoTipo   ,IdCatalogoSexo   ,IdCatalogoEstado ,Nombre   ) VALUES     (5,  8, 10, 'pepe' );  
INSERT INTO Hormiga ( IdCatalogoTipo   ,IdCatalogoSexo   ,IdCatalogoEstado ,Nombre   ) VALUES     (4,  7, 9, 'JC' );  



SELECT    h.IdCatalogo              
,h.IdCatalogoPadre         
,h.Nombre                  
,h.Detalle                 
,h.Estado                  
,h.FechaCreacion           
,h.FechaModifica   
FROM    Catalogo p         
JOIN    Catalogo h on h.IdCatalogoPadre = p.IdCatalogo 
WHERE   p.IdCatalogo = 1 AND h.Estado = 'A' 