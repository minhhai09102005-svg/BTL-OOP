/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.antique_shop;
import java.util.*;
public class Items {
    private static int code = 1;
    private int ma;
    private String name;
    private String date;
    
    private String chuanHoa(String inputDate) {
        String[] a = inputDate.split("/");
        String result = "";
        int date = Integer.valueOf(a[0]);
        int month = Integer.valueOf(a[1]);
        int year = Integer.valueOf(a[2]);
        if(month > 12){
            String temp = a[0];
            a[0] = a[1];
            a[1] = temp;
        }
        if(date < 10){
            a[0] = "0" + a[0];
        }
        if(month < 10){
            a[1] = "0" + a[1];
        }
        result = a[0] + "/" + a[1] + "/" + a[2];
        return result;
    }

    
    public void input(Scanner sc){
        name = sc.nextLine();
        date = sc.nextLine();
        name = name.toUpperCase();
        date = chuanHoa(date);
    }
    
    public void output(){
        System.out.print(ma + " ");
        System.out.println(name + " ");
        System.out.println(date);
    }
    
    public boolean SearchByCode(int m) {
     if (ma == m) {
         output();
         return true;
    }
        return false;
    }

    public boolean SearchByDate(String s) {
        s = chuanHoa(s);
        if (date.equals(s)) {     // so sánh chuỗi
            output();
            return true;
        }
        return false;
    }

    public boolean SearchByName(String s) {
        s = s.toUpperCase();
        if (name.equals(s)) {     // so sánh chuỗi
            output();
            return true;
        }
        return false;
    }
}
