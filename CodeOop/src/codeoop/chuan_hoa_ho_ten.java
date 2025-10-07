/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
public class chuan_hoa_ho_ten {
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("DATA.in");
        Scanner sc = new Scanner(f);
        int ok = 1;
        while(ok == 1){
            String s = sc.nextLine();
            if(s.compareTo("END") == 0){
                break;
            }
            else{
                String[] a = s.trim().split("\\s+");
                if(a.length > 0){
                    for (int i = 0; i < a.length; i++) {
                        if (!a[i].isEmpty()) { // bỏ qua chuỗi rỗng
                            a[i] = a[i].toLowerCase();
                            a[i] = a[i].substring(0, 1).toUpperCase() + a[i].substring(1);
                        }
                    }
                    for (String word : a) {
                        if (!word.isEmpty()) {
                            System.out.print(word + " ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}