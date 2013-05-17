package edu.ucsb.cs56.S12.choice.issue842;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.io.*;
import javax.swing.JFrame;

/**
 * RogueController - Handles user input and gamestate.
 * 	Will eventually be split up into separate components,
 * 	but for the sake of having something that runs,
 * 	RogueController both creates a RoguePanel and allows the user to manipulate it.
 * @author Clayven Anderson and Jonathan Tan
 * @author Minh Le
 */
public class RogueController extends JFrame implements KeyListener
{
	
	//Unique serialVersion identification. Generated by Eclipse.
	//private static final long serialVersionUID = 8465305666089157172L;
	//x and y is the player intial position
	private int x=40;
	private int y=12;

	private Random randomNumber = new Random();
	
	// origX and origY is the position the character is at before the it moves
	private int origX;
	private int origY;

	//Canvas - The RoguePanel instance everything is drawn to.
	private RoguePanel canvas;

	
	// handles all game state from attack and damage to remove of monsters and player
	private LogicEngine logicHandler;
	
	/**
	 * No parameters.
	 * Constructor initializes the RoguePanel canvas, logicEngine logicHandler and declares that it is listening for keys.
	 */
	public RogueController(){
		super();
		canvas = new RoguePanel();
		add(canvas);
		pack();
		logicHandler = new LogicEngine();
		addKeyListener(this);
	}

	/**
	 * Handles movement of player by checking if it can move there first through the logic engine
	 * if it can move, invoke the canvas to animate it
	 * if it can't, it checks if its because of out of bounds or a monster
	 * if its a monster the player will attack it and its its dead the canvas will animate the removal of the monster
	 */
	public void moveHero(){
		if(!logicHandler.movable(x,y,origX, origY)){
			
				if(logicHandler.monsterIsDead(x,y)){
					canvas.clear(x,y);
				}

			x = origX;
			y = origY;		
		}
		canvas.moveHeroAnimated(x, y,logicHandler.getPlayer().getHitPoints(),logicHandler.getPlayer().getScore());
	}
	
	/**
	 * Handles movement of all monsters by checking if it can move there first through the logic engine
	 * if it can move, invoke the canvas to animate it
	 * if it can't, it checks if its because of out of bounds or a monster or a player
	 * if its a player, the monster will attack it and if its a monster it will eat it
	 */
	public void moveMonster(){
		//xPos,yPos is the position the monster is going to move to
		int gridWidth, gridHeight, xPos, yPos;
		int[] direction = new int[2];
		Monster mon[][] = logicHandler.getMonsters();
		gridHeight = canvas.getGridHeight();
		gridWidth = canvas.getGridWidth();

		/* loops through all the monsters
		xOrig,yOrig is the position the monster is at right now */
	     for (int xOrig = 0; xOrig < gridWidth; xOrig++) {	  
	          for (int yOrig = 0; yOrig < gridHeight; yOrig++) {
	        	  
	        	  if(mon[xOrig][yOrig]!=null){
	        		  // gets the direction of movement of the monster at xOrig, yOrig
	        		  direction = mon[xOrig][yOrig].getDirection(logicHandler.getPlayerPosition());
	        		  xPos= xOrig + direction[0];
	        		  yPos = yOrig + direction[1];
	        		  
	        		  if(logicHandler.movable(xPos, yPos,xOrig,yOrig)){
	        			  
	        			  canvas.moveMonster(xPos, yPos);
	        		  }else{
	        			  if(xPos>=0 && xPos<=80 && yPos>=0 && yPos<=24){
	        			  //display the you were attacked flag if the collision was with a player
	        			  if(logicHandler.getObject(xPos, yPos) instanceof Player){
	        				  canvas.monsterAttack();
	        				  canvas.moveHeroAnimated(x, y,logicHandler.getPlayer().getHitPoints(),logicHandler.getPlayer().getScore());
	        			  }
	        			  canvas.moveMonster(xOrig, yOrig);

	        			  }
	        		  }
	        	  }
	          }
	     }
	     //update the monster list in logic handler
	    logicHandler.storeMonsters();
	    fillEmptySpace();
	}
	
	public void fillEmptySpace(){
		Object floor[][];
		floor = logicHandler.getFloor();
	     for (int x= 0; x < canvas.getGridWidth()-1; x++) {	  
	          for (int y = 0; y < canvas.getGridHeight()-1; y++) {
	        	  if(floor[x][y]== null){
	        		  canvas.emptySpace(x,y);
	        	  }
	          }
		
	     }
	}
	
