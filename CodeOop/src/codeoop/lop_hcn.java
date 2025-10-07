/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.lang.Math.*;

class Rectangle{
    private double width;
    private double height;
    private String color;
    public Rectangle(double width, double height, String color){
        this.color = color;
        this.height = height;
        this.width = width;  
    }
    public double findArea(){
        return width*height;
    }
    public double findPerimeter(){
        return (width + height) * 2;
    }
    public String chuanHoa(){
        String s = "";
        s += color.substring(0, 1).toUpperCase();
        s += color.substring(1).toLowerCase();
        return s;
    }
}

public class lop_hcn {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        double w = sc.nextDouble();
        double h = sc.nextDouble();
        String c = sc.next();

        // Kiểm tra hợp lệ
        if (w <= 0 || h <= 0) {
            System.out.println("INVALID");
            return;
        }

        Rectangle r = new Rectangle(w, h, c);
        System.out.printf("%.0f %.0f %s\n", r.findPerimeter(), r.findArea(), r.chuanHoa());
    }
}

