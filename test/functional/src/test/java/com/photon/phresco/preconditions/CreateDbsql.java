package com.photon.phresco.preconditions;

import java.sql.*;

public class CreateDbsql{
 // public static void main(String[] args) {}
  public CreateDbsql(String methodName){
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
  String table = "CREATE DATABASE " +methodName;
  st.executeUpdate(table);
  
  System.out.println("Database created successfully!");
  }
  catch(SQLException s){
  System.out.println("Database already exists!");
  }
  con.close();
  }
  catch (Exception e){
  e.printStackTrace();
  }
  }
}