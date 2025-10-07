package codeoop.sap_xep_mat_hang;
import java.util.*;

public class MatHang implements Comparable<MatHang> {
    private static int code = 1;
    private int ma;
    private String name;
    private String nhom;
    private double in;      // giá mua
    private double out;     // giá bán
    private double profit;  // lợi nhuận

    @Override
    public int compareTo(MatHang other) {
        // Nếu muốn giảm dần theo profit
        return Double.compare(other.profit, this.profit);
        // Nếu muốn tăng dần thì đổi lại:
        // return Double.compare(this.profit, other.profit);
    }

    public void input(Scanner sc) {
        sc.nextLine();
        ma = code++;
        name = sc.nextLine();
        nhom = sc.nextLine();
        in = sc.nextDouble();
        out = sc.nextDouble();
        profit = out - in;
    }

    public void output() {
        System.out.printf("%d %s %s %.2f%n", ma, name, nhom, profit);
    }
    
}
