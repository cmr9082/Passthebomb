import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class GProjectServer extends Application implements EventHandler<ActionEvent>{
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root = new VBox();
   private FlowPane fp1 = new FlowPane(10,10);
   private Object lock = new Object();
   private Socket socket2 = null;
   
   
   // GUI Components
   private Label lblServer = new Label("Server IP:");
   private TextField tfServer = new TextField();
   private TextArea taLog = new TextArea();
   private Button btnStart = new Button("Start");
   private ComboBox cbCategory = new ComboBox();
   private RadioButton btnAuto = new RadioButton("Auto Start");
   private Pane pLog = new Pane();
   private Pane pButtons = new Pane();
   private Socket clientSocket = null;
   private boolean verify = true;
   private String guess;
   private String input;
   
   Vector<ObjectOutputStream> clients = new Vector<ObjectOutputStream>();
   
   //Game Components
   Prompts prompts = new Prompts();
   Vector<String> promptSet = null;
   int pointCounter = 0;
   boolean timerOn = true;
   
   
   //Sending Variables
   Variables pack = new Variables();
   Variables packMsg = new Variables();
   String name = "";
   double width = 300;
   double height = 300;
   
   

  //  lblTitle.setLayoutY(160);
//    lblTitle.setLayoutX(125);
//    fpMenu1.getChildren().add(lblTitle);
//    root.getChildren().addAll(fpMenu1);


   public static void main(String[] args) {
      launch(args);
   }
   
   /**
    * Launch, draw and set up GUI
    * Do server stuff
    */
    
   public void start(Stage _stage) {
      // Window setup
      stage = _stage;
      stage.setTitle("Pass the Bomb SERVER");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {System.exit(0);}});
      stage.setResizable(false);
      scene=new Scene(root, 600, 600); 
      stage.setScene(scene);                 
      stage.show();
      
      root.setStyle("-fx-background-image: url(/startBomb.png); -fx-background-repeat: no-repeat; -fx-background-size: 800 600; -fx-background-position: center center;");
     
      
   
      pLog.getChildren().addAll(taLog, btnStart, btnAuto, cbCategory, tfServer, lblServer);
   
      root.getChildren().addAll(pButtons, pLog);
      
      taLog.setPrefWidth(width);
      taLog.setPrefHeight(height);
      cbCategory.getItems().addAll("Movies","Everyday Objects","Phrases","Activities","Brands","Video Games","Movies","Foods");
      tfServer.setEditable(false);
      taLog.setEditable(false);
      taLog.setPrefHeight(225);
      taLog.setPrefWidth(150);
      taLog.setLayoutY(200);
      taLog.setLayoutX(130);
      btnAuto.setLayoutX(300);
      btnAuto.setLayoutY(235);
      btnStart.setLayoutX(300);
      btnStart.setLayoutY(270);
      cbCategory.setLayoutX(300);
      cbCategory.setLayoutY(200);
      lblServer.setLayoutX(300);
      lblServer.setLayoutY(305);
      tfServer.setLayoutX(300);
      tfServer.setLayoutY(330);
      btnStart.setOnAction(this);
      //Move the buttons and other server features to the side of the textArea
      
     
   
      cbCategory.getSelectionModel().selectFirst();
     //  ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
   
     
      try{tfServer.appendText(InetAddress.getLocalHost().getHostAddress().trim());}
      catch(Exception e){taLog.appendText("Error Getting Local IP: "+e.getMessage()+"\n");}
   
      Thread t1 = 
         new Thread() {
            public void run() {
               doServerStuff();
            }
         };
      t1.start();
            
   }
   
   public void handle(ActionEvent evt) {
    
      Button btn = (Button)evt.getSource();
      
      // Switch on its name
      switch(btn.getText()) {
         case "Start":
            gameStart();
            break;
      
      }
   }

   private void doServerStuff() {
      try {
         ServerSocket sSocket = new ServerSocket(45549);
         while(true) {
            Socket socket1 = sSocket.accept();
            Thread t2 = new ClientThread(socket1);
            t2.start();
            
         }
      }
      catch(Exception e) {
      }
   }

   private void broadcastMessage(String command, Object data) {
      //System.out.println("sending broadcast message : " + clients.size());  
      for(ObjectOutputStream clientOutStream : clients) {
         //System.out.println("sending broadcast message ==");
         try {
            clientOutStream.writeUTF(command);
            clientOutStream.flush();
            Variables v = (Variables)data;
            //System.out.println("Sending player data " + v.toString());
            clientOutStream.writeObject(data);
            clientOutStream.reset();
            clientOutStream.flush();
         } catch(Exception ex)
         {
            ex.printStackTrace();
         }
      }
   }

         
   class ClientThread extends Thread {
      Socket socket2 = null;
      Scanner scn = null;
      ObjectInputStream ooi = null;
      ObjectOutputStream oos = null; 
   
      
      /** constructor */
      public ClientThread(Socket _cSocket) {
         socket2 = _cSocket;
      }
      
      /** Thread main */
      public void run() {
         try {
            ooi = new ObjectInputStream(socket2.getInputStream());
            oos = new ObjectOutputStream(socket2.getOutputStream());
            clients.add(oos);
            taLog.appendText("Request received from " + socket2.getInetAddress().getHostName()+"\n");
            
            
         
            while(true) {
               // String command = scn.nextLine();
               String command = ooi.readUTF();
               switch(command) {
               
                  case "JOIN":
                     name = ooi.readUTF();
                     taLog.appendText("Player "+name+" has Joined\n");
                     pack.playerlistAdd(name);
                     broadcastMessage("REFRESHLIST",pack);
                  
                     break;
                     
                  case "DISCONNECT":
                  
                     taLog.appendText("Player "+name+" has left\n");
                     pack.playerlistRemove(name);
                     
                     socket2.close();
                     broadcastMessage("REFRESHLIST",pack);
                     break;  
                     
                  case "SEND":
                    
                    //  String name2 = ooi.readUTF();
                     guess = ooi.readUTF();
                     doValidate(guess);
                     packMsg.playerlistAdd(guess);
                     broadcastMessage("REFRESHMSG",packMsg);
                  
                     break;
                     
                  case "TIMER-END":
                     timerOn = false;
                     break;  
                     
                  default:
                     taLog.appendText("ERROR: Unrecognized Command Recieved");
                     break;
               }  // switch 
               
              
            
            }  //while
            
            // pw.close();
            // scn.close();
            //socket2.close();
         }  // try
         catch(Exception e) {
         }
      }  // run
   }
 
 
 
   public void gameStart(){
            
   
   
   
   //Setup
    //Get and set Array Category
   
    
   //  try{
   //     ServerSocket s1 = new ServerSocket();  
   //     clientSocket = s1.accept();
   //     
   //     ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());  
   //     
   //      oos.writeUTF("STARTTIMER");
   //                      oos.flush();
   // 
   //     }catch(Exception e){
   //       e.getMessage();
   //     
   //     }
   
      taLog.appendText("Game Started!\n");
      
   
      switch(cbCategory.getValue().toString()){
         case "Everyday Object":
            promptSet = prompts.getEverydayObjects();
            break;
         case "Phrases":
            promptSet = prompts.getPhrases();
            break;      
         case "Activities":
            promptSet = prompts.getActivities();
            break;      
         case "Brands":
            promptSet = prompts.getBrands();
            break;
         case "Foods":
            promptSet = prompts.getFoods();
            break;
         case "Video Games":
            promptSet = prompts.getVideoGames();
            break;
         case "Movies":
            // // broadcastMessage("MOVIES", pack);
            
            try{
               for(ObjectOutputStream clientOutStream : clients) {
               
                  clientOutStream.writeUTF("MOVIES");
                  clientOutStream.reset();
                  clientOutStream.flush();
               
               }
            
            }catch(Exception e){
            
            
            }
            
            break;
      }
       
    
    
   //Gameplay
      
      //Isolate a player/ give them the turn
      
      try{//  while(timerOn){
      
         for(int i = 0; i < clients.size();i++){
         
            //Iterate the promptSet and send to isolated client
         
         
         
         
         
         
            ObjectOutputStream turnPlayer = clients.get(i);
            turnPlayer.writeUTF("YOURTURN");
            turnPlayer.flush();
         
            ServerSocket ss = new ServerSocket(45549);
            Socket socket = ss.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
         
            String currentWord = getCurrentWord();
            System.out.println(currentWord+"");
            while(verify){
               
               if(input.equals(currentWord)){
               
                  verify = false;
                  try{
                     for(ObjectOutputStream clientOutStream : clients) {
                     
                        clientOutStream.writeUTF("RESETWORD");
                        clientOutStream.reset();
                        clientOutStream.flush();
                     
                     }
                        
                     
                  }catch(Exception e){
                  
                  
                  }
               
               
               
               }else{
           
                  verify = true;                 
               }
               
            }           
         //  
         //             
         //             while(true) {
         //                // String command = scn.nextLine();
         //                String command = ooi.readUTF();
         //                switch(command) {
         //                
         //                   case "SEND":
         //                      String guess1 = ooi.readUTF();
         //                   
         //                      break;
         //                
         //                }
         //               
         //             
         //             }
         }
      }catch(Exception e){}
            //Listen for correct answer
   
        
      
      pointCounter++;
      // }
     
      //send points
   }
   
   public void doValidate(String _command){
   
      input = _command;
      System.out.println(input);  
   
      try{
         ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
         ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
      
      
         if(input.equals(pack.getCurrentWord())){
            System.out.println("Verified");
            verify = false;
           //  wrongAnswer.setStyle("-fx-color: red;"); 
         }else{
            System.out.println("Not Verified");
            verify = true;  
         }
      
      }catch(Exception e){
         e.getMessage();      
      } 
   }
   
   public String getCurrentWord(){
   String currentWord = pack.getCurrentWord();
      return currentWord;
   }
   
   
}

      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      

       
  
   
   
   


      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      

       
  
   
   
   
