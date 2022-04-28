import java.util.*;
import java.io.*;
public class Variables implements Serializable{

   //Initialization and Construction
   Vector<String> promptSet;
   Vector<String> playerList;
   String currentWord;
   
   public Variables(){
    playerList = new Vector<String>();
    promptSet = new Vector<String>();
    currentWord = "";

   }
   
   
   //PlayerList Interactions
   public void playerlistAdd(String _string){
      playerList.add(_string);
   }
   
   public void playerlistRemove(String _string){
      playerList.remove(playerList.indexOf(_string));
   }
   
   public Vector<String> playerListGet(){
         return playerList;
         
   }
   
   public String getPlayerInput(){             
            return playerList.lastElement();
          }    
   
   
   //PromptSet interactions
   public Vector<String> getPromptSet(){
         return promptSet;         
   }
   public void setPromptSet(Vector<String> ps){
         promptSet = ps;
   }

   //currentWord Interactions
   public String getCurrentWord(){return currentWord;}
   public void setCurrentWord(String _string){currentWord = _string;}
   
   
   


}