package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import core.ConfigManager;

public class DbManage {
	public static Connection conn = null;
	public static String sqliteDriver = "org.sqlite.JDBC";
	public static String mysqlDriver = "com.mysql.jdbc.Driver";
	// mysql 8.0.11
	public static String mysqlDriver_V8011 = "com.mysql.cj.jdbc.Driver";
	
	public static Connection getConn() {
		if(conn == null){
			Map<String, String> configMap = ConfigManager.getInstance().getConfigMap();
			String database = configMap.get("database");
			String url = configMap.get("sqlurl");
			String username = configMap.get("username");
		    String password = configMap.get("password");
		    String driver = "";
			if(database.toLowerCase().equals("sqlite")){
				driver = sqliteDriver;
			}else if(database.toLowerCase().equals("mysql")){
				driver = mysqlDriver;
//				username = "dev";
//			    password = "fleety";
			}
            //String url = "jdbc:sqlite:test.db";
		    //url = "jdbc:mysql://192.168.0.147:3306/card_manage?characterEncoding=utf-8";
		    try {
		        Class.forName(driver); //classLoader,加载对应驱动
				conn = DriverManager.getConnection(url, username, password);
		       
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	    return conn;
	}
	
	public static void releaseConn() {
		if(conn!=null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DbManage.getConn();
	}
}
