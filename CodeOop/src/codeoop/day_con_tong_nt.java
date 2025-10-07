/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.lang.Math;
public class day_con_tong_nt {
    static Scanner sc = new Scanner(System.in);
    static int n;
    static ArrayList<Integer> v = new ArrayList<>();
    static ArrayList<Integer> temp = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    
    public static void out(ArrayList<Integer> a){
        for(int i = 0; i < a.size(); i++){
            System.out.printf("%d ", a.get(i));
        }
        System.out.println();
    }
    
    public static boolean check(int a){
        if(a < 2) return false;
        if(a == 2) return true;
        if(a > 2 && a % 2 == 0) return false;
        for(int i = 3; i <= Math.sqrt(a); i += 2){
            if(a % i == 0){
                return false;
            }
        }
        return true;
    }
    
    public static void Try(int prev, int sum) {
        if(check(sum) == true){
            res.add(new ArrayList<>(temp));
        }
        for(int i = prev; i < n; i++){
            temp.add(v.get(i));
            Try(i + 1, sum + v.get(i));
            temp.remove(temp.size() - 1);
        }
    }
    
    public static void main(String[] args) {
        int t = sc.nextInt();
        while(t > 0){
            v.clear(); res.clear(); temp.clear();
            n = sc.nextInt();
            for(int i = 0; i < n; i++){
                int x = sc.nextInt();
                v.add(x);
            }
            Collections.sort(v, Collections.reverseOrder());
            Try(0, 0);
            res.sort((a, b) -> {
               int m = Math.min(a.size(), b.size());
               for(int i = 0; i < m; i++){
                   int cmp = a.get(i).compareTo(b.get(i));
                   if(cmp != 0) return cmp;
               }
               return Integer.compare(a.size(), b.size());
            });
            for(ArrayList<Integer> x : res){
                out(x);
            }
                t--;
        }
    }
}
