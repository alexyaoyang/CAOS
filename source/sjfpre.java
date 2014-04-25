import java.util.*;
public class sjfpre {

     public static Vector readyQ, finishQ, Q, out,wait;
     public static int all, clock=-1;
     public static prc P, T,sup;
     public static boolean idle;
     public static String Pname,A,S,F,Tq,Tqs,W,AVGT,AVGW;
     public static ArrayList <String> RunTru = new ArrayList <String>();
     public static ArrayList <Integer> STRunTru = new ArrayList <Integer>();
    
    public sjfpre() {
    Q = new Vector(1,1);
	readyQ = new Vector(1,1);
    finishQ = new Vector(1,1);
    wait = new Vector(1,1);
    
    }
    
    public static void main(String[] args) {
    sjfpre sjf = new sjfpre();
    sjf.SJFAlgo();
    }
    
    public void addProc(int proNo,int a,int b) {
    prc process = new prc("P"+proNo,a,b); 
    wait.addElement(process);
    }
    
    public void SJFAlgo(){
    	
    	RunTru.clear();
    	
    	for (int t=0;t<5;t++){

            for (int j=1; j<wait.size(); j++){
            	
            	   sup = (prc)wait.firstElement();
               
            int sr = sup.getService();
            
               if (sr > ((prc)wait.elementAt(j)).getService()) {
                     sup = (prc)wait.elementAt(j);
                     sr = sup.getService();
               
     System.out.println("add new "+sup.getName());
     wait.removeElement(sup);
     Q.addElement(sup);
               }
               else{System.out.println("add original "+sup.getName());
               wait.removeElement(sup);
                Q.addElement(sup);
               }}
                
}	sup = (prc)wait.firstElement();
               Q.addElement(sup); 
    
    
    
	all=Q.size();
      boolean interrupt=false;
      prc X=null; // preempted process
      
      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            interrupt = true;
            System.out.println("Time "+T.getArrival()+":Process "+T.getName()+" ready.");
            //an.upstatus("Time "+T.getArrival()+":Process "+T.getName()+" ready.");
         } // put in ready queue
         if (idle || interrupt) {
            if (interrupt) {
               interrupt=false;
               if (X!=null)
                  readyQ.addElement(X);
            } // reenter process
            if (readyQ.size()==0){
            	
            	            		STRunTru.add(-1);
            		RunTru.add(null);
            System.out.println("Time "+clock+" : "+"Nothing is running");
               continue;}
            if (idle)
               idle = false;
            P = (prc)readyQ.firstElement();
            int sr = P.getTminus();
            for (int j=1; j<readyQ.size(); j++){
            
               if (sr > ((prc)readyQ.elementAt(j)).getTminus()) {
                     P = (prc)readyQ.elementAt(j);
                     sr = P.getTminus();
               } // find earliest process with shortest remaining time
               if(sr==((prc)readyQ.elementAt(j)).getTminus()){
              String name1= P.getName();
              String name2= ((prc)readyQ.elementAt(j)).getName();
              int order1=0;
              int order2=0;
              if(name1.equals("P0")){
              	
              	order1=5;
              }
              if(name1.equals("P1")){
              	order1=4;
              }
              if(name1.equals("P2")){
              	order1=3;
              }
              if(name1.equals("P3")){
              	order1=2;
              }
              if(name1.equals("P4")){
              	order1=1;
              }
              
              
              if(name2.equals("P0")){
              	order2=5;
              }
              if(name2.equals("P1")){
              	order2=4;
              }
              if(name2.equals("P2")){
              	order2=3;
              }
              if(name2.equals("P3")){
              	order2=2;
              }
              if(name2.equals("P4")){
              	order2=1;
              }
              
         
              
               if(order2>order1){
              	P = (prc)readyQ.elementAt(j);
              }
          
               	
               }
            }
               
            readyQ.removeElement(P);            
         } // put in run state
         if(P!=null){
         
         P.servicing();
         //an.drawbar(P,clock);
         System.out.println("Time "+clock+":Serving process "+P.getName()+".");
         RunTru.add(P.getName());
         STRunTru.add(P.getTminus());
         //an.upstatus("Time "+clock+":Serving process "+P.getName()+".");
         if (P.getTminus()==0) {
         	System.out.println("Time "+(clock+1)+":Process "+P.getName()+" done.");
            //an.upstatus("Time "+(clock+1)+":Process "+P.getName()+" done.");
            P.report(clock+1); // anticipate completion
            finishQ.addElement(P);
            idle = true;
            X=null;
         } // put in finish queue
         else
            X=P; 
         }   
            	else{
            		System.out.println("Time "+clock+" : Nothing is running");
            		STRunTru.add(-1);
            		RunTru.add(null);
            	}
              
      } while (finishQ.size()<all);
      System.out.println("Algorithm finished.");
      //an.upstatus("Algorithm finished.");
      report(finishQ,"Shortest Remaining Time");
      //st.report(finishQ,"Shortest Remaining Time");
      //in.resetGUI();
      clock=-1;
    }
    
    
    
    public static prc processready(int tick) {
      for (int j=0; j<Q.size(); j++)
         if (((prc)(Q.elementAt(j))).getArrival() <= tick)
            return (prc)Q.elementAt(j);
      return null;
   } // clock

	public static void report(Vector R,String title) {
      out = R;
      display();
   } // report statistics to notepad
   private static void display() {
   	int sumW=0,sumT=0;
   	double avgW=0,avgT=0,xx=0;
      prc temp;

      Pname = "Process";
      W = "Waiting Time";
      A = "Arrival Time";
      S = "Service Time";
      F = "Finish Time";
      Tq = "Turnaround Time";
      Tqs = "Tq/Ts";
      AVGT="Average Turnaround";
      AVGW="Average Waiting";
  
      buffer(Pname,W,AVGW,AVGT,A,S,F,Tq);
      for (int j=0; j<out.size(); j++) {
         temp = (prc)out.elementAt(j);
         
      
         Pname += temp.getName();
         W += temp.getWaiting();
         A += temp.getArrival();
         S += temp.getService();
         F += temp.getFinish();
         Tq += temp.getTq();
         
         
         
         sumW+=temp.getWaiting();
         sumT+=temp.getTq();
    //      AVGW+=temp.getWaiting();
   //       AVGT+=temp.getTq();
         if(j==out.size()-1){		//check for last element
         xx=j+1;					//amount of processes
         
         	 avgT=sumT/xx;
         	 avgW=sumW/xx;
         	
         	 System.out.println("Average Waiting Time: "+avgW);				//use avgW and avgT if possible
         	 System.out.println("Average Turnaround Time: "+avgT);
         	 
         	 //sjfpre sjf = new sjfpre();
         	 
         	 RunTru.add(""+avgW);
         	 RunTru.add(""+avgT);
         }
 
       
         //Tqs += temp.getTqs();
         buffer(Pname,W,AVGW,AVGT,A,S,F,Tq);
         
    
      } // get info from each
      
  
      
      //pad.appendText(P+"\n"+A+"\n"+S+"\n"+F+"\n"+Tq+"\n"+Tqs+"\n");
      System.out.println(Pname+"\n"+W+"\n"+A+"\n"+S+"\n"+F+"\n"+Tq+"\n");
   } // display stats
   public static void buffer(String p, String w,String avw,String avt,String a, String s, String f, String tq) {
      int max = Math.max(Pname.length(),Math.max(W.length(),Math.max(AVGW.length(),Math.max(AVGT.length(),Math.max(A.length(),Math.max(S.length(),Math.max(F.length(),
                Math.max(Tq.length(),Tqs.length()))))))));
      max += 5;
      Pname = space (Pname,max);
      W = space (W,max);
      AVGW = space (AVGW,max);
      AVGT = space (AVGT,max);
      A = space (A,max);
      S = space (S,max);
      F = space (F,max);
      Tq = space (Tq,max);
      Tqs = space (Tqs,max);
      
     
   } // format with buffer spaces, left justfied
   
   public static String space(String x, int m) {
      while (x.length() < m)
         x += " ";
      return x;
   } // pad with spaces


   

}
