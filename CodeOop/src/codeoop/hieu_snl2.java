/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class hieu_snl2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s1 = sc.next();
        String s2 = sc.next();
        while(s1.length() < s2.length()){
            s1 = '0' + s1;
        }
        while(s2.length() < s1.length()){
            s2 = '0' + s2;
        }
        char minus = '+';
        if(s1.compareTo(s2) < 0){
            String temp = s1;
            s1 = s2;
            s2 = temp;
            minus = '-';
        }
        String res = "";
        int remember = 0;
        for(int i = s1.length() - 1; i >= 0; i--){
            int temp = (s1.charAt(i) - '0') - (s2.charAt(i) - '0') - remember;
            if(temp < 0){
                temp += 10;
                remember = 1;
            }
            else{
                remember = 0;
            }
            res = temp + res;
        }
        int index = 0;
        while(res.charAt(index) == '0' && index < res.length() - 1){
            index++;
        }
        if(minus == '-'){
            System.out.print(minus);
        }
        System.out.println(res.substring(index));
    }
}
