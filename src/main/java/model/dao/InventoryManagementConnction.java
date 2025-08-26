package model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InventoryManagementConnction {
	static {
		try{
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private Properties props;
	public InventoryManagementConnction() {
		this.props = new Properties();
		try(InputStream input = InventoryManagementConnction.class.getClassLoader().getResourceAsStream("db.properties")){
			if(input == null) {
				throw new RuntimeException();
			}
			props.load(input);
			System.out.println("db.properties loaded");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		String url = props.getProperty("db.url");
		String db = props.getProperty("db.name");
		String user = props.getProperty("db.user");
		String password = props.getProperty("db.password");
		try {
			return DriverManager.getConnection(url + db, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
