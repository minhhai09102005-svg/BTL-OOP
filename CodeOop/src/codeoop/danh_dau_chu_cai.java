/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class danh_dau_chu_cai {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Set<Character> se = new TreeSet<>();
        for(int i = 0; i < s.length(); i++){
            se.add(s.charAt(i));
        }
        System.out.println(se.size());
    }
}
