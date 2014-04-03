import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.Label;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.TextArea;

public class hw2 extends JFrame implements MouseMotionListener,ActionListener, MouseListener{
	private static int querycount=1;
	private boolean isBuilding = false;
	private boolean isStudent = false;
	private boolean isAS = false; 
	private boolean isPointQuery = false;
	private boolean isRangeQuery = false; 
	private boolean isRightMouseClicked=true;
	private boolean isDrawPointRange=false;
	private boolean isDrawPolyRange=false;
	private boolean isDrawSurroundingAS=false;
	private boolean isDrawEmergencyAS=false;
	ArrayList co_odStudent=new ArrayList<>();
	ArrayList co_odAS=new ArrayList<>();
	ArrayList co_odBldg=new ArrayList<>();
	ArrayList co_odStudentNN=new ArrayList<>();
	ArrayList co_odASNN=new ArrayList<>();
	ArrayList co_odBldgNN=new ArrayList<>();
	ArrayList polyXY=new ArrayList();
	int pointCount=0;
	int[] xPoints;
	int[] yPoints;
	int points;
	int orgASX_cood;
	int orgASY_cood;
	String orgASID;
	BufferedImage myPicture;
	JLabel lblX;
	JLabel lblY;
	JRadioButton rdbtnWholeRegion;
	JRadioButton rdbtnPointQuery;
	JRadioButton rdbtnRangeQuery;
	JRadioButton rdbtnSurroundingStudent;
	JRadioButton rdbtnEmergencyQuery;
	JCheckBox chckbxAS;
	JCheckBox chckbxBuilding;
	JCheckBox chckbxStudent;
	Button button;
	TextArea textArea;
	populate p;
	int co_odXPoint;
	int co_odYPoint;
	final HashMap<String,Color> colorMap = new HashMap<String,Color>() {{	   
		   put("a1psa",Color.blue);
		   put("a2ohe",Color.orange);
		   put("a3sgm",Color.magenta);
		   put("a4hnb",Color.green);
		   put("a5vhe",Color.red);
		   put("a6ssc",Color.pink);
		   put("a7helen",Color.yellow);
		}};
	
