/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.thu_khoa;
import java.util.*;
import codeoop.thu_khoa.ThiSinh;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        ThiSinh[] a = new ThiSinh[n];
        for(int i = 0; i < n; i++){
            a[i] = new ThiSinh();
            a[i].input(sc);
        }
        Arrays.sort(a);
        double m = a[0].tong;
        int i = 0;
        while(a[i].tong == m){
            a[i].out();
            i++;
        }
    }
}
