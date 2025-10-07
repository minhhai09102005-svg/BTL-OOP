/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;
public class loai_bo_so_nguyen {
    public static boolean check(String s){
        try{
            Integer.valueOf(s);
            return false;
        }
        catch(NumberFormatException e){
            return true;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("DATA.in");
        Scanner sc = new Scanner(f);
        ArrayList<String> a = new ArrayList<>();
        while(sc.hasNext()){
            String s = sc.next();
            if(check(s) == true){
                a.add(s);
            }
        }
        Collections.sort(a);
        for(int i = 0; i < a.size(); i++){
            System.out.print(a.get(i) + " ");
        }
    }
}
