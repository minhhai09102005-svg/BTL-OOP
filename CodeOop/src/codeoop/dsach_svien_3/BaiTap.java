/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.dsach_svien_3;
import java.util.*;
import codeoop.dsach_svien_3.SinhVien;
public class BaiTap {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        SinhVien[] a = new SinhVien[n];
        for(int i = 0; i < n; i++){
            a[i] = new SinhVien();
            a[i].input(sc);
        }
        Arrays.sort(a);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
