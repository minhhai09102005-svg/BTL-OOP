/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.TuyenGV;
import java.util.*;
import codeoop.TuyenGV.GiaoVien;
public class MainGV {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        GiaoVien[] a = new GiaoVien[n];
        for(int i = 0; i < n; i++){
            a[i] = new GiaoVien();
            a[i].input(sc);
        }
        Arrays.sort(a);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
