package codeoop;

import java.util.*;
import java.io.*;
public class so_nt_lon_nhat_trong_file {
    public static boolean check(int n){
        if(n < 2) return false;
        if(n == 2) return true;
        if(n > 2 && n % 2 == 0) return false;
        for(int i = 3; i <= Math.sqrt(n); i+=2){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("DATA.txt");
        Scanner sc = new Scanner(f);
        Map<Integer, Integer> freq = new TreeMap<>();
        while(sc.hasNext()){
            int n = sc.nextInt();
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }
        int count = 0;
        Map<Integer, Integer> mp = new TreeMap<>(Collections.reverseOrder());
        mp.putAll(freq);
        for(Map.Entry<Integer, Integer> e : mp.entrySet()){
            if(check(e.getKey()) == true){
                System.out.println(e.getKey() + " " + e.getValue());
                count++;
            }
            if(count == 10){
                break;
            }
        }
    }
}
