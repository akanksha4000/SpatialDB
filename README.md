Readme.txt
----------------------
Name: Akanksha Ramesh

Project Description:
Implemented a public announcement System that would query a spatial database. It kept track of all the areas the announcement system would cover such as the buildings and the student location. The project involved querying the database for retrieving the nearest neighbor, finding intersection of regions, finding the surrounding regions and plotting the values onto the location Map using JDBC.


 List of submitted files :
 populate.java
 hw2.java
 createdb.sql
 dropdb.sql
 readme.txt
 
Compilation Steps:
1)Place the image and data files buildings.xy students.xy announcementSystems.xy files in a folder.
1)Place the sdoapi.zip and classes111.jar in the same folder.
	javac -classpath ./sdoapi.zip;./classes111.jar; populate.java hw2.java 
2)To run the program:
	java -classpath ./sdoapi.zip;./classes111.jar; populate buildings.xy students.xy announcementSystems.xy
 

