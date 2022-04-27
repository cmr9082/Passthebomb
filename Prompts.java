import java.util.*;
import java.io.*;
public class Prompts implements Serializable{

   //Initialization and Construction
   Vector<String> promptSet;


   
   public Prompts(){
   promptSet = new Vector<String>();
   
   }
   
   public Vector<String> getEverydayObjects(){
      promptSet.clear();
      promptSet.add("pencil");     
      return promptSet;
      
   }
   public Vector<String> getPhrases(){
      promptSet.clear();
      promptSet.add("Kill Two Birds With One Stone");     
      return promptSet;
   }
   public Vector<String> getActivities(){
      promptSet.clear();
      promptSet.add("Riding a Bike");
      
      return promptSet;
   }
   public Vector<String> getBrands(){
      promptSet.clear();
      promptSet.add("Coke");
      
      return promptSet;
   }
   public Vector<String> getVideoGames(){
      promptSet.clear();
      promptSet.add("Minecraft");
      
      return promptSet;
   }
   public Vector<String> getMovies(){
      promptSet.clear();
      promptSet.add("Citizen Kane");
      promptSet.add("The Godfather");
      promptSet.add("Casablanca");
      promptSet.add("Gone with the Wind");
      promptSet.add("Schindler's List");
      promptSet.add("The Wizard of Oz");
      promptSet.add("Star Wars");
      promptSet.add("Psycho");
      promptSet.add("It's a Wonderful Life");
      promptSet.add("E.T.");
      promptSet.add("To Kill a Mockingbird");
      promptSet.add("Apocolypse Now");
      promptSet.add("Snow White");
      promptSet.add("King Kong");
      promptSet.add("Lord of the Rings");
      promptSet.add("Taxi Driver");
      promptSet.add("North by Northwest");
      promptSet.add("Rocky");
      promptSet.add("Jaws");
      promptSet.add("Indiana Jones");
      promptSet.add("Saving Private Ryan");
      promptSet.add("Shawshank Redemption");
      promptSet.add("The Silence of the Lambs");
      promptSet.add("Forrest Gump");
      promptSet.add("Gladiator");
      promptSet.add("Titanic");
      promptSet.add("12 Angry Men");
      promptSet.add("The Sixth Sense");
      promptSet.add("Pulp Fiction");
      promptSet.add("Blade Runner");
      promptSet.add("Toy Story");
      promptSet.add("Inception");
      promptSet.add("The Dark Knight");
      promptSet.add("Spirited Away");
      promptSet.add("There Will Be Blood");
      promptSet.add("Mad Max");
      promptSet.add("Call Me By Your Name");
      promptSet.add("No Country for Old Men");
      promptSet.add("Ocean's Eleven");
      promptSet.add("Anchorman");
      promptSet.add("Superbad");
      promptSet.add("The Incredibles");
      promptSet.add("Ted");
      promptSet.add("The Social Network");
      promptSet.add("The Wolf of Wall Street");
      promptSet.add("National Treasure");
      promptSet.add("The Grand Budapest Hotel");
      promptSet.add("Get Out");
      promptSet.add("Moonlight");
      promptSet.add("Whiplash");
      promptSet.add("Parasite");
      promptSet.add("The Florida Project");
      promptSet.add("The Exorcist");
      promptSet.add("Boyhood");
      promptSet.add("Zodiac");
      promptSet.add("Lady Bird");
      promptSet.add("La La Land");
      promptSet.add("Her");
      promptSet.add("American Psycho");
      promptSet.add("Marriage Story");
      

      return promptSet;
   }
   public Vector<String> getFoods(){
      promptSet.clear();
      promptSet.add("Pizza");
      promptSet.add("Burger");
      promptSet.add("Macaroni and Cheese");
      promptSet.add("Peanut Butter and Jelly");
      promptSet.add("Popcorn");
      promptSet.add("Ice Cream");
      promptSet.add("Salad");
      promptSet.add("Fried Chicken");
      promptSet.add("Apple Pie");
      promptSet.add("Tater Tots");
      promptSet.add("Wings");
      promptSet.add("Cookies");
      promptSet.add("Steak");
      promptSet.add("Nachos");
      promptSet.add("Chips");
      promptSet.add("Hot Dog");
      promptSet.add("Chicken Pot Pie");
      promptSet.add("Quesadilla");
      promptSet.add("Grilled Cheese");
      promptSet.add("Spaghetti and Meatballs");
 
 
      return promptSet;
   }
   

}