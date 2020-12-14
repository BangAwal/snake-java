package gameSource;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gameSource.Main.STATE;


public class Menu extends MouseAdapter implements Rendering {
	
	private Main main;				//Initialization Main Class
	
	private ImageIcon playIcon;
	private ImageIcon scoreIcon;
	private ImageIcon quitIcon;
	private ImageIcon backMenuIcon;
	
	//Constructor 
	public Menu(Main main) {
		this.main = main;
	}
	
	//Render The Visuals
	public void render(Graphics graphics) {
		
		if (Main.gameState == STATE.Menu) {
							
			playIcon = new ImageIcon("res/img/button/play.png");
			playIcon.paintIcon(main, graphics, 350, 150);
			
			scoreIcon = new ImageIcon("res/img/button/score.png");
			scoreIcon.paintIcon(main, graphics, 350, 250);
	
			quitIcon = new ImageIcon("res/img/button/quit.png");
			quitIcon.paintIcon(main, graphics, 350, 350);
									
		} else if (Main.gameState == STATE.Game) {
			graphics.setColor(Color.white);		
			graphics.drawString("Pause", 100, 44);
			
			graphics.drawString("Back To Menu", 170, 44);		
			graphics.drawRect(165, 30, 90, 20);
			
		} else if (Main.gameState == STATE.End) {
			graphics.setColor(Color.white);
			graphics.setFont(new Font("Courier New", Font.BOLD, 50));
			graphics.drawString("Game Over", 340, 300);
			
			graphics.drawString("Scores: " + Gameplay.score, 340, 350);
			
			backMenuIcon = new ImageIcon("res/img/button/backtom.png");
			backMenuIcon.paintIcon(main, graphics, 360, 390);
				
		}
	}
	
	//Where The Mouse Pointing
	private boolean mouseOver(int mx, int my ,int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			} 
		} else {
			return false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (Main.gameState == STATE.Menu) {
			//Play Button
			if (mouseOver(mx, my, 350, 150, 200, 64)) {
				Main.gameState = STATE.Game;
			}	
			//Score Button
			if (mouseOver(mx, my, 350, 250, 200, 64)) {
				//Initiate Connection To DB
				DatabaseConn showConn = new DatabaseConn();
				
				//Initiate Frame
				JFrame frame = new JFrame(); 
		        frame.setTitle("Scoreboard"); 
		       
		        //Initiate Table
		        JTable scoreTable = new JTable();
		        scoreTable.setModel(new DefaultTableModel(
		            new Object [][] {

		            },
		            new String [] {
		                "Score", "Date"
		            }
		        ));
		        JScrollPane scrollPane =new JScrollPane(scoreTable);
				
		        //Setting Frame
				frame.add(scrollPane); 
			    frame.setSize(370, 300); 
			    frame.setVisible(true); 
		        frame.setLocationRelativeTo(null);
		        
		        showConn.show(scoreTable); 				//Fetch Data From Database and Put it in table
			}
			//Quit Button
			if (mouseOver(mx, my, 350, 350, 200, 64)) {
				System.exit(1);
			}
			
			
		}
		
		
		if (Main.gameState == STATE.Game) {
			//Back to menu from pause
			if (mouseOver(mx, my, 165, 30, 90, 20)) {
				Main.gameState = STATE.Menu;
				Main.paused = false;
				Gameplay.score = 0;
				Gameplay.lengthOfSnake = 3;
				Gameplay.moves = 0;
				Gameplay.level = 1;
			}
			
		}
		if (Main.gameState == STATE.End) {
			//Back to menu from Game Over
			if (mouseOver(mx, my, 360, 390, 230, 64)) {
				Main.gameState = STATE.Menu;
				Gameplay.score = 0;
				Gameplay.lengthOfSnake = 3;
				Gameplay.moves = 0;
				Gameplay.level = 1;
			}
		}
	}


}
