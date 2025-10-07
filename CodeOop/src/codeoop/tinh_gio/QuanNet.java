/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.tinh_gio;
import java.util.*;
public class QuanNet implements Comparable<QuanNet>{
    private String ma;
    private String name;
    private String in;
    private String out;
    private int minutes;
    private String time = "";
    
    private void xuLy(){
        int minutes1 = 0;
        int minutes2 = 0;
        String[] a = in.split(":");
        minutes1 = Integer.valueOf(a[0]) * 60 + Integer.valueOf(a[1]);
        String[] b = out.split(":");
        minutes2 = Integer.valueOf(b[0]) * 60 + Integer.valueOf(b[1]);
        minutes = minutes2 - minutes1;
    }
    
    @Override
    public int compareTo(QuanNet other){
        return Integer.compare(this.minutes, other.minutes) * -1;
    }
    
    public void input(Scanner sc){
        ma = sc.nextLine();
        name = sc.nextLine();
        in = sc.nextLine();
        out = sc.nextLine();
        xuLy();
        time = (minutes / 60) + " gio " + (minutes % 60) + " phut";
    }
    
    public void output(){
        System.out.printf("%s %s %s\n", ma, name, time);
    }
}


