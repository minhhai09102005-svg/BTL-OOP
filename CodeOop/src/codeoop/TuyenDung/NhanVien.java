/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.TuyenDung;
import java.util.*;
public class NhanVien implements Comparable<NhanVien>{
    private static int ma = 1;
    private int code;
    private String name;
    private String lythuyet;
    private String thuchanh;
    private double lt;
    private double th;
    private double tb;
    private String status;
    
    public double chuanHoa(String s){
        String d = "";
        if(s.equals("10") == true){
            d = "10";
        }
        else{
            if(s.length() > 1){
                if(s.contains(".") == false){
                    d += s.charAt(0);
                    d += ".";
                    for(int i = 1; i < s.length(); i++){
                        d += s.charAt(i);
                    }
                }
                else{
                    d = s;
                }
            }
            else{
                d = s;
            }
        }
        return Double.valueOf(d);
    }
    
    @Override
    public int compareTo(NhanVien other){
        return Double.compare(this.tb, other.tb) * -1;
    }
    
    public void input(Scanner sc){
        code = ma;
        ma++;
        name = sc.nextLine();
        lythuyet = sc.nextLine();
        thuchanh = sc.nextLine();
        lt = chuanHoa(lythuyet);
        th = chuanHoa(thuchanh);
        tb = (lt + th) / 2;
        if(tb > 9.5){
            status = "XUAT SAC";
        }
        else if(tb >= 8){
            status = "DAT";
        }
        else if(tb >= 5){
            status = "CAN NHAC";          
        }
        else{
            status = "TRUOT";
        }
    }
    
    public void output(){
        System.out.printf("TS%02d %s %.2f %s\n", code, name, tb, status);
    }
}