	private final JPanel imgpanel = new JPanel() {
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.clearRect(0, 0, getWidth(), getHeight() );
		        g.drawImage(myPicture, 0, 0, this);
		       
		        if(isBuilding) {
		        	
		        	Iterator it1 = co_odBldg.iterator();
		        	int i=0;
		        	while(it1.hasNext())
		        	{
		        		
		        		points = (int)it1.next();
		         	    ArrayList x = (ArrayList)it1.next();
		        		Iterator iterx = x.iterator();
		        		ArrayList y = (ArrayList)it1.next();
		        		Iterator itery = y.iterator();
		        		i=0;
		        		xPoints = new int[x.size()];
		        		yPoints = new int[y.size()];
		        		while(iterx.hasNext())
		            	{
		        			
		        			xPoints[i] = (int)iterx.next();
		        			i++;
		            	}
		         	  
		        		i=0;
		        		while(itery.hasNext())
		            	{
		        			yPoints[i] = (int)itery.next();
		        			i++;
		            	}
		        	
		        		Polygon buildingPoly = new Polygon(xPoints,yPoints,points);
		        		g.setColor(Color.YELLOW);
		        		if(isPointQuery)
		        			g.setColor(Color.GREEN);
					    g.drawPolygon(buildingPoly);
		        	}
		        	
		        	if(isPointQuery)	{

			        	Iterator it2 = co_odBldgNN.iterator();
			        	i=0;
			        	while(it2.hasNext())
			        	{
			        		
			        		points = (int)it2.next();
			         	    ArrayList x = (ArrayList)it2.next();
			        		Iterator iterx = x.iterator();
			        		ArrayList y = (ArrayList)it2.next();
			        		Iterator itery = y.iterator();
			        		i=0;
			        		xPoints = new int[x.size()];
			        		yPoints = new int[y.size()];
			        		while(iterx.hasNext())
			            	{
			        			
			        			xPoints[i] = (int)iterx.next();
			        			i++;
			            	}
			         	  
			        		i=0;
			        		while(itery.hasNext())
			            	{
			        			yPoints[i] = (int)itery.next();
			        			i++;
			            	}
			        	
			        		Polygon buildingPoly = new Polygon(xPoints,yPoints,points);
			        		g.setColor(Color.YELLOW);
						    g.drawPolygon(buildingPoly);
			        	}
			        
		        	
		        	}
	        	    
			      	        	
		        }
		        if(isStudent) {
		        	System.out.println("inside stud");
		        	Iterator it = co_odStudent.iterator();
		        	while(it.hasNext())
		        	{
		        	    ArrayList xy = (ArrayList)it.next();
	        	    	g.setColor(Color.GREEN);
	        	    	if(isPointQuery)
		        			g.setColor(Color.GREEN);
				        g.fillRect((int)Math.round((Double)xy.get(0)) - 5, (int)Math.round((Double)xy.get(1)) - 5, 10, 10);
		        	
		        	}
		        	
		        	if(isPointQuery) {
		        		Iterator it1 = co_odStudentNN.iterator();
			        	while(it1.hasNext())
			        	{
			        	    ArrayList xy = (ArrayList)it1.next();
		        	    	g.setColor(Color.YELLOW);
					        g.fillRect((int)Math.round((Double)xy.get(0)) - 5, (int)Math.round((Double)xy.get(1)) - 5, 10, 10);
					        co_odAS.clear();
					        co_odBldg.clear();
					        co_odStudent.clear();
					      
			        	
			        	}
		        	}
		        }
		        if(isAS) {
		        	Iterator it = co_odAS.iterator();
		        	while(it.hasNext())
		        	{
		        	    ArrayList xy = (ArrayList)it.next();
				        g.setColor(Color.RED);
				        if(isPointQuery)
		        			g.setColor(Color.GREEN);
				        int x=(int)Math.round((Double)xy.get(0));
				        int y =(int)Math.round((Double)xy.get(1));
				        int radius=(int)xy.get(2);
				        g.fillRect(x-7, y-7 , 15, 15);
				        g.drawOval(x-radius, y-radius, radius*2, radius*2);
				     
		        	}
		        	if(isPointQuery) {
		        	Iterator it2 = co_odASNN.iterator();
		        	while(it2.hasNext())
		        	{
		        	    ArrayList xy = (ArrayList)it2.next();
				        g.setColor(Color.YELLOW);
				        int x=(int)Math.round((Double)xy.get(0));
				        int y =(int)Math.round((Double)xy.get(1));
				        int radius=(int)xy.get(2);
				        g.fillRect(x-7, y-7 , 15, 15);
				        g.drawOval(x-radius, y-radius, radius*2, radius*2);
				     
		        	}
		        	}
		        }
		        
		        if(isDrawPointRange) {
		        	g.setColor(Color.RED);
		        	g.fillRect(co_odXPoint-2, co_odYPoint-2 , 5, 5);
		        	g.drawOval(co_odXPoint-50, co_odYPoint-50, 50*2, 50*2);
		        	co_odASNN.clear();
		        	co_odBldgNN.clear();
		        	co_odStudentNN.clear();
		        }
		        
		        if(isDrawPolyRange) {
		        	int x1,x2,y1,y2;
		        	g.setColor(Color.RED);
		        	x2=(int)polyXY.get(0);
		        	y2=(int)polyXY.get(1);
		        	for(int i=2;i<polyXY.size();i++){
		        		x1=x2;
		        		y1=y2;
		        		x2=(int)polyXY.get(i);
		        		y2=(int)polyXY.get(++i);
		         	    g.drawLine(x1,y1,x2,y2);  
		        	}
		        	if(isRightMouseClicked) {
		        		g.drawLine((int)polyXY.get((polyXY.size()-2)),(int)polyXY.get((polyXY.size()-1)),(int)polyXY.get(0),(int)polyXY.get(1));
		        	}
		        }
		        
		        if(isDrawSurroundingAS || isDrawEmergencyAS) {
		        	Iterator it2 = co_odASNN.iterator();
		        	while(it2.hasNext())
		        	{
		        	    ArrayList xy = (ArrayList)it2.next();
		        	    if(isDrawSurroundingAS)
		        	    	g.setColor(Color.RED);
		        	    else
		        	    	g.setColor(colorMap.get(orgASID)); 
				        int x=(int)Math.round((Double)xy.get(0));
				        int y =(int)Math.round((Double)xy.get(1));
				        int radius=(int)xy.get(2);
				        g.fillRect(x-7, y-7 , 15, 15);
				        g.drawOval(x-radius, y-radius, radius*2, radius*2);	     
		        	}  
		        }
		    }};
	public hw2() {
		getContentPane().setLayout(null);
		
		rdbtnWholeRegion = new JRadioButton("Whole region");
		rdbtnWholeRegion.setBounds(826, 176, 156, 23);
		getContentPane().add(rdbtnWholeRegion);
		
		rdbtnPointQuery = new JRadioButton("Point Query");
		rdbtnPointQuery.setBounds(826, 201, 156, 23);
		getContentPane().add(rdbtnPointQuery);
		
		rdbtnRangeQuery = new JRadioButton("Range Query");
		rdbtnRangeQuery.setBounds(826, 227, 156, 23);
		getContentPane().add(rdbtnRangeQuery);
		
		rdbtnSurroundingStudent = new JRadioButton("Surrounding Student");
		rdbtnSurroundingStudent.setBounds(826, 253, 156, 23);
		getContentPane().add(rdbtnSurroundingStudent);
		
		rdbtnEmergencyQuery = new JRadioButton("Emergency Query");
		rdbtnEmergencyQuery.setBounds(826, 279, 156, 23);
		getContentPane().add(rdbtnEmergencyQuery);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnWholeRegion);
	    group.add(rdbtnPointQuery);
	    group.add(rdbtnRangeQuery);
	    group.add(rdbtnSurroundingStudent);
	    group.add(rdbtnEmergencyQuery);
	    
		
		chckbxAS = new JCheckBox("AS");
		chckbxAS.setBounds(826, 61, 121, 23);
		getContentPane().add(chckbxAS);
		
		chckbxBuilding = new JCheckBox("Building");
		chckbxBuilding.setBounds(826, 87, 121, 23);
		getContentPane().add(chckbxBuilding);
		
		chckbxStudent = new JCheckBox("Students");
		chckbxStudent.setBounds(826, 113, 121, 23);
		getContentPane().add(chckbxStudent);
		
		
		try {
			URL url = getClass().getResource("map.jpg");
			File file = new File(url.getPath());
			myPicture = ImageIO.read(file);
			imgpanel.setBounds(0, 0, 820, 580); 
			getContentPane().add(imgpanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		button = new Button("Submit Query");
		button.setActionCommand("Submit ");
		button.setBounds(838, 381, 144, 22);
		getContentPane().add(button);
		
		Label label = new Label("Active Feature Type");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setBounds(826, 24, 114, 22);
		getContentPane().add(label);
		
		Label label_1 = new Label("Query");
		label_1.setFont(new Font("Dialog", Font.BOLD, 12));
		label_1.setBounds(826, 148, 114, 22);
		getContentPane().add(label_1);
		
		textArea = new TextArea();
		textArea.setBounds(0, 589, 820, 81);
		getContentPane().add(textArea);
		
		lblX = new JLabel("X :");
		lblX.setBounds(856, 435, 46, 14);
		getContentPane().add(lblX);
		
		lblY = new JLabel("Y : ");
		lblY.setBounds(856, 469, 46, 14);
		getContentPane().add(lblY);
		setTitle("Name: Akanksha Ramesh, USC ID: 4816951862");
		p = new populate();
		p.connectToDB();
		imgpanel.revalidate();
		imgpanel.repaint();
		imgpanel.addMouseMotionListener(this);
		imgpanel.addMouseListener(this);
		button.addActionListener(this);
		rdbtnPointQuery.addActionListener(this);
		rdbtnWholeRegion.addActionListener(this);
		rdbtnEmergencyQuery.addActionListener(this);
		rdbtnRangeQuery.addActionListener(this);
		rdbtnSurroundingStudent.addActionListener(this);
		this.setSize(1100,710);
		setVisible(true);
	}
	public void mouseMoved(MouseEvent e)
	{
	       	lblX.setText("X : " + new Integer(e.getX()).toString());
	       	lblY.setText("Y : " + new Integer(e.getY()).toString());
	   
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==button) {
				
				if(chckbxAS.isSelected())
					isAS=true;
				else
					isAS=false;
				
				if(chckbxBuilding.isSelected())
					isBuilding=true;
				else
					isBuilding=false;
				
				if(chckbxStudent.isSelected())
					isStudent=true;
				else
					isStudent=false;
				executeQueries();
			}
			if(e.getSource()==rdbtnEmergencyQuery || e.getSource()== rdbtnPointQuery  || e.getSource() == rdbtnRangeQuery || e.getSource()== rdbtnSurroundingStudent || e.getSource()== rdbtnWholeRegion) {
				setToDefault();
				imgpanel.repaint();
			}
					
	}
	
	public void executeQueries() {
			
		if(rdbtnWholeRegion.isSelected()) {
			if(isStudent || isAS || isBuilding)
				textArea.append("------------------------------------------Query " + (querycount++) +"---------------------------------------------------");
			isPointQuery=false;
			if(isStudent) {
				co_odStudent.clear();
				String query1 = "select location from STUDENTS";
				co_odStudent = p.retreiveStudentResults(query1);
				textArea.append("\n" + query1 + "\n");
			}
			if(isAS){
				co_odAS.clear();
				String query1="select center, radius from ANNOUNCEMENTSYSTEMS";
				co_odAS = p.retreiveASResults(query1);
				textArea.append("\n" + query1 + "\n");
			}
			if(isBuilding) {
				co_odBldg.clear();
				String query1="select no_of_vertices, shape from BUILDINGS";
				co_odBldg=p.retreiveBuildingResults(query1);
				textArea.append("\n" + query1 + "\n");
			}
			if(isStudent || isAS || isBuilding)
				textArea.append("--------");
			imgpanel.revalidate();
			imgpanel.repaint();
		}
		if(rdbtnPointQuery.isSelected()) {
			isPointQuery=true;
			if(isStudent || isAS || isBuilding)
				textArea.append("------------------------------------------Query " + (querycount++) +"---------------------------------------------------");
			
			if(isStudent) {
				co_odStudent.clear();
				co_odStudentNN.clear();
				String query1 = "SELECT /*+ INDEX(s student_idx) */ "+
						"s.location "+
						"FROM students s "+
						"WHERE SDO_RELATE(s.location, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+ 
						"'mask=ANYINTERACT') = 'TRUE' ";
				co_odStudent = p.retreiveStudentResults(query1);
				textArea.append("\n" + query1 + "\n");
				String query2 = "SELECT /*+ INDEX(s student_idx) */ "+
						"s.location "+  
						"FROM students s "+
						"WHERE SDO_NN(s.location, SDO_geometry(2001, NULL, SDO_point_type("+co_odXPoint+","+co_odYPoint+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE' " +
						"AND SDO_RELATE(s.location, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+ 
						"'mask=ANYINTERACT') = 'TRUE' ";
				co_odStudentNN = p.retreiveStudentResults(query2);
				textArea.append("\n" + query2 + "\n");
				
			}
			if(isAS){
				co_odAS.clear();
				co_odASNN.clear();
				String query1 = "SELECT /*+ INDEX(a as_idx) */ "+
						"a.center, a.radius "+
						"FROM announcementSystems a "+
						"WHERE SDO_RELATE(a.center, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
//						String query1 = "SELECT /*+ INDEX(a as_idx) */ "+
//						"a.center, a.radius "+
//						"FROM announcementSystems a "+
//						"WHERE MDSYS.sdo_within_distance(a.center, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+
//						"'distance=a.radius') = 'TRUE' ";
				co_odAS = p.retreiveASResults(query1);
				textArea.append("\n" + query1 + "\n");
				String query2 = "SELECT /*+ INDEX(a as_idx) */ "+
								"a.center,a.radius "+  
								"FROM announcementSystems a "+
								"WHERE SDO_NN(a.center, SDO_geometry(2001, NULL, SDO_point_type("+co_odXPoint+","+co_odYPoint+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE' " +
								"AND SDO_RELATE(a.center, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
				textArea.append("\n" + query2 + "\n");
				co_odASNN = p.retreiveASResults(query2);
			}
			if(isBuilding) {
				co_odBldg.clear();
				co_odBldgNN.clear();
				String query1  = "SELECT /*+ INDEX(b bldg_idx) */ " +
						"b.no_of_vertices, b.shape "+
						"FROM buildings b "+
						"WHERE SDO_RELATE(b.shape, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
				
				textArea.append("\n" + query1 + "\n");
				co_odBldg=p.retreiveBuildingResults(query1);	
				String query2 = "SELECT /*+ INDEX(b bldg_idx) */ "+
								"b.no_of_vertices, b.shape "+ 
								"FROM buildings b "+
								"WHERE SDO_NN(b.shape, SDO_geometry(2001, NULL, SDO_point_type("+co_odXPoint+","+co_odYPoint+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE' " +
								"AND SDO_RELATE(b.shape, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(co_odXPoint-50) +","+co_odYPoint+","+co_odXPoint+","+(co_odYPoint+50) +","+(co_odXPoint+50) +","+co_odYPoint+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
				textArea.append("\n" + query2 + "\n");
				co_odBldgNN = p.retreiveBuildingResults(query2);
			}
			if(isStudent || isAS || isBuilding)
				textArea.append("------------");
			imgpanel.revalidate();
			imgpanel.repaint();
		}
		
		if(rdbtnRangeQuery.isSelected()) {
			if(isStudent || isAS || isBuilding)
				textArea.append("------------------------------------------Query " + (querycount++) +"---------------------------------------------------");
			
			isRangeQuery=true;
			String polyXYStr="";
			for(int i=0;i<polyXY.size()-1;i++) {
				polyXYStr += new Integer((int)polyXY.get(i)).toString() + ", ";
			}
			polyXYStr+= polyXY.get(polyXY.size()-1).toString();
			if(isStudent) {
				co_odStudent.clear();
				String query1 = "SELECT /*+ INDEX(s student_idx) */ "+
						"s.location "+
						"FROM students s "+
						"WHERE SDO_RELATE(s.location, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+polyXYStr+")), "+ 
						"'mask=ANYINTERACT') = 'TRUE' ";
				co_odStudent = p.retreiveStudentResults(query1);
				textArea.append("\n" + query1 + "\n");
				//System.out.println(query1);
			}
			if(isAS){
				co_odAS.clear();
				String query1 = "SELECT /*+ INDEX(a as_idx) */ "+
						"a.center, a.radius "+
						"FROM announcementSystems a "+
						"WHERE SDO_RELATE(a.center, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+polyXYStr+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
				co_odAS = p.retreiveASResults(query1);
				textArea.append("\n" + query1 + "\n");
				//System.out.println(query1);
				}
			if(isBuilding) {
				co_odBldg.clear();
				String query1  = "SELECT /*+ INDEX(b bldg_idx) */ " +
						"b.no_of_vertices, b.shape "+
						"FROM buildings b "+
						"WHERE SDO_RELATE(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+polyXYStr+")), "+
						"'mask=ANYINTERACT') = 'TRUE' ";
				textArea.append("\n" + query1 + "\n");
				co_odBldg=p.retreiveBuildingResults(query1);
				textArea.append(query1 + "\n");
				//System.out.println(query1);
				}
			
			if(isStudent || isAS || isBuilding)
				textArea.append("-------------");
			imgpanel.revalidate();
			imgpanel.repaint();
		
			}
		
		if(rdbtnSurroundingStudent.isSelected()) {
			
			co_odStudent.clear();
			int x=0,y=0,radius=0;
			Iterator it2 = co_odASNN.iterator();
        	while(it2.hasNext())
        	{
        	    ArrayList xy = (ArrayList)it2.next();
		        x=(int)Math.round((Double)xy.get(0));
		        y =(int)Math.round((Double)xy.get(1));
		        radius=(int)xy.get(2);
        	}
			String query1 = "SELECT /*+ INDEX(s student_idx) */ "+
					"s.location "+
					"FROM students s "+
					"WHERE SDO_RELATE(s.location, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(x-radius) +","+y+","+x+","+(y+radius) +","+(x+radius) +","+y+")), "+ 
					"'mask=inside') = 'TRUE' ";
			textArea.append("\n" + query1 + "\n");
			if(isStudent || isAS || isBuilding)
				textArea.append("-----------------");
			co_odStudent = p.retreiveStudentResults(query1);
			isStudent=true;
			imgpanel.revalidate();
			imgpanel.repaint();
		
		}
		
		if(rdbtnEmergencyQuery.isSelected()) {
			
				
			Iterator it = co_odStudent.iterator();
			Graphics g= imgpanel.getGraphics();
			int newASXco_od = 0, newASYco_od = 0,radius = 0,newRad=0;
			String as_id = "",newASID="";
			ArrayList<String> ASValues = new ArrayList<String>();
        	while(it.hasNext())
        	{
        	    ArrayList xy = (ArrayList)it.next();
        	    int studXco_od = (int)Math.round((Double)xy.get(0));
        	    int studYco_od = (int)Math.round((Double)xy.get(1));
		        String query2 = "SELECT /*+ INDEX(a as_idx) */ "+
						" a.center,a.radius,a.as_id "+  
						"FROM announcementSystems a "+
						"WHERE SDO_NN(a.center, SDO_geometry(2001, NULL, SDO_point_type("+studXco_od+","+studYco_od+",NULL), NULL,NULL),  'sdo_num_res=2') = 'TRUE' " +
						"AND a.as_id <> '" + orgASID + "' ";
		       
		        textArea.append("\n" + query2 + "\n");
		        co_odASNN.clear();
		        co_odASNN = p.retrieveASResultsWithID(query2);
		        Iterator it2 = co_odASNN.iterator();
	        	while(it2.hasNext())
	        	{
	        	    ArrayList xy1 = (ArrayList)it2.next();
	        	    newASXco_od=(int)Math.round((Double)xy1.get(0));
	        	    newASYco_od =(int)Math.round((Double)xy1.get(1));
	        	    newRad=(int)xy1.get(2);
			        newASID=(String) xy1.get(3);
			    			       
	        	}
	        	
	        	/*if(ASValues.contains(newASID))
	        		i=ASValues.indexOf(newASID);
	        	else {
	        		ASValues.add(newASID);
	        		i=ASValues.indexOf(newASID);
	           	}*/
	        	if(colorMap.containsKey(newASID))
	        		g.setColor(colorMap.get(newASID));       	
	        	g.fillRect(newASXco_od-7, newASYco_od-7 , 15, 15);
		        g.drawOval(newASXco_od-newRad, newASYco_od-newRad, newRad*2, newRad*2);
		        g.fillRect(studXco_od-5, studYco_od-5 , 10, 10);
		       
        	}
        	textArea.append("--------------");
		}
		

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(rdbtnPointQuery.isSelected()) {
			if(SwingUtilities.isLeftMouseButton(e)) {
			co_odXPoint = e.getX();
			co_odYPoint = e.getY();
			setToDefault();
			isDrawPointRange=true;
			imgpanel.revalidate();
			imgpanel.repaint();
			System.out.println("first click");
		}
		}
		if(rdbtnRangeQuery.isSelected()) {
			if(SwingUtilities.isLeftMouseButton(e)) {
					if(isRightMouseClicked) {
						setToDefault();
						isRightMouseClicked=false;
						polyXY.clear();
						imgpanel.revalidate();
						imgpanel.repaint();
						isDrawPolyRange=true;
					}
					polyXY.add(e.getX());
					polyXY.add(e.getY());
					if(pointCount>0) {	
						imgpanel.revalidate();
						imgpanel.repaint();
					}
					pointCount++;
				}
						
			if(SwingUtilities.isRightMouseButton(e)) {
				pointCount=0;
				polyXY.add(e.getX());
				polyXY.add(e.getY());
				isRightMouseClicked=true;
				imgpanel.revalidate();
				imgpanel.repaint();
				
			}		
		}
		
		if(rdbtnSurroundingStudent.isSelected() || rdbtnEmergencyQuery.isSelected()) {
			textArea.append("------------------------------------------Query " + (querycount++) +"---------------------------------------------------");
			setToDefault();
			if(rdbtnSurroundingStudent.isSelected()) {
				isDrawSurroundingAS=true;
			}
			if(rdbtnEmergencyQuery.isSelected()) {
				isDrawEmergencyAS=true;
			}
			co_odXPoint = e.getX();
			co_odYPoint = e.getY();
			
			co_odASNN.clear();
			
			String query = "SELECT /*+ INDEX(a as_idx) */ "+
					"a.center,a.radius,a.as_id "+  
					"FROM announcementSystems a "+
					"WHERE SDO_NN(a.center, SDO_geometry(2001, NULL, SDO_point_type("+co_odXPoint+","+co_odYPoint+",NULL), NULL,NULL),  'sdo_num_res=1') = 'TRUE' ";
			
			textArea.append("\n" + query + "\n");
	
			if(rdbtnSurroundingStudent.isSelected()) {
				co_odASNN = p.retreiveASResults(query);
			}
			if(rdbtnEmergencyQuery.isSelected()) {
				co_odASNN = p.retrieveASResultsWithID(query);
				co_odStudent.clear();
				int x=0,y=0,radius=0;
				Iterator it2 = co_odASNN.iterator();
	        	while(it2.hasNext())
	        	{
	        	    ArrayList xy = (ArrayList)it2.next();
			        x=(int)Math.round((Double)xy.get(0));
			        y =(int)Math.round((Double)xy.get(1));
			        radius=(int)xy.get(2);
			        orgASID = (String) xy.get(3);
			        orgASX_cood=x;
			        orgASY_cood=y;
			      
	        	}
				String query1 = "SELECT /*+ INDEX(s student_idx) */ "+
						"s.location "+
						"FROM students s "+
						"WHERE SDO_RELATE(s.location, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+(x-radius) +","+y+","+x+","+(y+radius) +","+(x+radius) +","+y+")), "+ 
						"'mask=inside') = 'TRUE' ";
				
				textArea.append("\n" + query1 + "\n");
				co_odStudent = p.retreiveStudentResults(query1);
				isStudent=true;
			}
			imgpanel.revalidate();
			imgpanel.repaint();
		
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
			
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
		
	public void setToDefault() {
		isBuilding = false;
		isStudent = false;
		isAS = false; 
		isPointQuery = false;
		isRangeQuery = false; 
		isRightMouseClicked=true;
		isDrawPointRange=false;
		isDrawPolyRange=false;
		isDrawSurroundingAS=false;
		isDrawEmergencyAS=false;
		co_odStudent.clear();
		co_odAS.clear();
		co_odBldg.clear();
		co_odStudentNN.clear();
		co_odASNN.clear();
		co_odBldgNN.clear();
	}
}
