/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;

public class ma_tran_np {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] a = new int[n][3];
        int res = 0;
        for(int i = 0; i < n; i++){
            int count = 0;
            for(int j = 0; j < 3; j++){
                a[i][j] = sc.nextInt();
                if(a[i][j] == 1){
                    count++;
                }
            }
            if(count > 3 - count){
                res++;
            }
        }
        System.out.println(res);
    }
}
