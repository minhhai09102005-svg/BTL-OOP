package codeoop;
import java.util.*;

public class ktra_fibo {
    static long[] a = new long[95];  // dùng long thay vì int

    public static void fibo() {
        a[0] = 0;
        a[1] = 1;
        for (int i = 2; i < 95; i++) {
            a[i] = a[i - 1] + a[i - 2];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        fibo();
        int t = sc.nextInt();
        while (t > 0) {
            long n = sc.nextLong();  // đọc vào long
            int index = Arrays.binarySearch(a, n);
            if (index >= 0) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
            t--;
        }
    }
}
