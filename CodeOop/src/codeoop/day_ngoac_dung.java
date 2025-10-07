/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class day_ngoac_dung {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            String s = sc.next();
            Stack<Character> st = new Stack<>();
            for(int i = 0; i < s.length(); i++){
                if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                    st.add(s.charAt(i));
                }
                else{
                    if (!st.isEmpty() && (
                            (s.charAt(i) == ')' && st.peek() == '(') ||
                            (s.charAt(i) == ']' && st.peek() == '[') ||
                            (s.charAt(i) == '}' && st.peek() == '{')
                        )) {
                        st.pop();
                    } else {
                        st.add(s.charAt(i));
                    }
                }
            }
            if(st.empty() == false){
                System.out.println("NO");
            }
            else{
                System.out.println("YES");
            }
            t--;
        }
    }
}
