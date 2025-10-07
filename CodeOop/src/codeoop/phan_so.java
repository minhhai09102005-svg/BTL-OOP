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
    public PhanSo(long tu, long mau){
        this.tu = tu;
        this.mau = mau;
    }
    public void rutGon(){
        long gcd = ucln(tu, mau);
        tu = tu / gcd;
        mau = mau / gcd;
    }
    public void print(){
        System.out.print(tu + "/" + mau);
        System.out.println();
    }
}

public class phan_so {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        long t = sc.nextLong();
        long m = sc.nextLong();
        PhanSo p = new PhanSo(t, m);
        p.rutGon();
        p.print();
    }
}
