package gameSource;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;


public class Main extends Canvas implements Runnable {

	//Generate Serialization For Main Class
	private static final long serialVersionUID = 1550691097823471818L;
	
	private Thread thread;						//Initialization Thread
	private boolean running = false;			//Boolean for game if its run or not
	public static boolean paused = false;		//Boolean for game if its pause or not
	private Menu menu;							//Initialization Menu Class
	private Gameplay gameplay;					//Initialization GamePlay Class
	
	private ImageIcon titleIcon;				//Initialization ImageIcon
	private ImageIcon backgroundIcon;			//Initialization background
	
	//Creating Enumeration For Game State
	public enum STATE {
		Menu,
		Game,
		End
	};
	
	//Initiate Starting Value Of gameState
	public static STATE gameState = STATE.Menu;
	
	//Constructor
	public Main () {
		menu = new Menu(this);					//Creating Instance For Menu Class
		gameplay = new Gameplay(this);			//Creating Instance For GamePlay Class
		this.addKeyListener(gameplay);			//Adding Key Listener For GamePlay
		this.addMouseListener(menu);			//Adding Mouse Listener For Menu
		AudioPlayer.load();						//Load Music From AudioPlayer Class
		AudioPlayer.getMusic("music").loop();	//Getting the music and loop it
		new Window("Snake", this);				//Creating Instance For Window Class to Create The Window
		
	}
	
	
	private void render() {
		//Create Buffer Strategy To Render the graphic so we can draw Everything before display it
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null) {
			this.createBufferStrategy(3); 		//Improving Visuals or Triple Buffering
			return;
		}
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		//Draw title image border
		graphics.setColor(Color.white);
		graphics.drawRect(24, 10, 851, 55);
				
		//Draw the title image
		titleIcon = new ImageIcon("res/img/snaketitle.png");
		titleIcon.paintIcon(this, graphics, 25, 11);
		
		//Draw the background image
		backgroundIcon = new ImageIcon("res/img/background.jpg");
		backgroundIcon.paintIcon(this, graphics, 25, 75);
		
		
		//Render The Graphics For Every State
		if (gameState == STATE.Menu || gameState == STATE.End || paused) {
			menu.render(graphics);
		} else if (gameState == STATE.Game) {
			gameplay.render(graphics);
		} 
		
		graphics.dispose();			//Terminate Current Window Not Every Window
		bufferStrategy.show();		//Make The Next Buffer Visible
	}
	
	//Preventing thread interference and memory consistency errors and Start The Thread
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	//Stop The Thread
	public synchronized void stop() {
		try {
			thread.join();
			running = false; 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Running The Thread And Everything 
	public void run() {
		this.requestFocus();	// Request Focus From Component
		while(running){ 
			render(); 			// render the visuals of the game
		}
		stop(); 				// no longer running stop the thread
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
