/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.dsach_svien_2;
import java.util.*;
import codeoop.dsach_svien_2.SinhVien;
public class NewClass {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int n = sc.nextInt();
       sc.nextLine(); // Thêm dòng này để loại bỏ ký tự xuống dòng sau khi đọc 'n'
       
       SinhVien[] a = new SinhVien[n];
       for(int i = 0; i < n; i++){
            a[i] = new SinhVien();
            a[i].input(sc);
       }
       for(int i = 0; i < n; i++){
            a[i].output();
       }
    }
}
