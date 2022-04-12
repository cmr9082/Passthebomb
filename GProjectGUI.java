import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * GUIStarter - class to help with JavaFX classes
 * @author  D. Patric
 * @version 2205
 */

public class GProjectGUI extends Application implements EventHandler<ActionEvent> {
   //General Declarations
   private Stage stage;       
   private Socket socket = null;
   private PrintWriter pw = null;
   private Scanner scn = null;
   private ObjectInputStream varRecieve= null;
   private Variables pack = null;
   private Vector<String> localList = null;
   
   //Start Menu Initiations
   private Scene sceneStart;
   private VBox rootStart = new VBox(8);
   private FlowPane fpMenu0 = new FlowPane(10,10);
   private FlowPane fpMenu1 = new FlowPane(50,30);
   private Button btnHTP = new Button("How to Play");
   private Button btnJoin = new Button("Join");
   private Image title = new Image("/title.png",true);
   private ImageView titleView = new ImageView(title);
   private TextInputDialog tidName = new TextInputDialog();
   private TextInputDialog tidIP = new TextInputDialog();   
   
   
   //Game Menu Intiations
   private Scene sceneGame;   
   private FlowPane rootGame = new FlowPane(10,10);
   private VBox vbGame1 = new VBox(8);
   private VBox vbGame2 = new VBox(8);
   private FlowPane fpChatSend = new FlowPane(5,5);
   private FlowPane fpNext = new FlowPane(5,5);
   
   private ProgressBar timer = new ProgressBar();
   private TextField currentWord = new TextField();
   private Button btnNext = new Button("Next");
   private TextArea taChat = new TextArea();
   private TextField tfChatInput = new TextField();
   private Button btnSend = new Button("Send");
   
   private TextArea taList = new TextArea();
   private Label lblT1Points = new Label("Team 1: ");
   private Label lblT2Points = new Label("Team 2: ");
   private TextField tfT1Points = new TextField();
   private TextField tfT2Points = new TextField();
   private Button btnLeave = new Button("Leave");
   private Button btnGrab = new Button("Grab Host");
   private Button btnStart = new Button("Start Game");
   private ComboBox cbCategory = new ComboBox();
   private Button btnRelinquish = new Button("Relinquish Host");
   private Label lblHost = new Label("Host Controls:");
   

   
   
   
   
   
   
   
   
   
   
   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      //Initial Setups
      stage = _stage;                      
      stage.setTitle("Pass The Bomb");            
      sceneStart = new Scene(rootStart, 500, 500);
      sceneGame = new Scene(rootGame, 700, 600);                             
      stage.setScene(sceneStart);                 
      stage.show();                          
      
      
      //Setting Up Start Menu
      titleView.setFitWidth(250);
      titleView.setFitHeight(250);
      fpMenu1.getChildren().addAll(btnJoin,btnHTP);
      fpMenu1.setAlignment(Pos.CENTER);
      fpMenu0.getChildren().add(titleView);
      fpMenu0.setAlignment(Pos.CENTER);
      rootStart.getChildren().addAll(fpMenu0,fpMenu1);
      tidName.setHeaderText("Enter Your Username");
      tidName.setContentText("Name: ");
      tidIP.setHeaderText("Enter the Server's IP Address");
      tidIP.setContentText("Server IP: ");         
      btnJoin.setOnAction(this);
      btnHTP.setOnAction(this);
      
      //Setting Up Game Menu
      timer.setProgress(1.0F);
      rootGame.getChildren().addAll(vbGame1,vbGame2);
      fpNext.getChildren().add(btnNext);
      fpChatSend.getChildren().addAll(tfChatInput,btnSend);
      vbGame1.getChildren().addAll(timer,currentWord,fpNext,taChat,fpChatSend);
      vbGame2.getChildren().addAll(taList,btnLeave,lblHost,btnGrab,btnStart,cbCategory,btnRelinquish);
      fpNext.setAlignment(Pos.CENTER);
      taList.setPrefWidth(250);
      taChat.setPrefWidth(400);
      timer.setPrefWidth(400);
      tfChatInput.setPrefWidth(350);
      currentWord.setEditable(false);
      taList.setEditable(false);
      taChat.setEditable(false);
      btnStart.setDisable(true);
      btnRelinquish.setDisable(true);
      cbCategory.setDisable(true);
      cbCategory.getItems().addAll("Everyday Objects","Phrases","Activities","Brands","Video Games","Movies","Foods");
      btnLeave.setOnAction(this);
      btnNext.setOnAction(this);
      btnSend.setOnAction(this);
      btnGrab.setOnAction(this);
      btnStart.setOnAction(this);
      btnRelinquish.setOnAction(this);
      


      
      
      
      
   }
   
   public void handle(ActionEvent evt) {
      // Get the button that was clicked
      Button btn = (Button)evt.getSource();
      
      // Switch on its name
      switch(btn.getText()) {
          case "Join":
              doConnect();
             stage.setScene(sceneGame);
             break;
          case "How to Play":
             //stage.setScene(sceneHTP);
             System.out.println("How To Play screen starts");  
             break;
          case "Leave":
            stage.setScene(sceneStart);
            try{socket.close();}catch(Exception e){System.out.println("Error Disconnecting: "+ e.getMessage());}
            pw.println("DISCONNECT");
            
            break;
          case "Grab Host":
             btnStart.setDisable(false);
             btnRelinquish.setDisable(false);
             cbCategory.setDisable(false);
             btnGrab.setDisable(true); 
             break;   
          case "Relinquish Host":
             btnStart.setDisable(true);
             btnRelinquish.setDisable(true);
             cbCategory.setDisable(true);
             cbCategory.setValue("");
             btnGrab.setDisable(false); 
             break;               
          case "Start":
            //Theoretically would start another method maybe in another class or sumthin
            break;
     
      }
      
      
   }
   

   
   
//Sending   
   public void doConnect(){
             tidIP.showAndWait();
             tidName.showAndWait();
             String ip = (String)tidIP.getResult();
             String name = (String)tidName.getResult();
             try{socket = new Socket(ip, 45549);
                scn = new Scanner(socket.getInputStream());
                pw = new PrintWriter(socket.getOutputStream());
                varRecieve = new ObjectInputStream(socket.getInputStream());  
                pw.println("JOIN");
                pw.println(name);
                pw.flush();
                pack = (Variables)varRecieve.readObject();
                localList = pack.playerlistGet();
                for(int i = 0;i<localList.size();i++){
                  taList.appendText(localList.get(i)+"\n");
                
                
                
                }
                
                
                

                

                
                
                
                
                
                
                
       
                   
             }catch(Exception e){System.out.println("Problem joining: "+ e.getMessage());taChat.setText("Error Joining: Leave this Lobby and try rejoining with correct ip");}
             
  
   
   }
   

   
   
   
   
   
   
   
      
}	
