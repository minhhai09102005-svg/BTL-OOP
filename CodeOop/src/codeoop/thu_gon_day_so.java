/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class thu_gon_day_so {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n= sc.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = sc.nextInt();
        }
        Stack<Integer> st = new Stack<>();
        for(int i = 0; i < n; i++){
            if(!st.empty() && (a[i] + st.peek()) % 2 == 0){
                st.pop();
            }
            else{
                st.push(a[i]);
            }
        }
        System.out.println(st.size());
    }
}
