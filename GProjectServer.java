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

public class GProjectServersolution extends Application implements EventHandler<ActionEvent>{
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root = new VBox();
   private FlowPane fp1 = new FlowPane();
   private Object lock = new Object();
   
   // GUI Components
   private Label lblServer = new Label("Server IP:");
   private TextField tfServer = new TextField();
   private TextArea taLog = new TextArea();
   
   Vector<ObjectOutputStream> clients = new Vector<ObjectOutputStream>();
   
   //Sending Variables
   Variables pack = new Variables();
   Variables packMsg = new Variables();

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
      scene=new Scene(root, 500, 500); 
      stage.setScene(scene);                 
      stage.show();
      fp1.getChildren().addAll(lblServer,tfServer);
      root.getChildren().addAll(fp1,taLog);
      tfServer.setEditable(false);
      taLog.setEditable(false);
      taLog.setPrefHeight(450);
      
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
      

   public void handle(ActionEvent evt) {}
   
   
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
      System.out.println("sending broadcast message : " + clients.size());  
      for(ObjectOutputStream clientOutStream : clients) {
         System.out.println("sending broadcast message ==");
         try {
            clientOutStream.writeUTF(command);
            clientOutStream.flush();
            Variables v = (Variables)data;
            System.out.println("Sending player data " + v.toString());
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
                     String name = ooi.readUTF();
                     taLog.appendText("Player "+name+" has Joined\n");
                     pack.playerlistAdd(name);
                     
                     //broadcase to all connected user

                     broadcastMessage("REFRESHLIST",pack);

                     break;
                     
                  case "DISCONNECT":
                     //use Variables method to remove name from player list
                     socket2.close();
                     break;  
                     
                  case "SEND":
                     String name2 = ooi.readUTF();
                     packMsg.playerlistAdd(name2);
                     //broadcase to all connected user
                     broadcastMessage("REFRESHMSG",packMsg);
                     
                     break;  
                     
                  case "START":
                     taLog.appendText("It Worked!");
                     break;
                  
                  default:
//                      pw.println("ERROR-Unrecognized command: " + command);
//                      pw.flush();
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
 
}

      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      

       
  
   
   
   


      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      

       
  
   
   
   
