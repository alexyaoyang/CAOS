import javax.swing.*;
import java.applet.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.Vector;
import java.text.NumberFormat;

public class CAOS extends JFrame{
	String chosen;
	JFrame results;
	int jcbProcSelect,jcbPartSelect,jtfMemSizeSet,m,remainder,totalIntFrag,totalExtFrag,totalMM,totalProcess,totalResults,hasExtFrag;
	JPanel jpHome,jpCSSetup,jpMMSetup,jpPopup,jpResults;
	JLabel jlWelcome1,jlWelcome2,jlPopupChosen,jlPopupProc,jlPopupPart,jlPopupMemSize,jlUnfit,jlInstruct1,jlInstruct2;
	JButton jbFF,jbBF,jbWF,jbPriority,jbRR,jbSJF,jbHome,jbBack,jbRandom,jbReset,jbGenerate;
	JComboBox jcbProc,jcbPart;
	JFormattedTextField jtfMemSize;
	String[] jcbListProcess = {"Please Select","2","3","4","5"};
	String[] jcbListPartition = {"Please Select","1","2","3","4","5"};
	Font f = new Font("Bradley Hand ITC",Font.BOLD, 88);
	JTable jtProc,jtPart;
	DefaultTableModel dtmProc,dtmPart;
	TableColumn tcProc;
	Random ranNo = new Random();
	boolean hasResults = false;
	
	ArrayList <Integer> MemorySize = new ArrayList<Integer>();
	ArrayList <Integer> ProcessSize = new ArrayList<Integer>();
	ArrayList <Integer> partPosition = new ArrayList<Integer>();
	ArrayList <Integer> intFrag = new ArrayList<Integer>();
	ArrayList <Integer> partComparison = new ArrayList<Integer>();
	ArrayList <Integer> Part1Process = new ArrayList<Integer>();
	ArrayList <Integer> Part2Process = new ArrayList<Integer>();
	ArrayList <Integer> Part3Process = new ArrayList<Integer>();
	ArrayList <Integer> Part4Process = new ArrayList<Integer>();
	ArrayList <Integer> Part5Process = new ArrayList<Integer>();
	ArrayList <Integer> Unfittable = new ArrayList<Integer>();
	ArrayList <String> SJFRunTru = new ArrayList<String>();
	ArrayList <Integer> SJFSTRunTru = new ArrayList<Integer>();
	
	Draw draw;
	
	int[] AT,ST,P,TT,PI;
	float[] TAT,WT;
	String[] PID;
	
	public CAOS(){
		setSize(500,480);
		setTitle("Home");
    	setLocation(100,100);
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new BorderLayout(10,10));
    	setResizable(false);
    	//setUndecorated(true);
    	
    	jpHome = new JPanel();
    	
    	f = new Font("Arial",Font.BOLD, 32);
        jlWelcome1 = new JLabel("CAOS CPU Scheduling &");
        jlWelcome1.setFont(f);
        jlWelcome1.setForeground(Color.black);
        
        jlWelcome2 = new JLabel("Memory Allocation Algorithms");
        jlWelcome2.setFont(f);
        jlWelcome2.setForeground(Color.black);
        
        jlInstruct1 = new JLabel("Please choose from one of the Algorithms below to proceed");
        jlInstruct2 = new JLabel("or place your mouse over for individual descriptions.");
        
        jbFF = new JButton("First Fit");
        jbFF.setPreferredSize(new Dimension(240, 100));
        jbFF.setToolTipText("First Fit - Memory Allocation Algorithms. Places process in the first possible partition.");
        jbFF.addActionListener(new jblisten());//assign all button to same Action Listener
        jbFF.setActionCommand("First Fit"); //set Action Command for checking which button is pressed
        
        jbWF = new JButton("Worst Fit");
        jbWF.setPreferredSize(new Dimension(240, 100));
        jbWF.setToolTipText("Worst Fit - Memory Allocation Algorithm. Places process in the biggest partition.");
        jbWF.addActionListener(new jblisten());
        jbWF.setActionCommand("Worst Fit");
        
        jbBF = new JButton("Best Fit");
        jbBF.setPreferredSize(new Dimension(240, 100));
        jbBF.setToolTipText("Best Fit - Memory Allocation Algorithms. Places process in the smallest possible partition.");
        jbBF.addActionListener(new jblisten());
        jbBF.setActionCommand("Best Fit");
        
        jbPriority = new JButton("Priority (Pre-Emptive)");
        jbPriority.setPreferredSize(new Dimension(240, 100));
        jbPriority.setToolTipText("Priority - CPU Scheduling Algorithms. Runs processes according to process priority, starting from smaller priorities.");
        jbPriority.addActionListener(new jblisten());
        jbPriority.setActionCommand("Priority");
        
        jbRR = new JButton("Round Robin (Natural Pre-Emptive)");
        jbRR.setEnabled(false);
        jbRR.setPreferredSize(new Dimension(240, 100));
        jbRR.setToolTipText("Round Robin - CPU Scheduling Algorithm. Fair to all processes as it allows each process to have a slot of time.");
        jbRR.addActionListener(new jblisten());
        jbRR.setActionCommand("Round Robin");
        
