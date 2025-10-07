/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.TuyenGV;
import java.util.*;
public class GiaoVien implements Comparable<GiaoVien>{
    private static int m = 1;
    private int code;
    private String name;
    private String ma;
    private String subject;
    private double tinhoc;
    private double chuyenmon;
    private double tong;
    private String status;
    
    private void chuanHoa(){
        if(ma.charAt(0) == 'A'){
            subject = "TOAN";
        }
        if(ma.charAt(0) == 'B'){
            subject = "LY";
        }
        if(ma.charAt(0) == 'C'){
            subject = "HOA";
        }
        if(ma.charAt(1) == '1'){
            tong += 2;
        }
        if(ma.charAt(1) == '2'){
            tong += 1.5;
        }
        if(ma.charAt(1) == '3'){
            tong += 1;
        }
        if(ma.charAt(1) == '4'){
            tong += 0;
        }
    }
    
    @Override
    public int compareTo(GiaoVien other){
        return Double.compare(this.tong, other.tong) * -1;
    }
    
    public void input(Scanner sc){
        sc.nextLine();
        code = m;
        m++;
        name = sc.nextLine();
        ma = sc.nextLine();
        tinhoc = sc.nextDouble();
        chuyenmon = sc.nextDouble();
        tong = tinhoc * 2 + chuyenmon;
        chuanHoa();
        if(tong >= 18){
            status = "TRUNG TUYEN";
        }
        else{
            status = "LOAI";
        }
    }
    
    public void output(){
        System.out.printf("GV%02d %s %s %.1f %s\n", code, name, subject, tong, status);
    }
}
