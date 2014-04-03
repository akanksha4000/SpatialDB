CREATE TABLE buildings (
  building_id VARCHAR2(3) PRIMARY KEY,
  building_name VARCHAR2(30),
  no_of_vertices NUMBER,
  shape SDO_GEOMETRY);
  
  
  CREATE TABLE students (
  person_id VARCHAR2(3) PRIMARY KEY,
  location SDO_GEOMETRY);
  
  CREATE TABLE announcementSystems (
  as_id varchar2(10) PRIMARY KEY,
  center SDO_GEOMETRY,
  radius NUMBER);
  
  
  INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'buildings',
  'shape',
  SDO_DIM_ARRAY(  
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);

INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'students',
  'location',
  SDO_DIM_ARRAY(   
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);

INSERT INTO USER_SDO_GEOM_METADATA 
  VALUES (
  'announcementSystems',
  'center',
  SDO_DIM_ARRAY(   
    SDO_DIM_ELEMENT('X', 0, 820, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 580, 0.005)
     ),
  NULL   -- SRID
);

CREATE INDEX bldg_idx
   ON buildings(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
   
CREATE INDEX student_idx
   ON students(location)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
   
CREATE INDEX as_idx
   ON announcementSystems(center)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;