package codeoop;
import java.util.*;

class Rect {
    private double width;
    private double height;
    private String color;

    public Rect(double width, double height, String color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void out() {
        if (width <= 0 || height <= 0) {
            System.out.println("INVALID");
        } else {
            String s = color.substring(0,1).toUpperCase() + color.substring(1).toLowerCase();
            System.out.printf("%.0f %.0f %s\n", (width + height) * 2, width * height, s);
        }
    }
}

public class khai_bao_lop_hcn {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        String str = sc.next();
        Rect r = new Rect(a, b, str);
        r.out();
    }
}
