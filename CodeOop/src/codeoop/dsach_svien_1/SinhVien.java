/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.dsach_svien_1;
import java.util.*;

public class SinhVien{
    private static int code = 1;
    private int ma;
    private String name;
    private String lop;
    private String dob;
    private double gpa;
    private void chuanHoa(){
        String[] a = dob.split("/");
        // Logic chuẩn hóa ngày tháng năm sinh
        // Kiểm tra tháng, năm.
        // Cần đảm bảo rằng các phần tử của mảng a là số để tránh lỗi NumberFormatException
        if (a.length == 3) {
            try {
                int day = Integer.parseInt(a[0]);
                int month = Integer.parseInt(a[1]);
                int year = Integer.parseInt(a[2]);
                
                // Chuẩn hóa ngày
                String dayStr = (day < 10) ? "0" + day : String.valueOf(day);
                // Chuẩn hóa tháng
                String monthStr = (month < 10) ? "0" + month : String.valueOf(month);

                dob = dayStr + "/" + monthStr + "/" + year;
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu không thể chuyển đổi chuỗi thành số
            }
        }
    }
    public void input(Scanner sc){
        ma = code;
        code++;
        name = sc.nextLine();
        lop = sc.next();
        dob = sc.next();
        gpa = sc.nextDouble();
        // Sau khi đọc gpa, bạn cần tiêu thụ ký tự xuống dòng còn sót lại
        sc.nextLine(); // Thêm dòng này vào
        chuanHoa();
    }
    public void output(){
        System.out.printf("B20DCCN%03d %s %s %s %.2f\n", ma, name, lop, dob, gpa);
    }
}
