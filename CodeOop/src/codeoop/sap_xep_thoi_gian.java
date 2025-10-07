/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
class ThoiGian{
    private int gio;
    private int phut;
    private int giay;
    public void input(Scanner sc){
        gio = sc.nextInt();
        phut = sc.nextInt();
        giay = sc.nextInt();
    }
    public void output(){
        System.out.println(gio + " " + phut + " " + giay);
    }
    public static Comparator<ThoiGian> cmp = new Comparator<ThoiGian>(){
        public int compare(ThoiGian t1, ThoiGian t2) {
            if (t1.gio != t2.gio) {
                return t1.gio - t2.gio;
            } else if (t1.phut != t2.phut) {
                return t1.phut - t2.phut;
            }
            return t1.giay - t2.giay;
        }
    };
}
public class sap_xep_thoi_gian {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ThoiGian[] a = new ThoiGian[n];
        for(int i = 0; i < n; i++){
            a[i] = new ThoiGian();
            a[i].input(sc);
        }
        Arrays.sort(a, ThoiGian.cmp);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
