

public class prc{
        
   String name;
   int arrivalTime, burstTime, finishTime, timeLeft, Tq,waiting;
   double Tqs;
   
    public prc(String n, int a, int b) {
    
    name=n;
    arrivalTime=a;
    burstTime=timeLeft=b;
    }
    
   public int getArrival() { return arrivalTime; }
   public int getTminus() { return timeLeft; }
   public String getName() { return name; }
   public int getService() { return burstTime; }
   public int getFinish() {return finishTime; }
   public double getTq() {return Tq; }
   public int getWaiting(){return waiting;}
   public void resetData(){
   	Tq=0;
   finishTime=0;
   waiting=0;}
   
   public void servicing() { timeLeft--;}
   
   public void report(int t) {

      finishTime=t;
    Tq = finishTime-arrivalTime;
      waiting=Tq-burstTime;
      Tqs = Math.round (Tq / burstTime);
   } // calculate data
}
