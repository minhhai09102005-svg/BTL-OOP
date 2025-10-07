/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.lang.Math.*;
class SoPhuc{
    private int a;
    private int b;
    public SoPhuc(int a, int b){
        this.a = a;
        this.b = b;
    }
    public String res1(SoPhuc n2){
        String res = "";
        SoPhuc sum = new SoPhuc(a + n2.a, b + n2.b);
        res += (sum.a * a - sum.b * b) + " + " + (sum.a * b + sum.b * a) + "i"; 
        return res;
    }
    public String res2(SoPhuc n2){
        String res = "";
        SoPhuc sum = new SoPhuc(a + n2.a, b + n2.b);
        res += ((int)Math.pow(sum.a, 2) - (int)Math.pow(sum.b, 2)) + " + " + (2 * sum.a * sum.b) + "i";
        return res;
    }
}

public class so_phuc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            int a = sc.nextInt();
            int b = sc.nextInt();
            SoPhuc s1 = new SoPhuc(a, b);
            int c = sc.nextInt();
            int d = sc.nextInt();
            SoPhuc s2 = new SoPhuc(c, d);
            System.out.print(s1.res1(s2) + ", ");
            System.out.println(s1.res2(s2));
            t--;
        }
    }
}
