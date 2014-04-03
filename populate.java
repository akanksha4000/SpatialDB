import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.sdoapi.OraSpatialManager;
import oracle.sdoapi.adapter.GeometryAdapter;
import oracle.sdoapi.geom.Geometry;
import oracle.sql.STRUCT;


public class populate {

	/**
	 * @param args
	 */
	
	Connection mainConnection = null;
    Statement mainStatement = null;
    ResultSet mainResultSet = null;
    StringBuilder  query = new StringBuilder();
	    
	public static void main(String[] args) {
		populate p = new populate();
		p.connectToDB();			// connect to DB
		String query = p.readCreateFile("createdb.sql").toString();			//read the createdb file
		p.deleteTuples();
		p.insertBuildingValues(args[0]);
		p.insertStudentValues(args[1]);
		p.insertAnnouncementSystemValues(args[2]);	
		new hw2();
	}
	
	public void deleteTuples() {
		try {
			String query1="delete from buildings";
			String query2="delete from students";
			String query3="delete from announcementSystems";
			mainStatement.executeUpdate(query1);
			mainStatement.executeUpdate(query2);
			mainStatement.executeUpdate(query3);
			System.out.println("Rows deleted");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertBuildingValues(String filename) {
		 try {
			 populate p = new populate();
			 String buildingInfo = p.readCreateFile(filename).toString();
			 String building[]=buildingInfo.split(System.getProperty("line.separator"));
         	for(int i=0;i<building.length;i++) {
         		String buildingElem[]=building[i].split(",");
         		String id= buildingElem[0].trim();
         		String name= buildingElem[1].trim();
         		String points= buildingElem[2].trim();
         		StringBuilder co_od = new StringBuilder("");
         		int j;
             	for(j=3;j<buildingElem.length-1;j++) {
	         		co_od.append(buildingElem[j] + ",");
             	}
             	co_od.append(buildingElem[j]);
             	//System.out.println("INSERT INTO buildings VALUES('"+id+"','"+name+"',"+points+",SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+co_od+")))");
             	mainStatement.executeUpdate("INSERT INTO buildings VALUES('"+id+"','"+name+"',"+points+",SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+co_od+")))");
         	}
			System.out.println("Values inserted!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertStudentValues(String filename) {
		 try {
			 populate p = new populate();
			 String studentInfo = p.readCreateFile(filename).toString();
			 String student[]=studentInfo.split(System.getProperty("line.separator"));
        	for(int i=0;i<student.length;i++) {
        		String studentElem[]=student[i].split(",");
        		String id= studentElem[0].trim();
        		StringBuilder co_od = new StringBuilder("");
            	for(int j=1;j<studentElem.length;j++) {
	         		co_od.append(studentElem[j] + ",");
            	}
            	//System.out.println("INSERT INTO STUDENTS VALUES('"+id+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+co_od+" NULL),NULL,NULL))");
            	mainStatement.executeUpdate("INSERT INTO STUDENTS VALUES('"+id+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+co_od+" NULL),NULL,NULL))");
        	}
			System.out.println("Student Values inserted!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertAnnouncementSystemValues(String filename) {
		try {
			 populate p = new populate();
			 String studentInfo = p.readCreateFile(filename).toString();
			 String student[]=studentInfo.split(System.getProperty("line.separator"));
       	for(int i=0;i<student.length;i++) {
       		String studentElem[]=student[i].split(",");
       		String id= studentElem[0].trim();
       		StringBuilder co_od = new StringBuilder("");
       		int j;
           	for(j=1;j<studentElem.length-1;j++) {
	         		co_od.append(studentElem[j] + ",");
           	}
           	String radius = studentElem[j].trim();
           	//System.out.println("INSERT INTO ANNOUNCEMENTSYSTEMS VALUES('"+id+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+co_od+" NULL),NULL,NULL),"+radius+")");
           	mainStatement.executeUpdate("INSERT INTO ANNOUNCEMENTSYSTEMS VALUES('"+id+"',SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+co_od+" NULL),NULL,NULL),"+radius+")");
       	}
			System.out.println("Announcement system Values inserted!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public void createTables(String query){
		 try
	        {

	            System.out.print( "\n ** Creating new tables ..." );
	           
	            	String queries[]=query.split(";");
	            	for(int i=0;i<queries.length-1;i++) {
	            		//System.out.println("query " +i +": " + queries[i]);
	            		mainStatement.executeUpdate(queries[i]);
	            	}
	            
	            System.out.println("\n Tables and metadata created!");

	        }
		 catch( SQLException e )
	        { System.out.println( " Error 2: " + e ); }
	        catch( Exception e )
	        { System.out.println( " Error 2: " + e ); }
	 }
	 public void deleteTables(String query){
			
	 }
	 public StringBuilder readCreateFile(String filename){
		 try  
		 {  
		 URL url = getClass().getResource(filename);
		 File file = new File(url.getPath());    
		 BufferedReader reader = new BufferedReader( new FileReader (file));
		 String line = null;
		 String ls = System.getProperty("line.separator");

		    while( ( line = reader.readLine() ) != null ) {
		        query.append( line );
		        query.append( ls );
		    }                             
		 }catch(IOException e){
			 System.out.println("Error!!");
		 }  
		 return query; 
	 }
	 public void connectToDB()
	    {
			try
			{
				// loading Oracle Driver
	    		System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
		    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		    	System.out.println(", Loaded.");

				/*String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		    	String userName = "sys as sysdba";
		    	String password = "Hibirdie10";*/
		    	
		    	String URL = "jdbc:oracle:thin:@localhost:1521:hw2";
		        String userName = "system";
		        String password = "hw2";

		    	System.out.print("Connecting to DB...");
		    	mainConnection = DriverManager.getConnection(URL, userName, password);
		    	System.out.println(", Connected!");

	    		mainStatement = mainConnection.createStatement();

	   		}
	   		catch (Exception e)
	   		{
	     		System.out.println( "Error while connecting to DB: "+ e.toString() );
	     		e.printStackTrace();
	     		System.exit(-1);
	   		}
	    }

	 public ArrayList<ArrayList<Double>> retreiveStudentResults(String query) {

			STRUCT point;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects
			ArrayList<ArrayList<Double>> co_od = new ArrayList<ArrayList<Double>>(100);
			try
			{
				try
		    	{
		                                // searches for all tuples
			        mainResultSet = mainStatement.executeQuery(query);
				}
		    	catch( Exception e )
			    { System.out.println( " Error : " + e.toString() ); }

	    	    @SuppressWarnings("deprecation")
				GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

	 	        while(mainResultSet.next())
	    	    {
		    	   
		    	    point = (STRUCT)mainResultSet.getObject(1);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
	      				ArrayList<Double> xy = new ArrayList<Double>();
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						double X = point0.getX();
						double Y = point0.getY();
						//System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"" );
						xy.add(X);
						xy.add(Y);
						co_od.add(xy);
					}
			    
	       	    }
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

		
			return co_od;
	 }
	 public ArrayList<ArrayList<Number>> retrieveASResultsWithID(String query) {


			STRUCT point;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects
			ArrayList<ArrayList<Number>> co_od = new ArrayList<ArrayList<Number>>(100);
			try
			{
				try
		    	{
		                                // searches for all tuples
			        System.out.println("\n ** Selecting all tuples in the table **" );
			        mainResultSet = mainStatement.executeQuery(query);
				}
		    	catch( Exception e )
			    { System.out.println( " Error : " + e.toString() ); }

	    	   @SuppressWarnings("deprecation")
				GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

	 	        while(mainResultSet.next())
	    	    {
		    	    ArrayList xyRad = new ArrayList();
		    	        	    

		    	    point = (STRUCT)mainResultSet.getObject(1);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
	      				
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						double X = point0.getX();
						double Y = point0.getY();
						//System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"," );
						xyRad.add(X);
						xyRad.add(Y);
						
					}
	      			int radius = mainResultSet.getInt(2);
	      			//System.out.print( "\"" + radius + "\"" );
	      			String as_id = mainResultSet.getString( 3 );
	      			xyRad.add(radius);
	      			xyRad.add(as_id);
	      			co_od.add(xyRad);
	      			
			   
	       	    }
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			
			return co_od;
	 }
	 
	 public ArrayList<ArrayList<Number>> retreiveASResults(String query) {

			STRUCT point;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects
			ArrayList<ArrayList<Number>> co_od = new ArrayList<ArrayList<Number>>(100);
			try
			{
				try
		    	{
		                                // searches for all tuples
			        System.out.println("\n ** Selecting all tuples in the table **" );
			        mainResultSet = mainStatement.executeQuery(query);
				}
		    	catch( Exception e )
			    { System.out.println( " Error : " + e.toString() ); }

	    	   @SuppressWarnings("deprecation")
				GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);

	 	        while(mainResultSet.next())
	    	    {
		    	    ArrayList<Number> xyRad = new ArrayList<Number>();
		    	   /* String as_id = mainResultSet.getString( 1 );
		    	    System.out.print( "\"" + as_id + "\"," );*/

		    	    point = (STRUCT)mainResultSet.getObject(1);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
	      				
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						double X = point0.getX();
						double Y = point0.getY();
						//System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"," );
						xyRad.add(X);
						xyRad.add(Y);
						
					}
	      			int radius = mainResultSet.getInt(2);
	      			//System.out.print( "\"" + radius + "\"" );
	      			xyRad.add(radius);
	      			co_od.add(xyRad);
			        
	       	    }
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

	
			return co_od;
	 }
	 
	 public ArrayList<Serializable> retreiveBuildingResults(String query) {

			STRUCT shape;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects
			ArrayList<Serializable> verticesXY = new ArrayList<Serializable>();
			try
			{
				try
		    	{
		                                // searches for all tuples
			        System.out.println("\n ** Selecting all tuples in the table **" );
			        mainResultSet = mainStatement.executeQuery(query);
				}
		    	catch( Exception e )
			    { System.out.println( " Error : " + e.toString() ); }

	    	    System.out.println("\n ** Showing all Tuples ** " );
		       
		  		@SuppressWarnings("deprecation")
				GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, mainConnection);
		  		
	 	        while(mainResultSet.next())
	    	    {
	 	        	
	 	        	ArrayList<Integer> xPoints = new ArrayList<Integer>(100);
	 		        ArrayList<Integer> yPoints=new ArrayList<Integer>(100);
		    	 	int points = mainResultSet.getInt(1);
		    	    //System.out.print( "\"" + points + "\"," );
		    	    verticesXY.add(points);
		    	    shape = (STRUCT)mainResultSet.getObject(2);
					geom = sdoAdapter.importGeometry( shape );
	      			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
	      			{
						oracle.sdoapi.geom.Polygon poly = (oracle.sdoapi.geom.Polygon) geom;
						oracle.sdoapi.geom.CurveString cs = poly.getExteriorRing();
						oracle.sdoapi.geom.LineString line =(oracle.sdoapi.geom.LineString)cs;
						double coord[] = line.getCoordArray();
						//System.out.print( "\"(X = " + X + ", Y = " + Y + ")\"," );
						
						for(int i=0;i<coord.length;i++)
						{
							//System.out.print(" \"" + coord[i] + " \"");
							xPoints.add((int)Math.round(coord[i]));
							i++;
							yPoints.add((int)Math.round(coord[i]));
							
						}
						verticesXY.add(xPoints);
						verticesXY.add(yPoints);
						
					}
	      		
			       
	       	    }
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			
			return verticesXY;
	 }

}
