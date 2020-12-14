package gameSource;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class DatabaseConn extends DatabaseMethod {
	//Prepare the configuration for database connection
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/snakedb?useTimezone=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;		//Initiate Connection variable
    static Statement stmt;		//Initiate Statement variable

    //Method to input score and current date
	@Override
	public void input(int score, String date) {
		try{
    		//Connect to database
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            //Prepare and execute query
            String insertQuery = "INSERT INTO scoretb(score, date) VALUES ('"+ score +"', '"+ date +"')";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.execute(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void show(JTable table) {
		try{
    		//Connect to database
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            //Prepare and execute query
            ResultSet rs;
            String selectQuery = "SELECT score, date FROM scoretb ORDER BY score DESC";
            PreparedStatement pstmt = conn.prepareStatement(selectQuery);
            rs = pstmt.executeQuery(selectQuery);
            
            //Create Table and Add the data from database to new row in table
            DefaultTableModel tabel1 = (DefaultTableModel)table.getModel();
            tabel1.setRowCount(0);          
            while(rs.next()){
                tabel1.addRow(new Object[]{rs.getInt("score"), rs.getString("date")});
            }
            
        } catch (Exception e) {
        	
        }
		
	}
    
}
