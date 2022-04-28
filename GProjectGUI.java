import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.beans.property.IntegerProperty;
import javafx.scene.*;
import javafx.util.Duration;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import java.lang.Object.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * GUIStarter - class to help with JavaFX classes
 * @author  D. Patric
 * @version 2205
 */

public class GProjectGUI extends Application implements EventHandler<ActionEvent> {

   //General Declarations
   private Stage stage;       
   private Socket socket = null;
   private ObjectInputStream ooi = null;
   private ObjectOutputStream oos = null;
   private Prompts prompts = new Prompts();
   private Vector<String> promptSet = new Vector<String>();
   private Variables pack = new Variables();
   private Variables packCurrent = new Variables();
   private Vector<String> localList = null;
   private Vector<String> localMsg = null; 
   
   //Start Menu Initiations
   private Scene sceneStart;
   private VBox rootStart = new VBox(8);
   private Pane fpMenu1 = new Pane();
   private Pane fpMenu2 = new Pane();
   private Pane fpMenu3 = new Pane();
   private Image title = new Image("/startBomb.png",true);
   private ImageView titleView = new ImageView(title);
   
   //Buttons
   private Button btnHTP = new Button("How to Play");
   private Button btnPlay = new Button("Play");
   private Button btnJoin = new Button("Join");
   private Button btnEnter = new Button("Enter");
   private Button btnNext = new Button("Next"); 
   private Button btnSend = new Button("Send");
   private Button btnRelinquish = new Button("Relinquish Host");
   private Button btnLeave = new Button("Leave");
   
   //Input Scene
   private TextInputDialog tidName = new TextInputDialog();
   private TextInputDialog tidIP = new TextInputDialog();  
   private TextField tfIP = new TextField();
   private TextField tfName = new TextField();   
   
   //Scene for actual game   
   private Scene sceneGame;   
   private FlowPane rootGame = new FlowPane(10,10);
   private VBox vbGame1 = new VBox(8);
   private VBox vbGame2 = new VBox(8);
   private FlowPane fpChatSend = new FlowPane(5,5);
   private FlowPane fpNext = new FlowPane(5,5);   
   private ProgressBar timer = new ProgressBar();
   private IntegerProperty seconds = new SimpleIntegerProperty();
   private TextField tfWord = new TextField("Your Prompt Word...");
   private TextArea taChat = new TextArea();
   private TextField tfChatInput = new TextField();
   private Pane cPane = new Pane(); 
   private TextArea taList = new TextArea("Player List:");
   private ComboBox cbCategory = new ComboBox();
   private Label lblHost = new Label("Host Controls:");
   
   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
   
      //Initial Setups
      stage = _stage;    
      stage. setResizable(false);
      rootStart.setId("pane");                  
      stage.setTitle("Pass The Bomb");            
      sceneStart = new Scene(rootStart, 500, 350);
      rootStart.setStyle("-fx-background-image: url(/startBomb.png); -fx-background-repeat: no-repeat; -fx-background-size: 500 350; -fx-background-position: center center;");
      
      //Get the bomb image for the GUI
      FileInputStream stream = new FileInputStream("bomb.png");
      Image image  = new Image(stream);      
      ImageView imageView = new ImageView(image);
      sceneGame = new Scene(rootGame, 700, 600); 
       
      //Set and show the stage                       
      stage.setScene(sceneStart);                 
      stage.show();   
   
      //Titles for the Start scene
      Text lblTitle = new Text();
      lblTitle.setText("Pass The Bomb!");
      Text lblIP = new Text();
      lblIP.setText("Enter IP:");
      Text lblName = new Text();
      lblName.setText("Enter UserName:");
      
      //Button Styles   
      btnJoin.setStyle("-fx-background-color: #BC1002; -fx-text-fill: #ffffff;  -fx-border-radius: 15px; -fx-padding: 0px;");
      btnHTP.setStyle("-fx-background-color: #BC1002; -fx-text-fill: #ffffff; -fx-padding: 0px; -fx-border-radius: 15px;" );
      btnEnter.setStyle("-fx-background-color: #BC1002; -fx-text-fill: #ffffff;  -fx-border-radius: 15px; -fx-padding: 0px;");
      btnPlay.setStyle("-fx-background-color: #BC1002; -fx-text-fill: #ffffff;  -fx-border-radius: 15px; -fx-padding: 0px;");
      btnLeave.setStyle("-fx-background-color: #ECC402; -fx-text-fill: #ffffff;  -fx-border-radius: 15px; -fx-padding: 15px;");
      btnSend.setStyle("-fx-background-color: #ECC402; -fx-text-fill: #ffffff; -fx-width: 400;  -fx-border-radius: 15px; -fx-padding: 15px;");
      btnNext.setStyle("-fx-background-color: #ECC402; -fx-text-fill: #ffffff; -fx-width: 100;  -fx-border-radius: 15px; -fx-padding: 5px;");
   
