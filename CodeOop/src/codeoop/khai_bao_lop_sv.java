/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.lang.Math.*;

class SinhVien{
    private String name;
    private String lop;
    private String dob;
    private float gpa;
    private void chuanHoa() {
        String[] a = dob.split("/");
        
        // Logic hoán đổi ngày và tháng
        // Kiểm tra nếu ngày (a[0]) lớn hơn 12, thì có khả năng nó là tháng và cần hoán đổi
        if (Integer.valueOf(a[0]) > 12) {
            String temp = a[0];
            a[0] = a[1];
            a[1] = temp;
        }
        
        // Thêm số 0 ở đầu nếu ngày hoặc tháng nhỏ hơn 10
        for (int i = 0; i < 2; i++) { // Chỉ lặp qua ngày và tháng (a[0] và a[1])
            if (a[i].length() < 2) {
                a[i] = "0" + a[i];  
            }
        }
        dob = a[0] + "/" + a[1] + "/" + a[2]; // Gán lại giá trị đã chuẩn hóa
    }
    public SinhVien(){
        
    }
    public void input(){
        Scanner sc = new Scanner(System.in);
        name = sc.nextLine();
        lop = sc.next();
        dob = sc.next();
        gpa = sc.nextFloat();
        chuanHoa();
    }
    public void output(){
        System.out.print("B20DCCN001 ");
        System.out.printf("%s %s %s %.2f", name, lop, dob, gpa);
    }
}

public class khai_bao_lop_sv {
    public static void main(String[] args){
        SinhVien s = new SinhVien();
        s.input();
        s.output();
    }
}
