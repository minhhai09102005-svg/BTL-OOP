/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
public class dc_email {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Map<String, Integer> mp = new HashMap<>();
        int t = sc.nextInt();
        sc.nextLine();
        while(t > 0){
            String str = sc.nextLine();
            String s = str.toLowerCase();
            String[] a = s.split("\\s+");
            String res = "";
            res += a[a.length - 1];
            for(int i = 0; i < a.length - 1; i++){
                res += a[i].charAt(0);
            }
            mp.put(res, mp.getOrDefault(res, 0) + 1);
            if(mp.get(res) > 1){
                System.out.println(res + mp.get(res));
            }
            else{
                System.out.println(res);
            }
            t--;
        }
    }   
}
