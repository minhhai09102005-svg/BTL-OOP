package codeoop;
import java.util.*;

enum Color {
    RED, BLUE, GREEN, BLACK, WHITE, YELLOW, GRAY
}

class Vehicle {
    private String id;
    private String manu;
    private int year;
    private int cost;
    private Color color; 

    public void input(Scanner sc) {
        id = sc.next();
        sc.nextLine();
        manu = sc.nextLine();
        year = sc.nextInt();
        cost = sc.nextInt();
        sc.nextLine();
        String c = sc.nextLine().trim().toUpperCase();
        try {
            color = Color.valueOf(c);
        } catch (IllegalArgumentException e) {
            color = Color.GRAY; 
        }
    }

    public void output() {
        System.out.println(id + " " + manu + " " + year + " " + cost + " " + color);
    }
}

public class vehicle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Vehicle[] a = new Vehicle[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Vehicle();
            a[i].input(sc);
        }
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            a[i].output();
        }
    }
}