	/**
	 * Checks to see if player is dead
	 * 
	 */
	public void checkPlayerStatus(){
	    int[] array= new int[5];
	    int a  = 0;
	       if( logicHandler.getGameOver() == true){
	    	canvas.clear();
			try{ 
		    File myFile = new File( "Score.txt");
		    FileReader fileReader = new FileReader("Score.txt");
		    BufferedReader reader = new BufferedReader(fileReader);
		    String line = null; 
		    while((line = reader.readLine())!= null){
			array[a]= Integer.parseInt(line);
			a++;
		    }
		}catch (Exception ex){
		    ex.printStackTrace();
			}
	    	canvas.displayLosingScreen(logicHandler.getPlayer().getScore(),array); 
	     }
	    if(logicHandler.playerIsDead()&& logicHandler.getGameOver() == false){
		try{ 
		    File myFile = new File( "Score.txt");
		    FileReader fileReader = new FileReader("Score.txt");
		    BufferedReader reader = new BufferedReader(fileReader);
		    String line = null; 
		    while((line = reader.readLine())!= null){
			array[a]= Integer.parseInt(line);
			a++;
		    }
		}catch (Exception ex){
		    ex.printStackTrace();
		}
		int temp=0;
		int temp2 = 0;
		for(int count = 0;count <5;count++){
		    if(array[count] < logicHandler.getPlayer().getScore()){
			temp = array[count];
			array[count] = logicHandler.getPlayer().getScore();
			if( count != 4){
			    for( int c = count+1; count < 5; count++){
				temp2 = array[c];
				array[c] = temp;
				temp = temp2;
			    }
			}
			break;
		    }
		}
		//canvas.displayLosingScreen(logicHandler.getPlayer().getScore(),array);
		try{
		    FileWriter writer = new FileWriter("Score.txt");
		    for( int b = 0 ; b< 5;b++){
			writer.write(""+array[b]+ "\n");
		    }
		    writer.close();
		}catch(IOException ex){
		    ex.printStackTrace();
		}
		canvas.clear();
		canvas.displayLosingScreen(logicHandler.getPlayer().getScore(),array);
		logicHandler.setGameOver(true);
	    }
	    // if( logicHandler.getGameOver() == true){
	    // 	canvas.clear();
	    // 	//canvas.displayLosingScreen(logicHandler.getPlayer().getScore(),array); 
	    //  }
	    
	    
	}
    
	/**
	 * Check to see if all monsters are dead
	 */
	public void checkAllMonsterStatus(){
		int gridWidth, gridHeight;
		Monster mon[][] = logicHandler.getMonsters();
		gridHeight = canvas.getGridHeight();
		gridWidth = canvas.getGridWidth();
		
	     for (int xOrig = 0; xOrig < gridWidth; xOrig++) {	  
	          for (int yOrig = 0; yOrig < gridHeight; yOrig++) {
	        	  if(mon[xOrig][yOrig]!=null){
	        		  return;
	        	  }
	          }
	     }
	     for(int a =0 ; a < 10 ;a++){
		 logicHandler.createMonster();}
	     //    canvas.clear();
	     //	canvas.displayWinningScreen();
	     
		
	}
	

	
	
	/**
	 * Method mandated by KeyListener interface.
	 * Calls moveHero().
	 * @param key Keystroke event fired by keyboard.
	 */
	public void keyPressed(KeyEvent key){	
		
	    
	    
		//WASD moves
		origX = x; 
		origY = y;
		switch (key.getKeyChar()){
		case 'w'	    :	 this.y--; break;
		case 'a'	    :  	 this.x--; break;
		case 'd'	    :	 this.x++; break;
		case 's'		:	 this.y++; break;
		default			:	return;
		}

		canvas.clear();
		//Writes last received input.
		canvas.write(key.getKeyChar(),7,23,RoguePanel.white,RoguePanel.black);
		moveHero();
		moveMonster();
		if(randomNumber.nextInt(100)==0){
			logicHandler.createMonster();
		}
		checkPlayerStatus();
		checkAllMonsterStatus();
		canvas.repaint();
	}
	
	/**
	 * Method required by the KeyListener interface.
	 * Doesn't do anything yet.
	 */
	public void keyReleased(KeyEvent key){
		
	}
	
	/**
	 * Method required by the KeyListener interface.
	 * Doesn't do anything yet.
	 */
	public void keyTyped(KeyEvent key){
		
	}
	
	public static void main(String[] args){
		RogueController mainControl = new RogueController();
		mainControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainControl.setVisible(true);
		
		//TEMPORARY MAIN SCREEN
		mainControl.canvas.write("MOVE WITH W A S D. Eat all the monsters to win",9,12,RoguePanel.white,RoguePanel.black);
		
		
	}//Main Delimit
	
}//Class Delimit
