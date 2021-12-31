package com.czarnacki.bazasprzetu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySql {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void ConnectToMySQL() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1/bazasprzetu?"
					+ "useUnicode=true&useJDBCCompilantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
					+ "&user=root&password=");
			statement = connect.createStatement();
			System.out.println("Połączono z bazą danych");
					
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Błąd połączenia z bazą danych");
		} 	
		
	}
	
	//tu funkcja close do zamykania połączenia z baza dancyh
	public void Close() {
		
	}
	
	public ResultSet Select(String zapytanie) throws SQLException {
		
		resultSet = statement.executeQuery(zapytanie);
		return resultSet;
		
	}
	
	
	//muszą byc inne rezultaty piod rozne zapytania
	
	public String[][] Logowanie(ResultSet resultSet) throws SQLException {
		System.out.println("logowanie");
		resultSet.last();
		int size = resultSet.getRow();
		resultSet.beforeFirst();
		String[][] wynik = new String[size][2];
        int i = 0;
        while (resultSet.next()) {
            
			
            String login = resultSet.getString("login");
            String haslo = resultSet.getString("password");
            wynik[i][0] = login;  
            wynik[i][1]	= haslo;
            i++;		           		
            
            System.out.println("login: " + login);
            System.out.println("haslo: " + haslo);
            
        }
		return wynik;
	}
	
	
	public String[][] zapytanieKierownicy(ResultSet resultSet) throws SQLException {
		System.out.println("logowanie");
		resultSet.last();
		int size = resultSet.getRow();
		resultSet.beforeFirst();
		
        int i = 0;
        ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		String[][] wynik = new String[size][columnsNumber];
		
		while (resultSet.next()) {
            
			
            String id = resultSet.getString("id");
            String nazwisko = resultSet.getString("nazwisko");
            String imie = resultSet.getString("imie");
            String status = resultSet.getString("status");
            wynik[i][0] = id;  
            wynik[i][1]	= nazwisko;
            wynik[i][2]	= imie;
            wynik[i][3]	= status;
            i++;		           		
            
           
            
        }
		return wynik;
	}
	
	public boolean insertKierownicy(String n, String i, int s) throws SQLException {
		
		String query = " insert into kierownicy (nazwisko, imie, status)"
		        + " values (?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = connect.prepareStatement(query);
		      preparedStmt.setString (1, n);
		      preparedStmt.setString (2, i);
		      preparedStmt.setInt    (3, s);
		      preparedStmt.execute();
		      return true;
	}
	
public boolean poprawKierownicy(int id, String n, String i, int s) throws SQLException {
		
		String query = "update kierownicy set nazwisko = ?, imie = ?, status = ? where id = ?";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = connect.prepareStatement(query);
		      preparedStmt.setString (1, n);
		      preparedStmt.setString (2, i);
		      preparedStmt.setInt    (3, s);
		      preparedStmt.setInt    (4, id);
		      preparedStmt.execute();
		      return true;
	}
	
}
