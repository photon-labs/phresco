package com.photon.phresco.preconditions;

import java.sql.*;

public class DeleteDbsql{
  public static void main(String[] args) {
 Connection con = null;
 String url = "jdbc:mysql://localhost:3306/";
 String dbName = "jdbctutorial";
 String driverName = "com.mysql.jdbc.Driver";
 String userName = "root";
 String password = "";
 try{
  Class.forName(driverName).newInstance();
 con = DriverManager.getConnection(url+dbName, userName, password);
 try{
  Statement st = con.createStatement();
  String table = "DROP DATABASE nodedb";
  st.executeUpdate(table);
  
  System.out.println("Database deleted successfully!");
  }
  catch(SQLException s){
  System.out.println("Database does not exist!");
  }
  con.close();
  }
  catch (Exception e){
  e.printStackTrace();
  }
  }
}