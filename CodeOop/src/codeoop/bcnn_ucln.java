/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;

public class bcnn_ucln{
    public static long ucln(long a, long b){
        if(b == 0) return a;
        else return ucln(b, a % b);
    }

    public static long bcnn(long a, long b){
        return (a * b) / ucln(a, b);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            long a = sc.nextLong();
            long b = sc.nextLong();
            System.out.print(bcnn(a, b) + " ");
            System.out.print(ucln(a, b) + " ");
            System.out.println();
            t--;
        }
    }
}
