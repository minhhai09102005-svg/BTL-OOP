/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.thu_khoa;
import java.util.*;
public class ThiSinh implements Comparable<ThiSinh>{
    private static int ma = 1;
    private int code;
    private String name;
    private String date;
    private double diem1;
    private double diem2;
    private double diem3;
    public double tong;
    
    @Override
    public int compareTo(ThiSinh other){
        return Double.compare(this.tong, other.tong) * -1;
    }
    
    public void input(Scanner sc){
        code = ma;
        ma++;
        name = sc.nextLine();
        date = sc.nextLine();
        diem1 = sc.nextDouble();
        diem2 = sc.nextDouble();
        diem3 = sc.nextDouble();
        sc.nextLine();
        tong = this.diem1 + this.diem2 + this.diem3;
    }
    
    public void out(){
        System.out.printf("%d %s %s %.1f\n", code, name, date, tong);
    }
}
