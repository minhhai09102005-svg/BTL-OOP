/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.lang.Math.*;

class PhanSo{
    private long tu;
    private long mau;
    
    private long ucln(long a, long b){
        if(b == 0){
            return a;
        }
        else{
            return ucln(b, a % b);
        }
    }
    
    private long bcnn(long a, long b){
        return (a * b)/ucln(a, b);
    }
    
    public PhanSo(long tu, long mau){
        this.tu = tu;
        this.mau = mau;
    }
    
    public void tong(PhanSo p2){
        long newmau = bcnn(mau, p2.mau);
        long newtu = (tu * (newmau / mau)) + (p2.tu * (newmau / p2.mau));
        long gcd = ucln(newtu, newmau);
        newtu = newtu / gcd;
        newmau = newmau / gcd;
        System.out.print(newtu + "/" + newmau);
    }
} 

public class tong_phan_so {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        long t1 = sc.nextLong();
        long m1 = sc.nextLong();
        PhanSo p1 = new PhanSo(t1, m1);
        long t2 = sc.nextLong();
        long m2 = sc.nextLong();
        PhanSo p2 = new PhanSo(t2, m2);
        p1.tong(p2);
    }
}
