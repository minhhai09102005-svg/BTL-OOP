/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;
public class tinh_tong {
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("DATA.in");
        Scanner sc = new Scanner(f);
        ArrayList<String> v = new ArrayList<>();
        while(sc.hasNext()){
            String word = sc.next();
            v.add(word); 
        }
        long res = 0;
        for(int i = 0; i < v.size(); i++){
            try{
                int x = Integer.valueOf(v.get(i));
                res += x;
            }
            catch(Exception e){
                
            }
        }
        System.out.println(res);
    }
}
