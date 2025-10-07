/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class dt_cuc_gach {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            String s = sc.nextLine();
            String num = "";
            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                if("ABCabc".indexOf(c) != -1) num += "2";
                else if("DEFdef".indexOf(c) != -1) num += "3";
                else if("GHIghi".indexOf(c) != -1) num += "4";
                else if("JKLjkl".indexOf(c) != -1) num += "5";
                else if("MNOmno".indexOf(c) != -1) num += "6";
                else if("PQRSpqrs".indexOf(c) != -1) num += "7";
                else if("TUVtuv".indexOf(c) != -1) num += "8";
                else if("WXYZwxyz".indexOf(c) != -1) num += "9";
                // nếu ký tự không thuộc nhóm thì bỏ qua
            }
            
            // kiểm tra palindrome
            boolean ok = true;
            for(int i = 0, j = num.length()-1; i < j; i++, j--){
                if(num.charAt(i) != num.charAt(j)){
                    ok = false;
                    break;
                }
            }
            if(ok) System.out.println("YES");
            else System.out.println("NO");
            t--;
        }
    }
}
