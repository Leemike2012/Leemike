package com.the.electricdoor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test2 {
    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        try{
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
            String user = "root";
            String password = "abc";

            Connection conn = DriverManager.getConnection(url,user,password);
            Statement stat = conn.createStatement();
            String sql = "select * from person";
            
            ResultSet rs = stat.executeQuery(sql);  //执行sql

            while(rs.next()){
                String name = rs.getString("name");
                System.out.println(name);
            }





        }catch (SQLException e){
            e.printStackTrace();
    }
    }
        
    
}
