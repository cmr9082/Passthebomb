import java.util.*;
import java.io.*;
public class Variables implements Serializable{

   //Initialization and Construction
   Vector<String> promptSet;
   Vector<String> playerList;
   String currentWord;
   int team1Points;
   int team2Points;
   
   public Variables(){
    playerList = new Vector<String>();
    promptSet = new Vector<String>();
    currentWord = "";
    team1Points = 0;
    team2Points = 0;
   }
   
   
   //PlayerList Interactions
   public void playerlistAdd(String _string){
      playerList.add(_string);
   }
   
   public void playerlistRemove(String _string){
      playerList.remove(playerList.indexOf(_string));
   }
   
   public Vector<String> playerlistGet(){
         return playerList;
         
   }
   //PromptSet interactions
   public Vector<String> getPromptSet(){
         return promptSet;         
   }
   public void setPromptSet(Vector<String> ps){
         promptSet = ps;
   }
   
   //Points Interaction
   public void team1Inc(){team1Points++;}
   public void team1Dec(){team1Points--;}
   public void team2Inc(){team2Points++;}
   public void team2Dec(){team2Points--;}
   public int team1PointGet(){return team1Points;}
   public int team2PointGet(){return team2Points;}
   
   //currentWord Interactions
   public String getCurrentWord(){return currentWord;}
   public void setCurrentWord(String _string){currentWord = _string;}
   
   
   


}