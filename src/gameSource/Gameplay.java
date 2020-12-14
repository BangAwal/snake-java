package gameSource;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import javax.swing.Timer;

import gameSource.Main.STATE;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener, Rendering{
	
	//Generate Serialization For Game Play Class
	private static final long serialVersionUID = -6624370677244940747L;
	
	//Snake Length Coordinates
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];
	
	//Movement
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	//Icon For Direction 
	private ImageIcon leftMouthIcon;
	private ImageIcon rightMouthIcon;
	private ImageIcon upMouthIcon;
	private ImageIcon downMouthIcon;
	
	//Icon For Direction if level 3
	private ImageIcon leftMouthIconLvl3;
	private ImageIcon rightMouthIconLvl3;
	private ImageIcon upMouthIconLvl3;
	private ImageIcon downMouthIconLvl3;
	
	//Initialization First Condition
	public static int lengthOfSnake = 3;
	public static int score = 0;
	public static int moves = 0;
	public static int level = 1;
	
	
	//Icon For Snake Body And Food
	private ImageIcon snakeImageIcon;
	private ImageIcon bodyLevelUpIcon;
	private ImageIcon foodImageIcon;

	//Initiate Array For Food Position
	private int [] foodXPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400
			, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750};
	private int [] foodYPos = {125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400
			, 425, 450, 475, 500, 525, 550, 575};
	
	//Variable for random food spawn
	private Random random = new Random();
	private int mouseXPos;
	private int mouseYPos;
	
	private Main main;								//Initialize Main Class
	
	//Initialization Timer
	private Timer timer;
	private int delay = 100;
	
	DatabaseConn inputConn = new DatabaseConn();	//Initiate Database Connection

	//Constructor
	public Gameplay(Main main) {
		
		//Focus Configuration
		this.main = main;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//Start Timer For Delay Movement
		timer = new Timer(delay, this);
		timer.start();
			
	}
	
	//Render The Visual
	public void render(Graphics graphics) {
		
		//Player Start Position
		if(moves == 0) {
			snakeXLength[2] = 100;
			snakeXLength[1] = 125;
			snakeXLength[0] = 150;
			
			snakeYLength[2] = 150;
			snakeYLength[1] = 150;
			snakeYLength[0] = 150;
		}
		
		//Draw Scores
		graphics.setColor(Color.white);
		graphics.setFont(new Font("arial", Font.PLAIN, 13));
		graphics.drawString("Scores: " + score, 780, 25);
		
		//Draw Scores
		graphics.drawString("Length: " + lengthOfSnake, 780, 40);
		
		//Draw Level
		graphics.drawString("Level:    " + level, 780, 55);

		//Paint The Snake Icon
		rightMouthIcon = new ImageIcon("res/img/unlevel3/rightmouth.png");
		rightMouthIcon.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);
		
		//Loop for snake head and body position
		for (int i = 0; i < lengthOfSnake; i++) {
			if (i == 0  && right) {
				if (level != 3) {
					rightMouthIcon = new ImageIcon("res/img/unlevel3/rightmouth.png");
					rightMouthIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				} else {
					rightMouthIconLvl3 = new ImageIcon("res/img/level3/rightmouthLvl3.png");
					rightMouthIconLvl3.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				}
				
			}
			if (i == 0  && left) {
				if (level != 3) {
					leftMouthIcon = new ImageIcon("res/img/unlevel3/leftmouth.png");
					leftMouthIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				} else {
					leftMouthIconLvl3 = new ImageIcon("res/img/level3/leftmouthLvl3.png");
					leftMouthIconLvl3.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				}
				
			}
			if (i == 0  && down) {
				if (level != 3) {
					downMouthIcon = new ImageIcon("res/img/unlevel3/downmouth.png");
					downMouthIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				} else {
					downMouthIconLvl3 = new ImageIcon("res/img/level3/downmouthLvl3.png");
					downMouthIconLvl3.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				}
				
			}
			if (i == 0  && up) {
				if (level != 3) {
					upMouthIcon = new ImageIcon("res/img/unlevel3/upmouth.png");
					upMouthIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				} else {
					upMouthIconLvl3 = new ImageIcon("res/img/level3/upmouthLvl3.png");
					upMouthIconLvl3.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				}
				
			}
			
			if (i != 0) {
				if (level != 3) {
					snakeImageIcon = new ImageIcon("res/img/unlevel3/snakeimage.png");
					snakeImageIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				} else {
					bodyLevelUpIcon = new ImageIcon("res/img/level3/snakeimageLvl3.png");
					bodyLevelUpIcon.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
				}
				
			}
		}
		
		//Random Food Position And If Collision
		if (foodXPos[mouseXPos] == snakeXLength[0] && foodYPos[mouseYPos] == snakeYLength[0]) {
			score++;
			lengthOfSnake++;
			mouseXPos = random.nextInt(28);
			mouseYPos = random.nextInt(19);	
		}
		foodImageIcon = new ImageIcon("res/img/enemy.png");
		foodImageIcon.paintIcon(this, graphics, foodXPos[mouseXPos], foodYPos[mouseYPos]);

		//Game Over State 
		for (int k = 1; k < lengthOfSnake; k++) {
			if (level == 1) {
				//Level 1, Death only when touch its own body
				if (snakeXLength[k] == snakeXLength[0] && snakeYLength[k] == snakeYLength[0]) {
					Main.gameState = STATE.End;

					timer.stop();
					delay = 100;
					timer = new Timer(delay, this);
					timer.start();
					
					repaint();
					
				}
			} else {
				//Except Level 1, Die when touch its own body or touch the red border
				if (snakeXLength[k] == snakeXLength[0] && snakeYLength[k] == snakeYLength[0] ||
					snakeXLength[k] == 25 || snakeXLength[k] == 800 ||
					snakeYLength[k] == 75 || snakeYLength[k] == 625
					) {
					Main.gameState = STATE.End;

					timer.stop();
					delay = 100;
					timer = new Timer(delay, this);
					timer.start();

					repaint();
				}
			} 	
		}
		
		//Set For Level 2
		if (score == 10) {
			level = 2;
		}
		
		//Draw The Border
		if (score >= 10) {
			graphics.setColor(Color.red);
			//Left And Right Border
			graphics.fillRect(24, 75, 30, 575);
			graphics.fillRect(800, 75, 75, 575);
			
			//Up And Down Border
			graphics.fillRect(25, 75, 845, 25);
			graphics.fillRect(25, 625, 845, 25);
			
			
			graphics.setColor(Color.white);
			Font sub = new Font("arial", 1, 30);		
			graphics.setFont(sub);
			graphics.drawString("D", 825, 200);
			graphics.drawString("O", 825, 230);
			graphics.drawString("N", 825, 260);
			graphics.drawString("T", 825, 290);
			
			graphics.drawString("T", 825, 330);
			graphics.drawString("O", 825, 360);
			graphics.drawString("U", 825, 390);
			graphics.drawString("C", 825, 420);
			graphics.drawString("H", 825, 450);
			
		}
		
		//Set For Level 3
		if (score == 20) {
			level = 3;
		}
		
		//Set the move to false and input the score to database
		if (Main.gameState == STATE.End) {
			right = false;
			left = false;
			up = false;
			down = false;
			
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");  
			String strDate= formatter.format(date);  
			
			inputConn.input(score, strDate);
		}
						
		graphics.dispose(); 			//Terminate Current Window Not Every Window
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		//Except level 3 the timer will use 100 delay
		if (level != 3) {
			timer.start();
		} else {
			//If level 3 the delay will be 50, which faster movement for snake
			if (Main.gameState == STATE.Game) {
				timer.stop();
				delay = 50;
				timer = new Timer(delay, this);
				timer.start();
			} 
			
		}

		//when right is true
		if (right) {
			//Decrease the snake length and add it to next coordinate
			for (int i = lengthOfSnake-1; i>=0 ; i--) {
				snakeYLength[i+1] = snakeYLength[i];
			}
			//make the teleport from right to left
			for (int j = lengthOfSnake; j>=0; j--) {
				//move the X Coordinate
				if (j == 0) {
					snakeXLength[j] = snakeXLength[j] + 25;
				} else {
					snakeXLength[j] = snakeXLength[j-1];
				}
				//If the snake going through the right border it will move to the left border
				if (snakeXLength[j] > 850) {
					snakeXLength[j] = 25;
				}
			}
			repaint();
		}
		
		//when left is true
		if (left) {
			//Decrease the snake length and add it to next coordinate
			for (int i = lengthOfSnake-1; i>=0 ; i--) {
				snakeYLength[i+1] = snakeYLength[i];
			}
			//make the teleport from left to right
			for (int j = lengthOfSnake; j>=0; j--) {
				//move the X Coordinate
				if (j == 0) {
					snakeXLength[j] = snakeXLength[j] - 25;
				} else {
					snakeXLength[j] = snakeXLength[j-1];
				}
				//If the snake going through the left border it will move to the right border
				if (snakeXLength[j] < 25) {
					snakeXLength[j] = 850;
				}
			}
			repaint();
		}
		
		//when up is true
		if (up) {
			//Decrease the snake length and add it to next coordinate
			for (int i = lengthOfSnake-1; i>=0 ; i--) {
				snakeXLength[i+1] = snakeXLength[i];
			}
			//make the teleport from top to bottom
			for (int j = lengthOfSnake; j>=0; j--) {
				//move the Y Coordinate
				if (j == 0) {
					snakeYLength[j] = snakeYLength[j] - 25;
				} else {
					snakeYLength[j] = snakeYLength[j-1];
				}
				//If the snake going through the top border it will move to the bottom border
				if (snakeYLength[j] < 75) {
					snakeYLength[j] = 625;
				}
			}
			repaint();
		}
		
		//when down is true
		if (down) {
			//Decrease the snake length and add it to next coordinate
			for (int i = lengthOfSnake-1; i>=0 ; i--) {
				snakeXLength[i+1] = snakeXLength[i];
			}
			//make the teleport from bottom to top
			for (int j = lengthOfSnake; j>=0; j--) {
				//move the Y Coordinate
				if (j == 0) {
					snakeYLength[j] = snakeYLength[j] + 25;
				} else {
					snakeYLength[j] = snakeYLength[j-1];
				}
				//If the snake going through the bottom border it will move to the top border
				if (snakeYLength[j] > 625) {
					snakeYLength[j] = 75;
				}
			}
			repaint();
		}

		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//If game not paused
		if (!Main.paused) {
			//Pressed Right
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				moves++;
				right = true;
				//Avoid to move directly from left to right
				if (!left) {
					right = true;
				} else {
					right = false;
					left = true;
				}			
				up = false;
				down = false;
			}
			//Pressed left
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				moves++;
				left = true;
				//Avoid to move directly from right to left
				if (!right) {
					left = true;
				} else {
					left = false;
					right = true;
				}			
				up = false;
				down = false;
			}
			//Pressed up
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				moves++;
				up = true;
				//Avoid to move directly from down to up
				if (!down) {
					up = true;
				} else {
					up = false;
					down = true;
				}			
				left = false;
				right = false;
			}
			//Pressed up
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				moves++;
				down = true;
				//Avoid to move directly from up to down
				if (!up) {
					down = true;
				} else {
					down = false;
					up = true;
				}			
				left = false;
				right = false;
			}
		}
		
		//Exit the game if pressed escape
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(1);

		//If pressed P the game will pause
		if (e.getKeyCode() == KeyEvent.VK_P) {
			//Only work in game state
			if (Main.gameState == STATE.Game) {	
				if (Main.paused) {			//If true, set it false
					Main.paused = false;
				} else {					//Else, set it true and stop the movement
					Main.paused = true;
					up = false;
					down = false;
					right = false;
					left = false;
				}
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	


	





}
