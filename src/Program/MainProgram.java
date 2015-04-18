package program;

import characters.NPC;
import characters.Player;
import equipment.Equipped;
import equipment.Inventory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainProgram extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  
  
  
  @Override
  public void start(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root, 500, 500, Color.WHITE);

    GridPane gridpane = new GridPane();
    gridpane.setPadding(new Insets(5));
    gridpane.setHgap(10);
    gridpane.setVgap(10);
    
    Inventory inventory = new Inventory(20);
	Equipped equipped = new Equipped();
    
    Player player = new Player(inventory, equipped, 50, 100, 100, 0, 1, "player0");
	Player player1 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "player1");
	Player player2 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "player2");
	Player player3 = new Player(inventory, equipped, 50, 100, 100, 0, 1, "player3");
	
	NPC monster =  new NPC(50, 500, 0, 3, 1, "m1");
	NPC monster1 =  new NPC(50, 500, 0, 3, 1, "m2");
	NPC monster2 =  new NPC(50, 500, 0, 3, 1, "m3");
	NPC monster3 =  new NPC(50, 500, 0, 3, 1, "m4");
	
	Player[] players = {player, player1, player2, player3};
	NPC[] monsters = {monster, monster1, monster2, monster3};
	Combat combat =  new Combat(players, monsters);
	
	

    Label notification = new Label("You have found a monster! Press R to fight!");
    GridPane.setHalignment(notification, HPos.CENTER);
    gridpane.add(notification, 0, 0);
    
    //=============PLAYERS==================================
    Label[] playersLabel = new Label[players.length];
    Label[] playersHP = new Label[players.length];
    Label[] playersMP = new Label[players.length];
    
    
    for (int i = 0; i < players.length; i++) {
		playersLabel[i] = new Label("PLAYER " + i + ":");
		gridpane.add(playersLabel[i], 0, 4*i + 3);
		
		playersHP[i] = new Label("HP: " + players[i].getHealth());
		gridpane.add(playersHP[i], 0, 4*i + 4);
		
		playersMP[i] = new Label("MP: " + players[i].getMana());
		gridpane.add(playersMP[i], 0, 4*i + 5);
	}
    
    //=============MONSTERS=====================================
    Label[] monstersLabel = new Label[monsters.length];
    Label[] monstersHP = new Label[monsters.length];
    Label[] monstersMP = new Label[monsters.length];
    
    
    for (int i = 0; i < monsters.length; i++) {
		monstersLabel[i] = new Label("MONSTER " + i + ":");
		gridpane.add(monstersLabel[i], 9, 4*i + 3);
		
		monstersHP[i] = new Label("HP: " + monsters[i].getHealth());
		gridpane.add(monstersHP[i], 9, 4*i + 4);
		
		monstersMP[i] = new Label("MP: " + monsters[i].getMana());
		gridpane.add(monstersMP[i], 9, 4*i + 5);
	}
 
    
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke) {
        	System.out.println(ke.getCode());
        	if(ke.getCode() == KeyCode.UP){
        		notification.setText("UP");
    		}
    		else if(ke.getCode() == KeyCode.DOWN){
    			notification.setText("DOWN");
    		}
    		else if(ke.getCode() == KeyCode.RIGHT){
    		}
    		else if(ke.getCode() == KeyCode.LEFT){
    		}
    		else if(ke.getCode() == KeyCode.R){
    		}
    		else if(ke.getCode() == KeyCode.Z){
    		}
    		else if(ke.getCode() == KeyCode.X){
    			//Attack
    			//update stats on screen
    			//
    		}
        }
    });

    root.getChildren().add(gridpane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}