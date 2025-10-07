/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import static java.lang.Math.*;

class Point{
    private double x;
    private double y;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public static double distance(Point p1, Point p2){
        return sqrt(pow(p2.x - p1.x, 2) + pow(p2.y -p1.y, 2));
    }
}

public class khai_bao_lop_point {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            double x1 = sc.nextInt();
            double y1 = sc.nextInt();
            Point p1 = new Point(x1, y1);
            double x2 = sc.nextInt();
            double y2 = sc.nextInt();
            Point p2 = new Point(x2, y2);
            System.out.printf("%.4f\n", Point.distance(p1, p2));
            t--;
        }
    }
}
