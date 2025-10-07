/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.tong_da_thuc;
import java.util.*;
public class DaThuc {
    private String dt;
    
    public DaThuc(String dt){
        this.dt = dt;
    }
    
    public String KetQua(DaThuc other){
        Map<String, Integer> mp1 = new TreeMap<String, Integer>((s1, s2) -> {
           int n1 = Integer.parseInt(s1.substring(2)); // bỏ "x^" -> lấy số mũ
           int n2 = Integer.parseInt(s2.substring(2));
           return Integer.compare(n2, n1); // giảm dần
       });

        String dt1 = this.dt.replace('+', ' ');
        String[] a = dt1.split("\\s+");
        for(int i = 0; i < a.length; i++){
            if(a[i].length() < 5){
                a[i] = "1*" + a[i];
            }
            String[] s = a[i].split("\\*");
            mp1.put(s[1], mp1.getOrDefault(s[1], 0) + Integer.valueOf(s[0]));
        }
        String res = "";
        for(Map.Entry<String, Integer> entry : mp1.entrySet()){
            res += entry.getValue() + "*" + entry.getKey() + " ";
        }
        
        Map<String, Integer> mp2 = new TreeMap<String, Integer>((s1, s2) -> {
            int n1 = Integer.parseInt(s1.substring(2)); // bỏ "x^" -> lấy số mũ
            int n2 = Integer.parseInt(s2.substring(2));
            return Integer.compare(n2, n1); // giảm dần
        });

        String dt2 = other.dt.replace('+', ' ');
        String[] b = dt2.split("\\s+");
        for(int i = 0; i < b.length; i++){
            if(b[i].length() < 5){
                b[i] = "1*" + b[i];
            }
            String[] s = b[i].split("\\*");
            mp2.put(s[1], mp2.getOrDefault(s[1], 0) + Integer.valueOf(s[0]));
        }
        for(Map.Entry<String, Integer> entry : mp2.entrySet()){
            res += entry.getValue() + "*" + entry.getKey() + " ";
        }
        
        Map<String, Integer> mp3 = new TreeMap<String, Integer>((s1, s2) -> {
            int n1 = Integer.parseInt(s1.substring(2)); // bỏ "x^" -> lấy số mũ
            int n2 = Integer.parseInt(s2.substring(2));
            return Integer.compare(n2, n1); // giảm dần
        });

        String[] c = res.split("\\s+");
        for(int i = 0; i < c.length; i++){
            if(c[i].length() < 5){
                c[i] = "1*" + c[i];
            }
            String[] s = c[i].split("\\*");
            mp3.put(s[1], mp3.getOrDefault(s[1], 0) + Integer.valueOf(s[0]));
        }
        String kq = "";
        for(Map.Entry<String, Integer> entry : mp3.entrySet()){
            if(entry.getValue() > 0){
                kq += entry.getValue() + "*" + entry.getKey() + " + ";
            }
        }
        if (kq.length() >= 3) {
            kq = kq.substring(0, kq.length() - 3);
        }
        return kq;
    }
    
        public DaThuc cong(DaThuc other){
            String kq = this.KetQua(other);
            return new DaThuc(kq);
        }
        
        @Override
        public String toString(){
            return this.dt;
        }
}
