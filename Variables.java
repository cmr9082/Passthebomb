import java.util.*;
import java.io.*;
public class Variables implements Serializable{

   //Initialization and Construction
   Vector<String> playerList;
   int team1Points;
   int team2Points;
   
   public Variables(){
    playerList = new Vector<String>();
    team1Points = 0;
    team2Points = 0;
   }
   
   
   //PlayerList Interactions
   public void playerlistAdd(String _string){
      playerList.add(_string);
   }
   
   public void playerlistRemove(String _string){
      
      
   }
   
   public String playerlistGet(){
      String res = "";
      for(int i = 0;i<playerList.size();i++){
         res = res+playerList.get(i)+".";
      
      
      }
         return res;
         
   }
   
   //Points Interaction
   public void team1Inc(){team1Points++;}
   public void team1Dec(){team1Points--;}
   public void team2Inc(){team2Points++;}
   public void team2Dec(){team2Points--;}
   public int team1PointGet(){return team1Points;}
   public int team2PointGet(){return team2Points;}
   
   
   
   
   


}