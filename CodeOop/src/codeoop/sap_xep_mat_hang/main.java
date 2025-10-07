/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.sap_xep_mat_hang;
import java.util.*;
import codeoop.sap_xep_mat_hang.MatHang;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        MatHang[] a = new MatHang[n];
        for(int i = 0; i < n; i++){
            a[i] = new MatHang();
            a[i].input(sc);
        }
        Arrays.sort(a);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
