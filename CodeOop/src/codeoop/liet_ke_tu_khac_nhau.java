/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;
public class liet_ke_tu_khac_nhau {
    public static void main(String[] args) throws FileNotFoundException{
       File f = new File("VANBAN.in");
       Scanner sc = new Scanner(f);
       Set<String> se = new TreeSet<>();
       while(sc.hasNext()){
           String word = sc.next();
           se.add(word.toLowerCase());
       }
       for(String s : se){
           System.out.println(s);
       }
    }
}
