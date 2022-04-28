

public class VarTest{
   public static void main(String[] args){
      Variables pack = new Variables();
      
      pack.playerlistAdd("Neb");
      pack.playerlistAdd("Neb2");
      pack.playerlistAdd("Caleb");
      pack.playerlistAdd("Erich");
      
      System.out.println(pack.playerlistGet());
      pack.playerlistRemove("Neb");
      System.out.println(pack.playerlistGet());
      
      
      pack.setCurrentWord("Test");
      System.out.println(pack.getCurrentWord());
      
      
      
   
   }




}