      //Set the Font for Buttons and Labels
      lblTitle.setFont(Font.font ("Jockey One", 50));
      lblIP.setFont(Font.font ("Jockey One", 50));
      lblName.setFont(Font.font ("Jockey One", 43));
      btnPlay.setFont(Font.font ("Jockey One", 30));
      btnLeave.setFont(Font.font ("Jockey One", 30));
      btnSend.setFont(Font.font ("Jockey One", 30));
      btnHTP.setFont(Font.font ("Jockey One", 30));
      btnEnter.setFont(Font.font ("Jockey One", 30));
      btnJoin.setFont(Font.font ("Jockey One", 30));
      
      //Set the Layout for all GUI components
      
      //Buttons
      btnLeave.setPrefHeight(40);
      btnPlay.setPrefWidth(75);
      btnPlay.setPrefHeight(50);
      btnPlay.setLayoutX(125);
      btnPlay.setLayoutY(175);
      btnHTP.setPrefWidth(150);
      btnHTP.setPrefHeight(50);      
      btnHTP.setLayoutX(225);
      btnHTP.setLayoutY(175);
      btnEnter.setPrefWidth(100);
      btnEnter.setPrefHeight(50);
      btnEnter.setLayoutX(185);
      btnEnter.setLayoutY(200);
      btnJoin.setPrefWidth(100);
      btnJoin.setPrefHeight(50);
      btnJoin.setLayoutX(185);
      btnJoin.setLayoutY(200);
      
      //Labels
      lblTitle.setLayoutY(160);
      lblTitle.setLayoutX(125);
      lblIP.setLayoutY(145);
      lblIP.setLayoutX(165);
      lblName.setLayoutY(160);
      lblName.setLayoutX(120); 
      
      //TextFields     
      TextField tfIP = tidIP.getEditor();
      tfIP.setPrefWidth(175);
      tfIP.setPrefHeight(20);
      tfIP.setLayoutX(150);
      tfIP.setLayoutY(165);    
      TextField tfName = tidName.getEditor();
      tfName.setPrefWidth(175);
      tfName.setPrefHeight(20);
      tfName.setLayoutX(150);
      tfName.setLayoutY(165);
   
      
      rootStart.getChildren().addAll(fpMenu1);
      tidName.setHeaderText("Enter Your Username");
      // tidName.setContentText("");
      tidName.setGraphic(null);
      tidName.setHeaderText(null);
      tidIP.setGraphic(null);
      tidIP.setHeaderText(null);
      // tidIP.setContentText("");
      
      //Add GUI components to the Panes
      fpMenu1.getChildren().addAll(lblTitle, btnPlay,btnHTP);
      fpMenu2.getChildren().addAll(lblIP, btnEnter, tfIP);
      fpMenu3.getChildren().addAll(lblName, btnJoin, tfName);
      
      //Create and Style the Dialogue Panes
      DialogPane dPane1 = tidIP.getDialogPane();
      dPane1.setStyle("-fx-background-image: url(/startBomb.png);-fx-background-size: 500 350;  -fx-background-repeat: no-repeat; -fx-background-position: center center;");
      dPane1.setPrefWidth(500);
      dPane1.setPrefHeight(350);
      dPane1.getButtonTypes().remove(ButtonType.CANCEL);
      dPane1.lookupButton(ButtonType.OK).setVisible(false);
   
      dPane1.getChildren().add(fpMenu2);
   
      DialogPane dPane2 = tidName.getDialogPane();
      dPane2.setStyle("-fx-background-image: url(/startBomb.png);-fx-background-size: 500 350;  -fx-background-repeat: no-repeat; -fx-background-position: center center;");
      dPane2.setPrefWidth(500);
      dPane2.getButtonTypes().remove(ButtonType.CANCEL);
      dPane2.lookupButton(ButtonType.OK).setVisible(false);     
      dPane2.setPrefHeight(350);
      dPane2.getChildren().add(fpMenu3);   
      
