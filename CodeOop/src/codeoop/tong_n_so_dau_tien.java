/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class tong_n_so_dau_tien {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t= sc.nextInt();
        while(t > 0){
            int n = sc.nextInt();
            System.out.println((n * (n + 1)) / 2);
            t--;
        }
    }
}