        jbSJF = new JButton("Shortest Job First (Pre-Emptive)");
        jbSJF.setPreferredSize(new Dimension(240, 100));
        jbSJF.setToolTipText("Shortest Job First - CPU Scheduling Algorithm. Runs processes according to process Service/Burst time.");
        jbSJF.addActionListener(new jblisten());
        jbSJF.setActionCommand("Shortest Job First");
        
        jpHome.add(jlWelcome1, BorderLayout.PAGE_START);
        jpHome.add(jlWelcome2, BorderLayout.PAGE_START);
        jpHome.add(jlInstruct1, BorderLayout.PAGE_START);
        jpHome.add(jlInstruct2, BorderLayout.PAGE_START);
        jpHome.add(jbBF,  BorderLayout.CENTER);
        jpHome.add(jbPriority,  BorderLayout.LINE_END);
       	jpHome.add(jbFF,  BorderLayout.LINE_START);
       	jpHome.add(jbSJF,  BorderLayout.LINE_END);
       	jpHome.add(jbWF,  BorderLayout.LINE_START);
       	jpHome.add(jbRR,  BorderLayout.LINE_END);
    
        add(jpHome);
        jpHome.setVisible(true);
	}
	
	public class jblisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			chosen = e.getActionCommand();
			
			jpHome.setVisible(false);
			
			jpPopup = new JPanel();
			
			setTitle("Process Number Selection");
    		setLocation(100,100);
    		setSize(350,250);
    		if(chosen=="Shortest Job First"){
    		setSize(400,250);	
    		}
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setLayout(new BorderLayout(10,10));
    		setResizable(false);
    		
    		f = new Font("Arial",Font.PLAIN, 48);
			jlPopupChosen = new JLabel(chosen);
			jlPopupChosen.setFont(f);
			
			jbBack = new JButton("Back");
			jbBack.setPreferredSize(new Dimension(200, 50));
			jbBack.setToolTipText("Go back to Home");
			jbBack.addActionListener(new jbBacklisten());
			
			f = new Font("Bradley Hand ITC",Font.BOLD, 28);
			
			jpPopup.add(jlPopupChosen, BorderLayout.CENTER);
			jpPopup.add(jbBack);
			
    		if ((chosen=="First Fit")||(chosen=="Best Fit")||(chosen=="Worst Fit")){
				setTitle("Process & Partition Number Selection");
				setSize(350,440);

				jlPopupMemSize = new JLabel("Total Size of Memory?");
				jlPopupMemSize.setFont(f);
				
				jtfMemSize = new JFormattedTextField(NumberFormat.getIntegerInstance());
				jtfMemSize.setToolTipText("Enter the maximum amount of memory space.");
				jtfMemSize.setPreferredSize(new Dimension(200, 50));

				jlPopupPart = new JLabel("Number of Partitions?");
				jlPopupPart.setFont(f);
		
				jcbPart = new JComboBox(jcbListPartition);
				jcbPart.addActionListener(new jcbMMlisten());
				jcbPart.setToolTipText("Please choose number of partitions.");
				jcbPart.setPreferredSize(new Dimension(200, 50));

				jpPopup.add(jlPopupMemSize, BorderLayout.CENTER);
				jpPopup.add(jtfMemSize, BorderLayout.CENTER);
				jpPopup.add(jlPopupPart, BorderLayout.CENTER);
				jpPopup.add(jcbPart, BorderLayout.CENTER);
				}
	
			jlPopupProc = new JLabel("Number of Processes?");
			jlPopupProc.setFont(f);
			
			jcbProc = new JComboBox(jcbListProcess);
			jcbProc.addActionListener(new jcbMMlisten());
			jcbProc.addActionListener(new jcbCSlisten());
			jcbProc.setToolTipText("Please choose number of processes.");
			jcbProc.setPreferredSize(new Dimension(200, 50));
				
			jpPopup.add(jlPopupProc, BorderLayout.CENTER);
			jpPopup.add(jcbProc, BorderLayout.CENTER);

			add(jpPopup);
			jpPopup.setVisible(true);
				
		}}
		
	public class jcbCSlisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (((chosen=="Priority")||(chosen=="Shortest Job First")||(chosen=="Round Robin"))&&jcbProc.getSelectedIndex()!=0){
				jpPopup.setVisible(false);
				jcbProcSelect = (int)jcbProc.getSelectedIndex()+1;
				System.out.println("CPU Scheduling Processes chosen: "+jcbProcSelect);
				
				jpCSSetup = new JPanel();
					
				if(jcbProcSelect==2){//set window size according to processes selected
    			setSize(500,220);}
    			if(jcbProcSelect==3){
    			setSize(500,270);}
    			if(jcbProcSelect==4){
    			setSize(500,320);}
    			if(jcbProcSelect==5){
    			setSize(500,370);}
					
				setTitle(chosen+" Setup");
    			setLocation(0,100);
    			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			setLayout(new BorderLayout(10,10));
    			setResizable(false);
    		
				dtmProc = new DefaultTableModel();
				if (chosen=="Priority"){
				dtmProc.addColumn("Process ID");
				dtmProc.addColumn("Arrival Time");
				dtmProc.addColumn("Burst Time");
				dtmProc.addColumn("Priority");}
				if (chosen=="Shortest Job First"){
				dtmProc.addColumn("Process ID");
				dtmProc.addColumn("Arrival Time");
				dtmProc.addColumn("Burst Time");}
				if (chosen=="Round Robin"){
				dtmProc.addColumn("Process ID");
				dtmProc.addColumn("Arrival Time");
				dtmProc.addColumn("Burst Time");}
				
				for (int i = 0; i < jcbProcSelect; i++) {	//insert row according to value from jcbCSSelect
    			dtmProc.insertRow(i, new Object[]{"P"+(i)});
   				}
				
    			jtProc = new JTable(dtmProc);
				jtProc.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
    			jtProc.setRowHeight(50);
    			
    			for (int i = 0; i < jtProc.getColumnCount(); i++) {
    			tcProc = jtProc.getColumnModel().getColumn(i);
    			if(jtProc.getColumnCount()==4)
       			tcProc.setPreferredWidth(120);
       			else
       			tcProc.setPreferredWidth(160);
   				}
				
    			jbHome = new JButton("Home");
    			jbHome.setPreferredSize(new Dimension(115, 60));
    			jbHome.setToolTipText("Go back to Home.");
    			jbHome.addActionListener(new jbHomelisten());
    			jbRandom = new JButton("Random");
    			jbRandom.setPreferredSize(new Dimension(115, 60));
    			jbRandom.setToolTipText("Fills table with random data.");
    			jbRandom.addActionListener(new jbRandomlisten());
 				jbReset = new JButton("Reset");
 				jbReset.setPreferredSize(new Dimension(115, 60));
 				jbReset.setToolTipText("Clears Table.");
 				jbReset.addActionListener(new jbResetlisten());
 				jbGenerate = new JButton("Generate");
 				jbGenerate.setPreferredSize(new Dimension(115, 60));
 				jbGenerate.setToolTipText("Generate Gantt Chart.");
 				jbGenerate.addActionListener(new jbGeneratelisten());
 		
				jpCSSetup.add(jtProc.getTableHeader(), BorderLayout.PAGE_START);
				jpCSSetup.add(jtProc, BorderLayout.CENTER);
				jpCSSetup.add(jbHome, BorderLayout.PAGE_END);
				jpCSSetup.add(jbRandom, BorderLayout.PAGE_END);
				jpCSSetup.add(jbReset, BorderLayout.PAGE_END);
				jpCSSetup.add(jbGenerate, BorderLayout.PAGE_END);
				add(jpCSSetup);
				jpCSSetup.setVisible(true);
		}}}
	public class jcbMMlisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (((chosen=="First Fit")||(chosen=="Best Fit")||(chosen=="Worst Fit"))&&jcbProc.getSelectedIndex()!=0&&jcbPart.getSelectedIndex()!=0&&jtfMemSize.getValue()!=null){
				
				jcbProcSelect = (int)jcbProc.getSelectedIndex()+1;
				jcbPartSelect = (int)jcbPart.getSelectedIndex();
				String o = jtfMemSize.getValue().toString();;
				jtfMemSizeSet = Integer.parseInt(o);
				System.out.println(jcbPartSelect);
				System.out.println(jcbProcSelect);
				System.out.println(jtfMemSizeSet);
				
				if(jtfMemSizeSet<=50){
				jcbPart.setSelectedIndex(0);
				jcbProc.setSelectedIndex(0);
				jtfMemSize.setValue(null);
				JOptionPane.showMessageDialog(null, "Total Memory Size cannot be smaller than 10.");
				}
				
				else if(jtfMemSizeSet>50){
					
				jpPopup.setVisible(false);
				
				jpMMSetup = new JPanel();
				
				int sizeSetter;
   				if(jcbProcSelect>jcbPartSelect)
   					sizeSetter = jcbProcSelect;
   				else
   					sizeSetter = jcbPartSelect;
   				if(sizeSetter==2){//set window size according to processes selected
   					setSize(500,220);}
   				if(sizeSetter==3){
   					setSize(500,270);}
   				if(sizeSetter==4){
   					setSize(500,320);}
   				if(sizeSetter==5){
   					setSize(500,370);}
				
				setTitle(chosen+" Setup");
   				setLocation(100,100);
   				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   				setLayout(new BorderLayout(10,10));
   				setResizable(false);
				
				dtmPart = new DefaultTableModel();
				dtmPart.addColumn("Partition ID");
				dtmPart.addColumn("Size");
				
				dtmProc = new DefaultTableModel();
				dtmProc.addColumn("Process ID");
				dtmProc.addColumn("Size");
				
				for (int i = 0; i < jcbProcSelect; i++) {	//insert row according to value from jcbCSSelect
   					dtmProc.insertRow(i, new Object[]{"P"+(i)});
  					}
  					
  				for (int i = 0; i < jcbPartSelect; i++) {	//insert row according to value from jcbCSSelect
   					dtmPart.insertRow(i, new Object[]{"Partition"+(i+1)});
  					}
				
				jtProc = new JTable(dtmProc);
				jtProc.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
   				jtProc.setRowHeight(50);
				
				jtPart = new JTable(dtmPart);
				jtPart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
   				jtPart.setRowHeight(50);
   				
   				for (int i = 0; i < 2; i++) {
   				TableColumn column = jtPart.getColumnModel().getColumn(i);
   				column.setPreferredWidth(120);
  				}
					 
				for (int i = 0; i < 2; i++) {
    			TableColumn column = jtProc.getColumnModel().getColumn(i);
       			column.setPreferredWidth(120);
   				}
    				
    			jbHome = new JButton("Home");
    			jbHome.setPreferredSize(new Dimension(115, 60));
    			jbHome.setToolTipText("Go back to Home.");
    			jbHome.addActionListener(new jbHomelisten());
    			jbRandom = new JButton("Random");
    			jbRandom.setPreferredSize(new Dimension(115, 60));
    			jbRandom.setToolTipText("Fills table with random data.");
    			jbRandom.addActionListener(new jbRandomlisten());
 				jbReset = new JButton("Reset");
 				jbReset.setPreferredSize(new Dimension(115, 60));
 				jbReset.setToolTipText("Clears Table.");
 				jbReset.addActionListener(new jbResetlisten());
 				jbGenerate = new JButton("Generate");
 				jbGenerate.setPreferredSize(new Dimension(115, 60));
 				jbGenerate.setToolTipText("Generate Memory Allocation Cylinder.");
 				jbGenerate.addActionListener(new jbGeneratelisten());
 			
				jpMMSetup.add(jtPart.getTableHeader(), BorderLayout.PAGE_START);
				jpMMSetup.add(jtProc.getTableHeader(), BorderLayout.PAGE_START);
				jpMMSetup.add(jtPart, BorderLayout.CENTER);
				jpMMSetup.add(jtProc, BorderLayout.CENTER);
				jpMMSetup.add(jbHome, BorderLayout.PAGE_END);
				jpMMSetup.add(jbRandom, BorderLayout.PAGE_END);
				jpMMSetup.add(jbReset, BorderLayout.PAGE_END);
				jpMMSetup.add(jbGenerate, BorderLayout.PAGE_END);
				add(jpMMSetup);
				jpMMSetup.setVisible(true);
			}}}}
			
	public class jbHomelisten implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			if(hasResults)
			if(results.isVisible()==true)
				results.setVisible(false);
			if(((chosen=="Priority")||(chosen=="Shortest Job First")||(chosen=="Round Robin"))&&jcbProc.getSelectedIndex()!=0){
				System.out.println("Closing CPU Window");
				jpCSSetup.setVisible(false);}
			if(((chosen=="First Fit")||(chosen=="Best Fit")||(chosen=="Worst Fit"))&&jcbProc.getSelectedIndex()!=0&&jcbPart.getSelectedIndex()!=0&&jtfMemSize.getValue()!=null){
				System.out.println("Closing MM Window");
				jpMMSetup.setVisible(false);
				}
								
			setTitle("Home");
   			setLocation(100,100);
   			setSize(500,480);
   			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   			setLayout(new BorderLayout(10,10));
   			setResizable(false);
    	
			jpHome.setVisible(true);
		}}
		
		public class jbBacklisten implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			jpPopup.setVisible(false);
					
			setTitle("Home");
   			setLocation(100,100);
   			setSize(500,480);
   			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   			setLayout(new BorderLayout(10,10));
   			setResizable(false);
    	
			jpHome.setVisible(true);
		}}
			
	public class jbRandomlisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int rn;
			if((chosen=="Priority")||(chosen=="Shortest Job First")||(chosen=="Round Robin")){
				for (int p = 0; p < jcbProcSelect; p++) {
					for (int c = 1; c < 3; c++) {
						rn = ranNo.nextInt(9)+1;
						jtProc.setValueAt(rn,p,c);
				}}
				if(chosen=="Priority"){
					ArrayList<Object> priorities = new ArrayList<Object>();
					for (int i = 0; i < jcbProcSelect; i++) {
						priorities.add(i+1);
						}
					
					for (int i=1; i<priorities.size(); i++) {
    				int randomPosition = ranNo.nextInt(priorities.size());//get random position
    				Object temp = priorities.get(i);//retrieve to temp
    				priorities.set(i,(Object)priorities.get(randomPosition));//set to new position
    				priorities.set(randomPosition,(Object)temp);}//set old position value
				
				for (int i = 0; i < jcbProcSelect; i++) {
				jtProc.setValueAt(priorities.get(i),i,3);}}
			}
			if((chosen=="Worst Fit")||(chosen=="First Fit")||(chosen=="Best Fit")){
			int biggest = 0;
			for(int p = 0; p < jcbPartSelect; p++) {
				rn = ranNo.nextInt(jtfMemSizeSet/jcbPartSelect);
				if(rn<((jtfMemSizeSet/jcbPartSelect)/10)){
				rn=(rn+ranNo.nextInt(jtfMemSizeSet/jcbPartSelect))/2;}
				jtPart.setValueAt(rn,p,1);
				if(p==0)
					biggest = rn;
				if(biggest<rn&&p!=0)
					biggest = rn;
				}
			for(int p = 0; p < jcbProcSelect; p++) {
				rn = ranNo.nextInt(biggest);								
				jtProc.setValueAt(rn,p,1);	
			}}}}
	
	public class jbResetlisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if((chosen=="Priority")||(chosen=="Shortest Job First")||(chosen=="Round Robin")){
			for (int p = 0; p < jcbProcSelect; p++) {
					for (int c = 1; c < jtProc.getColumnCount(); c++) {
						jtProc.setValueAt("",p,c);
				}}}
			if((chosen=="Worst Fit")||(chosen=="First Fit")||(chosen=="Best Fit")){
			for (int c = 0; c < jcbPartSelect; c++) {
						jtPart.setValueAt("",c,1);
						}
			for (int c = 0; c < jcbProcSelect; c++) {
						jtProc.setValueAt("",c,1);
						}	
			}}}
			
	public void MMAlgorithm(){
		
				ProcessSize.clear();
				MemorySize.clear();
				partPosition.clear();
				intFrag.clear();
				partComparison.clear();
				Unfittable.clear();
				Part1Process.clear();
				Part2Process.clear();
				Part3Process.clear();
				Part4Process.clear();
				Part5Process.clear();
				totalMM=0;
				totalProcess=0;
				hasExtFrag =0;
        		
        		for (int p = 0; p < jcbProcSelect; p++) {
						String o = jtProc.getValueAt(p, 1).toString();
						int i = Integer.parseInt(o);
						ProcessSize.add(i);
						totalProcess = totalProcess+i;
				}
				
				for (int p = 0; p < jcbPartSelect; p++) {
						String o = jtPart.getValueAt(p, 1).toString();
						int i = Integer.parseInt(o);
						MemorySize.add(i);
						partPosition.add(i);
						intFrag.add(0);
						partComparison.add(i);
						totalMM = totalMM+i;
				}

				if(chosen=="Worst Fit"){
				Collections.sort(MemorySize, Collections.reverseOrder());}
				else if(chosen=="Best Fit"){
				Collections.sort(MemorySize);}	
		
		for (int p = 0; p < ProcessSize.size(); p++){
			remainder = MemorySize.get(m) - ProcessSize.get(p);
			int orgPos = partPosition.indexOf(MemorySize.get(m));
			int curProc = ProcessSize.get(p);
			int curPart = MemorySize.get(m);
			if (remainder >= 0){
				MemorySize.set(m,remainder);
				partPosition.set(orgPos,remainder);
				intFrag.set(orgPos,remainder);
				if(chosen=="Worst Fit"){
				Collections.sort(MemorySize, Collections.reverseOrder());}
				else if(chosen=="Best Fit"){
				Collections.sort(MemorySize);}

				if(orgPos==0)
					Part1Process.add(p);
				if(orgPos==1)
					Part2Process.add(p);
				if(orgPos==2)
					Part3Process.add(p);
				if(orgPos==3)
					Part4Process.add(p);
				if(orgPos==4)
					Part5Process.add(p);
						
				m=0;
			} 
			else if(remainder<0&&((m+1)<MemorySize.size())){
				m++;
				p--;
			}
			else if(remainder<0&&((m+1)>=MemorySize.size())){
				Unfittable.add(p);
				hasExtFrag = 1;
				m=0;
			}}
			
			totalIntFrag = 0;
			totalExtFrag = 0;
			totalResults = 0;
			
			for (int k=0;k<MemorySize.size();k++){
				totalResults = totalResults+partPosition.get(k);
			}
			for(int k=0;k<MemorySize.size();k++){
				int partition = partPosition.get(k);
				int comparison = partComparison.get(k);
				
				totalIntFrag = totalIntFrag+intFrag.get(k);
				if(partition==comparison&&hasExtFrag==1){
					totalExtFrag = totalExtFrag+comparison;
				}
			}
			}
			
	public class GanttChart extends Canvas{
    	public void paint(Graphics g){
    		AT= new int[jcbProcSelect];
    		ST= new int[jcbProcSelect];
    		P= new int[jcbProcSelect];
    		PI = new int[jcbProcSelect];
    		PID= new String[jcbProcSelect];
    		TAT= new float[jcbProcSelect]; 
    		WT= new float[jcbProcSelect];
    		int start=10;
    		int currentTime=0;
    		boolean allDone=false;
    		boolean foundProcess=false;
    		int minTime=1;
    		int Pri=1;
    		int lowestPri=0;
    		int minPro=0;
    		int completedJobs=0;
    		int StartPoint = 0;
    		int StartMinus2 = 0;
    		int heightPlus = 0;
    		
    		if(chosen=="Priority"){
    			for(int t=0;t<jcbProcSelect; t++)
    				{
    					AT[t] = Integer.parseInt(jtProc.getValueAt(t,1).toString());
		    			ST[t] = Integer.parseInt(jtProc.getValueAt(t,2).toString());
		    			P[t]  = Integer.parseInt(jtProc.getValueAt(t,3).toString());
		    			PID[t]= "P"+Integer.toString(t);
		    			PI[t]= t;
		    			//System.out.println(	"Arrive Time: "+AT[t]+" Service Time: "+ST[t]+" Priority: "+P[t]+" Process ID: "+PID[t]);
    				}
    				while(!allDone)
    				{
    					Vector <Integer> lowP = new Vector <Integer> (); 
						Vector <Integer> lowPID = new Vector <Integer> (); 
						Vector <Integer> lowAT = new Vector <Integer> ();
						Vector <Integer> lowST = new Vector <Integer> ();
						boolean processExist=false;
    					while(lowPID.size()==0)
    					{
    						for(int i=0;i<jcbProcSelect; i++)
    						{
    						
    							if((AT[i]<=currentTime)&&(ST[i]!=0))//if process has arrived and still needs to be done
    							{
    								processExist=true;
    							}
    						}
    							if(processExist)
    							{
    								for(int i=0;i<jcbProcSelect; i++)
    								{
    									if((AT[i]<=currentTime)&&(P[i]!=0)&&(P[i]==Pri))//Check if process has arrived, Priority not = 0, Priority = current priority.
    									{
    										lowP.add(P[i]);
    										lowPID.add(i);
    										lowAT.add(AT[i]);
    										lowST.add(ST[i]);
    									}						
    								}
    								if(lowPID.size()==0)
    									Pri++;
    							}
    							else
    							{
    								g.setColor(Color.BLACK);
    								g.drawRect(currentTime*30+20,10,30,20);
    								g.drawString(Integer.toString(currentTime),currentTime*30+20,42);
    								currentTime++;	
    							}
    							
    					}
    					if(lowPID.size()==1)//do the process, minus service time, check if done, current time minus 1
							{
								foundProcess = true;
								for(int l=0;l<jcbProcSelect;l++)
								{
									if(l==lowPID.elementAt(0))
									{
									
										int newST = 0; 
										newST = ST[l]; 
										newST--; 
										ST[l]=newST; 
									
										if(newST == 0) 
										{
											TAT[l]=(currentTime+1-AT[l]);
											WT[l]=(TAT[l]-Integer.parseInt(jtProc.getValueAt(l,2).toString()));
											completedJobs++; 
											P[l]=0;
											
										}
										StartPoint = currentTime;
										StartMinus2 = 0;
										if(currentTime<15){
										}
							    		if(currentTime>15&&currentTime<=31){
							    			StartPoint -=15;
							    			heightPlus = 45;
							    			StartMinus2 = 30;
										}
										else if(currentTime>31&&currentTime<=47){
							    			StartPoint -=31;
							    			heightPlus = 90;
							    			StartMinus2 = 30;
										}
										else if(currentTime>47){
							    			StartPoint -=47;
							    			heightPlus = 135;
							    			StartMinus2 = 30;
										}
										
										int ProID = PI[l];

										if(ProID==0)
										g.setColor(Color.RED);
										else if(ProID==1)
										g.setColor(Color.BLUE);
										else if(ProID==2)
										g.setColor(Color.GREEN);	
										else if(ProID==3)
										g.setColor(Color.ORANGE);
										else if(ProID==4)
										g.setColor(Color.MAGENTA);
										
										if(ST[l]==0)
										g.drawString("Done!",StartPoint*30+20-StartMinus2,10+heightPlus);
										g.drawString(PID[l],StartPoint*30+22-StartMinus2,22+heightPlus);
							    		g.drawRect(StartPoint*30+20-StartMinus2,10+heightPlus,30,20);
							    		g.drawString(Integer.toString(currentTime),StartPoint*30+20-StartMinus2,42+heightPlus);
								}
							}}
							
    					currentTime++; 				    	
				    	minPro = 0;
				    	Pri=1;
				    	foundProcess = false; 
				    	
				    	if(completedJobs == jcbProcSelect) 
				    	{
				    		allDone = true; 
				    		
				    		float tempTAT=0;
				    		float tempWT=0;
				    		for(int a=0; a<jcbProcSelect;a++)
				    		{
				    			tempTAT+=TAT[a];
				    			tempWT+=WT[a];
				    		}
				    		g.setColor(Color.BLUE);
				    		g.drawString("Average Turnaround Time: ",30,heightPlus+90);
				    		g.drawString(Float.toString(tempTAT/jcbProcSelect),180,heightPlus+90);
				    		g.drawString("Average Waiting Time: ",30,heightPlus+70);
				    		g.drawString(Float.toString(tempWT/jcbProcSelect),160,heightPlus+70);
				    	}
    					
    				}
    		}
    		else if(chosen=="Shortest Job First"){
    			SJFRunTru.clear();
    			SJFSTRunTru.clear();
    			
    			sjfpre sjf = new sjfpre();
				
				for(int p=0;p<jcbProcSelect;p++){
				int a = Integer.parseInt(jtProc.getValueAt(p,1).toString());				
				int b = Integer.parseInt(jtProc.getValueAt(p,2).toString());
				sjf.addProc(p,a,b);}
				
				sjf.SJFAlgo();
				
				SJFRunTru = sjf.RunTru;
				SJFSTRunTru = sjf.STRunTru;
				
				String ProID = null;
				int ServT = 0;
				
				for(int id = 0;id<SJFRunTru.size()-2;id++){
				
				ProID = SJFRunTru.get(id);
				ServT = SJFSTRunTru.get(id);
				//System.out.println(ServT+" "+ProID);
				
				if(ProID==null){
				ProID="";
				g.setColor(Color.BLACK);}
				else if(ProID.equals("P0"))
				g.setColor(Color.RED);
				else if(ProID.equals("P1"))
				g.setColor(Color.BLUE);
				else if(ProID.equals("P2"))
				g.setColor(Color.GREEN);	
				else if(ProID.equals("P3"))
				g.setColor(Color.ORANGE);
				else if(ProID.equals("P4"))
				g.setColor(Color.MAGENTA);
				
				
				StartPoint = id;
				StartMinus2 = 0;
				
				if(id<15){
				}
				if(id>15&&id<=31){
				StartPoint -=15;
				heightPlus = 45;
				StartMinus2 = 30;
				}
				else if(id>31&&id<=47){
	    		StartPoint -=31;
    			heightPlus = 90;
    			StartMinus2 = 30;
				}
				else if(id>47){
	   			StartPoint -=47;
	   			heightPlus = 135;
	   			StartMinus2 = 30;
				}
	
				if(ServT==0)
				g.drawString("Done!",StartPoint*30+20-StartMinus2,10+heightPlus);
				g.drawString(ProID,StartPoint*30+22-StartMinus2,22+heightPlus);
				g.drawRect(StartPoint*30+20-StartMinus2,10+heightPlus,30,20);
				g.drawString(""+id,StartPoint*30+20-StartMinus2,42+heightPlus);
				}
				g.setColor(Color.BLUE);
				g.drawString("Average Waiting Time is: "+SJFRunTru.get(SJFRunTru.size()-2),20,70+heightPlus);
				g.drawString("Average Turnaround Time is: "+SJFRunTru.get(SJFRunTru.size()-1),20,82+heightPlus);
				}
    		}
    	}	
				
	public class Draw extends Canvas{
    	public void paint(Graphics g){
    		
    		if((chosen=="Worst Fit")||(chosen=="First Fit")||(chosen=="Best Fit")){
    		g.drawOval(50, 0, 200, 20);
    		g.drawArc(50,500,200,20,0,-180);//total cylinder 500
    		g.drawLine(50,10,50,510);
    		g.drawLine(250,10,250,510); //string whole cylinder 515
    		
    		int height =0;
    		int partSize =0;
    		int partLength =0;
    		int proHeight =0;
    		int accuPartSize = 0;
    		int accuProcSize = 0;
    		int curProSize = 0;

    		for(int m=0;m<partComparison.size();m++){
    			
    		partSize =partComparison.get(m);
    		partLength = (500*partSize/totalMM);
    		height = height+(int)partLength;
				
    		if(m==0){
    		for(int pro=0;pro<Part1Process.size();pro++){
    		curProSize = ProcessSize.get(Part1Process.get(pro));
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		g.drawString("P"+Part1Process.get(pro)+" Size:"+curProSize,110,proHeight+25);
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		if(partPosition.get(m)>0||pro!=Part1Process.size()-1){
    		g.drawArc(50,proHeight,200,20,0,-180);
    		accuProcSize = accuProcSize + curProSize;
    		g.drawString(""+accuProcSize,252,proHeight+15);
    		}
    		}
    		g.drawString("Empty Space Left: "+partPosition.get(m),90,(height-proHeight)/2+proHeight+25);
    		proHeight = height;
    		}
    		
    		if(m==1){
    		for(int pro=0;pro<Part2Process.size();pro++){
    		curProSize = ProcessSize.get(Part2Process.get(pro));
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		g.drawString("P"+Part2Process.get(pro)+" Size:"+curProSize,110,proHeight+25);
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		if(partPosition.get(m)>0||pro!=Part2Process.size()-1){
    		g.drawArc(50,proHeight,200,20,0,-180);
    		accuProcSize = accuProcSize + curProSize;
    		g.drawString(""+accuProcSize,252,proHeight+15);
    		}
    		}
    		g.drawString("Empty Space Left: "+partPosition.get(m),90,(height-proHeight)/2+proHeight+25);
    		proHeight = height;
    		}
    		
    		if(m==2){
    		for(int pro=0;pro<Part3Process.size();pro++){
    		curProSize = ProcessSize.get(Part3Process.get(pro));
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		g.drawString("P"+Part3Process.get(pro)+" Size:"+curProSize,110,proHeight+25);
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		if(partPosition.get(m)>0||pro!=Part3Process.size()-1){
    		g.drawArc(50,proHeight,200,20,0,-180);
    		accuProcSize = accuProcSize + curProSize;
    		g.drawString(""+accuProcSize,252,proHeight+15);
    		}
    		}
    		g.drawString("Empty Space Left: "+partPosition.get(m),90,(height-proHeight)/2+proHeight+25);
    		proHeight = height;}
    		
    		if(m==3){
    		for(int pro=0;pro<Part4Process.size();pro++){
    		curProSize = ProcessSize.get(Part4Process.get(pro));
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		g.drawString("P"+Part4Process.get(pro)+" Size:"+curProSize,110,proHeight+25);
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		if(partPosition.get(m)>0||pro!=Part4Process.size()-1){
    		g.drawArc(50,proHeight,200,20,0,-180);
    		accuProcSize = accuProcSize + curProSize;
    		g.drawString(""+accuProcSize,252,proHeight+15);
    		}
    		}
    		g.drawString("Empty Space Left: "+partPosition.get(m),90,(height-proHeight)/2+proHeight+25);
    		proHeight = height;}
    		
    		if(m==4){
    		for(int pro=0;pro<Part5Process.size();pro++){
    		curProSize = ProcessSize.get(Part5Process.get(pro));
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		g.drawString("P"+Part5Process.get(pro)+" Size:"+curProSize,110,proHeight+25);
    		proHeight = proHeight+(partLength*curProSize/partSize/2);
    		if(partPosition.get(m)>0||pro!=Part5Process.size()-1){
    		g.drawArc(50,proHeight,200,20,0,-180);
    		accuProcSize = accuProcSize + curProSize;
    		g.drawString(""+accuProcSize,252,proHeight+15);
    		}
    		}
    		g.drawString("Empty Space Left: "+partPosition.get(m),90,(height-proHeight)/2+proHeight+25);
    		proHeight = height;}
    		
    		if(m!=(partComparison.size()-1)){
    		g.drawArc(50,height,200,20,0,-180);
    		g.drawArc(50,height+1,200,20,0,-180);}
    		accuPartSize = accuPartSize + partSize;
    		g.drawString(""+accuPartSize,252,(height+15));
    		if(partComparison.get(m)<10000)
    		g.drawString(""+partComparison.get(m),20,(height+15));
    		else if(partComparison.get(m)>10000&&partComparison.get(m)<100000)
    		g.drawString(""+partComparison.get(m),15,(height+15));
    		else
    		g.drawString(""+partComparison.get(m),10,(height+15));
    		}
			
			int u=0;
    		if(Unfittable.size()>0){
 			g.drawString("Process(es) not able to fit in:",50,534);
			for(u=0;u<Unfittable.size();u++){
			if(u==Unfittable.size()-1)
			g.drawString("P"+Unfittable.get(u),50+(u*20),546);
			else
			g.drawString("P"+Unfittable.get(u)+",",50+(u*20),546);
			}
			}
			
			g.drawString("Total Internal Fragmentation: "+totalIntFrag,50,558);
			g.drawString("Total External Fragmentation: "+totalExtFrag,50,570);
			
    		}  		
    		}}
	
	public class jbGeneratelisten implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(hasResults)
			if(results.isVisible()==true){
				results.setVisible(false);}
			
			int error = 0;
			for(int i = 0; i < jtProc.getColumnCount(); i++){
				if ((jtProc.getValueAt(jtProc.getRowCount()-1, i) == null)||(jtProc.getValueAt(jtProc.getRowCount()-1, i) == "")){
					error = 1;
				}}
			if((chosen=="Worst Fit")||(chosen=="First Fit")||(chosen=="Best Fit")){
				for(int i = 0; i < jtPart.getColumnCount(); i++){
					if ((jtPart.getValueAt(jtPart.getRowCount()-1, i) == null)||(jtPart.getValueAt(jtPart.getRowCount()-1, i) == "")){
					error = 1;
			}}}
		if(error==0){
			results = new JFrame();
			results.setTitle(chosen+" Results");
    		results.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    		results.setLayout(new BorderLayout(10,10));
    		results.setResizable(false);

			if((chosen=="Priority")||(chosen=="Shortest Job First")||(chosen=="Round Robin")){
				GanttChart gc = new GanttChart();
				results.setSize(520,250);
				results.setLocation(500,100);
				results.add(gc);
				validate();
				repaint();		
				}
			if((chosen=="Worst Fit")||(chosen=="First Fit")||(chosen=="Best Fit")){
				results.setSize(300,600);
				results.setLocation(600,0);
				MMAlgorithm();
				Draw draw = new Draw();
				results.add(draw);
				validate();
				repaint();
				}
				
				
				
		results.setVisible(true);
		hasResults=true;
		}
		else
			JOptionPane.showMessageDialog(null, "All Cells must be filled up.");
			}}
		
	public static void main(String[] args) {
       	JFrame window = new CAOS();
       	window.setVisible(true);
    }}