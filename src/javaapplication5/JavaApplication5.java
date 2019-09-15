package javaapplication5;

import java.util.Scanner;
import  javaapplication5.Dao;


public class JavaApplication5 {
 
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String ans;
        do {

            System.out.println("Menu: ");
            System.out.println("1.  show List");
            System.out.println("2.  Insert record");
            System.out.println("3.  update record");
            System.out.println("4.  Delete record");
            System.out.println("5.  singleList");

            System.out.println("Enter your choice :");

            
            int a = sc.nextInt();

            switch (a) {

                case 1:
                    Dao.getDataList();
                    break;
                case 2:
                   Dao.insertData();
                    break;
                case 3:
                    Dao.updateData();
                    break;
                case 4:
                    Dao.deleteData();
                    break;
                case 5:
                   Dao.getSingleList();
                    break;

                default:
                    System.out.println("Please choose appropriate number");

            }
            System.out.println("Continue for Not?(Y/N)?");
            sc.nextLine();
            ans = sc.nextLine();
        } while (ans.equalsIgnoreCase("Y"));
    }

   
    
}
