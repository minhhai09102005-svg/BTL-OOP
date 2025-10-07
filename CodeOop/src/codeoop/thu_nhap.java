/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;

class GiaoVien {
    private String code;
    private String name;
    private long base;
    private long bonus;
    private long factor;

    public GiaoVien(String code, String name, long base) {
        this.code = code;
        this.name = name;
        this.base = base;
    }

    public void process() {
        String s = code.substring(0, 2);
        if (s.equals("HT")) {
            bonus = 2000000;
        } else if (s.equals("HP")) {
            bonus = 900000;
        } else {
            bonus = 500000;
        }
        String f = code.substring(2);
        factor = Long.valueOf(f);
    }

    public void output() {
        process();
        System.out.print(code + " " + name + " " + factor + " " + bonus + " " + ((base * factor) + bonus));
    }
}

public class thu_nhap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String c = sc.next();
        sc.nextLine();
        String n = sc.nextLine();
        long b  = sc.nextLong();
        GiaoVien g = new GiaoVien(c, n, b);
        g.output();
    }
}
