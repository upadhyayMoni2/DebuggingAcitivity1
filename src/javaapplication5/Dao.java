
package javaapplication5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Dao {
    
     static  Connection conn = null;
     static  Statement stm = null;
     static ResultSet rs = null;
     static String id, regionName;
   static JSONObject singleConnError = new JSONObject();
   static JSONObject singleStmError = new JSONObject();
    static JSONObject singleRsError = new JSONObject();
    
   
 public  static Connection getConnection() {

        try {

            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");

            if (conn != null) {
                System.out.println("connected...");

            } else {
                System.out.println("connection fail...");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }

    
    public static void getDataList() {

        JSONArray mainarrList = new JSONArray();
        JSONObject singleobjList = new JSONObject();

        conn = getConnection();
             try {
                String sql = "select region_id, region_name from regions";
                stm = conn.createStatement();
                int i = stm.executeUpdate(sql);

                rs = stm.executeQuery(sql);

                while (rs.next()) {

                    id = rs.getString("region_id");

                    regionName = rs.getString("region_name");
                    singleobjList.accumulate("id", id);
                    singleobjList.accumulate("regionName", regionName);

                    mainarrList.add(singleobjList);
                    singleobjList.clear();
                }
                System.out.println(mainarrList);
            } catch (SQLException ex) {
                Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
               closeConnection();
        }
    }

    public static void insertData() {

        conn = getConnection();
        JSONObject singleInsert = new JSONObject();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert ID :");
        id = String.valueOf(sc.nextInt());
        sc.nextLine(); 
        System.out.println("Insert Name ");
        regionName = sc.nextLine();
        
        String sql = "insert into regions(region_id , region_name) values('"+id+"','"+regionName+"') ";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);

            if (i > 0) {
                singleInsert.accumulate("message", "Record inserted");
                System.out.println(singleInsert);
               
            } else {
                singleInsert.accumulate("message", "Record Not inserted");
                System.out.println(singleInsert);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
               closeConnection();
        }

    }

    public  static void deleteData() {

        conn = getConnection();
        JSONObject singleDelete = new JSONObject();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Id to delete record");
        
        id = String.valueOf(sc.nextInt());
        
        String sql = "Delete from regions where region_id = '"+id+"'";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);
            if (i > 0) {
                singleDelete.accumulate("message", "Record Deleted");
                System.out.println(singleDelete);
            } else {
                singleDelete.accumulate("message", "Record not Deleted");
                System.out.println(singleDelete);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
               closeConnection();
        }

    }

    public static void getSingleList() {

        conn = getConnection();
        JSONObject singleList = new JSONObject();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter ID to get single record");
        id = String.valueOf(sc.nextInt());
        

        String sql = "select * from regions where region_id='"+id+"'";

        try {
            stm = conn.createStatement();
            int i = stm.executeUpdate(sql);

            rs = stm.executeQuery(sql);

            while (rs.next()) {

                id = rs.getString("region_id");

                regionName = rs.getString("region_name");
                singleList.accumulate("id", id);
                singleList.accumulate("regionName", regionName);

            }
            System.out.println(singleList);

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
               closeConnection();
        }

    }

    public static void updateData() {

        conn = getConnection();
        JSONObject singleupdate = new JSONObject();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose ID to update record:");
        id = String.valueOf(sc.nextInt());
        sc.nextLine(); 
        System.out.println("update name: ");
        regionName = sc.nextLine();
       
        String sql = "update regions set region_name='"+regionName+"' where region_id= '"+id+"'";

        try {
            stm = conn.createStatement();

            int i = stm.executeUpdate(sql);

            if (i > 0) {
                singleupdate.accumulate("message", "Record Updated");
                System.out.println(singleupdate);
           
            } else {
                singleupdate.accumulate("message", "Record Not Updated");
                System.out.println(singleupdate);
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication5.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
               closeConnection();
        }

    }
    
    public static  void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
               singleRsError.accumulate("message","ResultSet error");
                System.out.println(singleRsError); 
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                singleStmError.accumulate("message","Statment error");
                System.out.println(singleStmError);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                singleConnError.accumulate("message","Coonection error");
                System.out.println(singleConnError);
            }
        }
    }

    
}
