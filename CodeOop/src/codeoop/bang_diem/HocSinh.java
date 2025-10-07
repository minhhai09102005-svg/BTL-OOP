/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.bang_diem;
import java.util.*;
public class HocSinh implements Comparable<HocSinh>{
    private String name;
    private double value1;
    private double value2;
    private double value3;
    private double value4;
    private double value5;
    private double value6;
    private double value7;
    private double value8;
    private double value9;
    private double value10;
    private double gpa;
    private String rank;
    private static int ma = 1;
    private int code;
    
    @Override
    public int compareTo(HocSinh other){
        if(this.gpa != other.gpa){
            return Double.compare(this.gpa, other.gpa) * -1;
        }
        return Integer.compare(this.code, other.code);
    }
    
    public void input(Scanner sc){
        sc.nextLine();
        code = ma;
        ma++;
        name = sc.nextLine();
        value1 = sc.nextDouble();
        value2 = sc.nextDouble();
        value3 = sc.nextDouble();
        value4 = sc.nextDouble();
        value5 = sc.nextDouble();
        value6 = sc.nextDouble();
        value7 = sc.nextDouble();
        value8 = sc.nextDouble();
        value9 = sc.nextDouble();
        value10 = sc.nextDouble();
        gpa = (value1 * 2 + value2 * 2 + value3 + value4 + value5 + value6 + value7 + value8 + value9 + value10) /12;
        if(gpa >= 9){
            rank = "XUAT SAC";
        }
        else if(gpa >= 8){
            rank = "GIOI";
        }
        else if(gpa >= 7){
            rank = "KHA";
        }
        else if(gpa >= 5){
            rank = "TB";
        }
        else{
            rank = "YEU";
        }
        gpa = Math.round(gpa * 10.0) / 10.0;

    }
    
    public void output(){
        System.out.printf("HS%02d %s %.1f %s", code, name, gpa, rank);
    }
}