      //Actions for the buttons
      btnEnter.setDefaultButton(true);
      btnJoin.setDefaultButton(true);    
      btnHTP.setOnAction(this);
      btnEnter.setOnAction(event -> tidIP.close());
      btnPlay.setOnAction(this);
      btnJoin.setOnAction(event -> tidName.close());
      tfWord.setEditable(false);
      taList.setEditable(false);
      taChat.setEditable(false);
      btnLeave.setOnAction(this);
      btnNext.setOnAction(this);
      btnSend.setOnAction(this);
      btnSend.setOnKeyPressed(
         new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
               if (k.getCode().equals(KeyCode.ENTER)) {
                  doSend();
               }
            }
         });
      
      //Add the pane to the main game GUI
      rootGame.getChildren().addAll(cPane);        
      rootGame.setStyle("-fx-background-color: #BA1009; -fx-border-width: 30px; -fx-border-color: #ECC402;");
      cPane.setPrefSize(400, 400);   
      cPane.relocate(100, 100);
      cPane.getChildren().addAll(taChat,timer,tfWord,btnNext,  imageView, fpChatSend,tfChatInput, btnLeave, btnSend, taList);
      
      //More Layout for GUI components
      timer.setStyle("-fx-accent: #BA1009; -fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");   
      timer.setPrefHeight(40);
      timer.setPrefWidth(310);
      timer.setLayoutY(450);
      timer.setLayoutX(125);
      imageView.setLayoutY(400);
      btnLeave.setLayoutX(495);
      btnLeave.setLayoutY(450);
      btnSend.setPrefWidth(400);
      btnSend.setLayoutX(35);
      btnSend.setLayoutY(315);
      taList.setLayoutX(460);
      taList.setLayoutY(50);
      taList.setPrefWidth(140);
      taList.setPrefHeight(200);
      taChat.setLayoutX(35);
      taChat.setLayoutY(50);
      tfWord.setLayoutX(35);
      tfWord.setLayoutY(12);
      btnNext.setLayoutX(200);
      btnNext.setLayoutY(12);
      tfChatInput.setLayoutX(35);
      tfChatInput.setLayoutY(270);
      tfChatInput.setPromptText("Type your guess...");
      taChat.setStyle("-fx-border-radius: 15px;");
      taChat.setPrefWidth(400);
      taChat.setPrefHeight(200);
      tfChatInput.setPrefWidth(400);            
      try{
         Image image1 = new Image(new FileInputStream("Rectangle 15.png"));
      
      }catch(Exception e){
      
         e.getMessage();
      
      }      
   }
   
   //Event Handler for Buttons
   public void handle(ActionEvent evt) {
      // Get the button that was clicked
      Button btn = (Button)evt.getSource();
      
      // Switch on its name
      switch(btn.getText()) {
         case "Play":
            doConnect();
            stage.setScene(sceneGame);
            
            break;
            
         case "How to Play":
            System.out.println("How To Play screen starts"); 
             
            break;
            
         case "Leave":
            doLeave();
            
            break;        
            
         case "Send":
            doSend();
            
            break;
            
         case "Next":
            tfWord.setText(getPrompt());
            
            break;                     
      }
   }

   //Method to connect to the Server
   public void doConnect(){
   
      String ip = null;
      String name = null;
      
      //Get inputs from the TextInputDialogs
      
      Optional<String> result = tidIP.showAndWait();
      ip = tidIP.getEditor().getText();
      
      Optional<String> result2 = tidName.showAndWait();
      name = tidName.getEditor().getText();
      
      try{
         socket = new Socket(ip, 45549);
         oos = new ObjectOutputStream(socket.getOutputStream());
         ooi = new ObjectInputStream(socket.getInputStream()); 
          
         //Start the thread
         new ReceiveMsgThread().start();
         
         //Send the command to join
         oos.writeUTF("JOIN");
         oos.flush();
         oos.writeUTF(name);
         oos.flush();
         
      }catch(Exception e){
         System.out.println("Problem joining: "+ e.getMessage());
         taChat.setText("Error Joining: Leave this Lobby and try rejoining with correct ip");
      }           
   }
   
   //Method to send the User input to the server then to the rest of the clients
   private void doWord(String _word){
      String word = _word;
    try {      
         oos.writeUTF(word);
         oos.flush();
         
      }catch (Exception ex) {
         ex.printStackTrace();
      }

   }
   private void doSend() {
   
      try {      
         String chat = tfChatInput.getText();
         oos.writeUTF("SEND");
         oos.flush();
         oos.writeUTF(chat);
         oos.flush();
         tfChatInput.setText("");
         
      }catch (Exception ex) {
         ex.printStackTrace();
      }
   }
   
   //Method to leave and disconnect from the Server
   private void doLeave(){
      try{
         taChat.setText("");
         tfWord.setText("");
         oos.writeUTF("DISCONNECT");
         oos.flush();     
         socket.close();
         stage.setScene(sceneStart);
         
      }catch(Exception e){System.out.println(e.getMessage());}
   }
   
   //Method to refresh the Chat box to load any new messages
   private void refreshMsg(Variables var) {
   
      //Loop through the variables to update the list
      Vector<String> playerList = var.playerListGet();
      localList = playerList;
      taChat.setText("");
      
      for(int i = 0;i< playerList.size();i++){
         System.out.println("Adding ==> " + playerList.get(i));
         taChat.appendText(playerList.get(i)+"\n");
      }
   }
   
   //Method to refresh the Chat box to load any new messages
   private void refreshWord(Variables var) {
   
      //Loop through the variables to update the list
      Vector<String> playerList = var.playerListGet();
      localList = playerList;

      for(int i = 0;i< playerList.size();i++){
         System.out.println("Adding ==> " + playerList.get(i));
        
      }
   }
   
   //Method to refresh teh player list
   private void refreshList(Variables var) {
   
      //Loop through the variables to update the list
      Vector<String> playerList = var.playerListGet();
      localList = playerList;
      taList.setText("");
      
      for(int i = 0 ; i< playerList.size();i++){
         System.out.println("Adding ==> " + playerList.get(i));
         taList.appendText(playerList.get(i)+"\n");
      }
   }   
   
   //class to receieve new messages from the server
   class ReceiveMsgThread extends Thread {
   
      public void run() {
      
         System.out.println("Client Thread Running");
         timer.progressProperty().bind(seconds.divide(60.0));
         Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
            new KeyFrame(Duration.minutes(1), 
            e-> {
                // do anything you need here on completion...
               System.out.println("Minute over");
            }, new KeyValue(seconds, 60))   
            );
      
         String message = "";
         
         try {   
              
            //Loop to keep listening
            while(true) {
               String command = ooi.readUTF();
                  //it is a command
               System.out.println("Command received : " + command);
               switch(command) {
                  case "REFRESHLIST":
                        //command to refresh the list
                        //Server is sending var object. readit.
                     System.out.println("Processing command: " + command);
                     Variables var = (Variables)ooi.readObject();
                     System.out.println("received data of size " + var.playerListGet().size());
                     refreshList(var);
                     break;
                      
                  case "REFRESHMSG":                    
                     System.out.println("Processing command: " + command);
                     Variables var2 = (Variables)ooi.readObject();
                     System.out.println("received data of size " + var2.playerListGet().size());
                     refreshMsg(var2);
                     break;
                     
                     
                  case "STARTTIMER":
                     timeline.setCycleCount(Animation.INDEFINITE);
                     timeline.play();
                     break;
               
                                        
                  case "MOVIES":
                     System.out.println("Hello World");
                     // Variables var2 = (Variables)ooi.readObject();
                     promptSet = prompts.getMovies();
                     
                     break;
                  case "YOURTURN":
                     String currentWord = getPrompt();
                     tfWord.setText(currentWord);
                     pack.setCurrentWord(currentWord);
                     doWord(currentWord);
                     break;
                     
                  case "RESETWORD":
                     tfWord.setText("");
                     
                     break;
                                        
                  default:
                     System.out.println("Invalid command: " + command);
               }
            }
         }catch(Exception ex) {
            ex.printStackTrace();
         }
      }
   }  
   
   //Method to get the prompt based on the choice in the Category
   public String getPrompt(){
      Collections.shuffle(promptSet);
      return promptSet.get(0);
   }
}


 
   
    
   

   
