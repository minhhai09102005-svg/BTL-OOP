/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.tim_kiem_giang_vien;
import java.util.*;
import codeoop.tim_kiem_giang_vien.GiangVien;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        GiangVien[] a = new GiangVien[n];
        for(int i = 0; i < n; i++){
            a[i] = new GiangVien();
            a[i].input(sc);
        }
        int queries = sc.nextInt();
        sc.nextLine();
        while(queries > 0){
            String sub = sc.nextLine();
            System.out.printf("DANH SACH GIANG VIEN THEO TU KHOA %s:\n", sub);
            for(int i = 0; i < a.length; i++){
                if(a[i].Search(sub) == true){
                    a[i].output();
                }
            }
            queries--;
        }
    }
}
