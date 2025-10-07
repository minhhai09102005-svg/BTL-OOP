/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
class MatHang{
    private String name;
    private static int counter = 0;
    private int code;
    private String unit;
    private int inprice;
    private int outprice;
    public void input(Scanner sc) {
    counter++;
    code = counter;
    sc.nextLine();         // bỏ ký tự xuống dòng còn lại từ lần nhập trước
    name = sc.nextLine();  // đọc tên mặt hàng (có thể có khoảng trắng)
    unit = sc.next();
    inprice = sc.nextInt();
    outprice = sc.nextInt();
    }
    public void output(){
        System.out.printf("MH" + "%03d %s %s %d %d %d", code, name, unit, inprice, outprice, outprice - inprice);
        System.out.println();
    }
    public static Comparator<MatHang> cmp = new Comparator<MatHang>(){
        public int compare(MatHang m1, MatHang m2){
            int profit1 = m1.outprice - m1.inprice;
            int profit2 = m2.outprice - m2.inprice;
            if(profit1 != profit2){
                return profit2 - profit1;
            }
            else{
                return m1.code - m2.code;
            }
        }
    };
}


public class danh_sach_mat_hang {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        MatHang[] a = new MatHang[n];
        for(int i = 0; i < n; i++){
            a[i] = new MatHang();
            a[i].input(sc);
        }
        Arrays.sort(a, MatHang.cmp);
        for(int i = 0; i < n; i++){
            a[i].output();
        }
    }
}
