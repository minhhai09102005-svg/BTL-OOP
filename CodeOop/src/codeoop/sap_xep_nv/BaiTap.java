/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.sap_xep_nv;
import java.util.*;
import codeoop.sap_xep_nv.NhanVien;
public class BaiTap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        NhanVien[] a = new NhanVien[n];
        for(int i = 0; i < n; i++){
            a[i] = new NhanVien();
            a[i].input(sc);
        }
        Arrays.sort(a);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
