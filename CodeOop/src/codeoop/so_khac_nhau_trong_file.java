/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;

public class so_khac_nhau_trong_file {
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("DATA.in");
        Scanner sc = new Scanner(f);
        Map<Integer, Integer> mp = new TreeMap<>();
        while(sc.hasNext()){
            int n = sc.nextInt();
            mp.put(n, mp.getOrDefault(n, 0) + 1);
        }
        for(Map.Entry<Integer, Integer> entry : mp.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
