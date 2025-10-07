import java.util.*;

public class day_con_tong_le {
    static Scanner sc = new Scanner(System.in);
    static int n;
    static int[] a;
    static ArrayList<Integer> temp = new ArrayList<>();

    static void out() {
        for (int x : temp) System.out.print(x + " ");
        System.out.println();
    }

    static void Try(int pos, int sum) {
        if (sum % 2 == 1) out(); // tổng lẻ → in ngay
        for (int i = pos; i < n; i++) {
            temp.add(a[i]);
            Try(i + 1, sum + a[i]);
            temp.remove(temp.size() - 1);
        }
    }

    public static void main(String[] args) {
        int t = sc.nextInt();
        while (t-- > 0) {
            n = sc.nextInt();
            a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            Arrays.sort(a);                 // sắp xếp tăng
            // đảo ngược thành giảm
            for (int i = 0; i < n/2; i++) {
                int tmp = a[i];
                a[i] = a[n - 1 - i];
                a[n - 1 - i] = tmp;
            }
            Try(0, 0);
        }
    }
}
