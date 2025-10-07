/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.dsach_svien_3;
import java.util.*;

public class SinhVien implements Comparable<SinhVien>{
    private static int code = 1;
    private int ma;
    private String name;
    private String lop;
    private String dob;
    private double gpa;
    
    private void chuanHoa(){
        String[] a = dob.split("/");
        if (a.length == 3) {
            try {
                int day = Integer.parseInt(a[0]);
                int month = Integer.parseInt(a[1]);
                int year = Integer.parseInt(a[2]);
                
                String dayStr = (day < 10) ? "0" + day : String.valueOf(day);
                String monthStr = (month < 10) ? "0" + month : String.valueOf(month);
                dob = dayStr + "/" + monthStr + "/" + year;
            } catch (NumberFormatException e) {
                // ignore
            }
        }
    }

    private void chuanHoa2(){
        String[] a = name.trim().split("\\s+");
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < a.length; i++){
            res.append(Character.toUpperCase(a[i].charAt(0)))
               .append(a[i].substring(1).toLowerCase())
               .append(" ");
        }
        name = res.toString().trim();
    }
    
    @Override
    public int compareTo(SinhVien other){
        return (Double.compare(this.gpa, other.gpa)) * -1;
    }

    public void input(Scanner sc){
        ma = code++;
        name = sc.nextLine();
        lop = sc.next();
        dob = sc.next();
        gpa = sc.nextDouble();
        sc.nextLine(); // bỏ ký tự xuống dòng
        chuanHoa();
        chuanHoa2();
    }

    public void output(){
        System.out.printf("B20DCCN%03d %s %s %s %.2f\n", ma, name.trim(), lop, dob, gpa);
    }
}


