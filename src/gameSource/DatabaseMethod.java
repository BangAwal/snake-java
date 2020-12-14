package gameSource;

import javax.swing.JTable;

public abstract class DatabaseMethod {
	public abstract void input(int score, String date);
	public abstract void show(JTable table);
}
