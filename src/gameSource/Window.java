package gameSource;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Window extends Canvas {
	
	//Generate Serialization For Window Class
	private static final long serialVersionUID = 4140568926534945031L;

	public Window(String title, Main main) {
		//Creating Frame
		JFrame frame = new JFrame(title);
		//Frame Configuration
		frame.setBounds(10, 10, 905, 700);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//Adding and Start The Frame
		frame.add(main);
		main.start();
		
	}
	
}
