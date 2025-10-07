/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class rutgon_xau {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        Stack<Character> st = new Stack<>();
        for(int i = s.length() - 1; i >= 0; i--){
            if(!st.empty() && s.charAt(i) == st.peek()){
                st.pop();
            }
            else{
                st.push(s.charAt(i));
            }
        }
        if(st.empty()){
            System.out.println("Empty String");
        }
        else{
            while(!st.empty()){
                System.out.print(st.peek());
                st.pop();
            }
        System.out.println();
        }
    }
}
