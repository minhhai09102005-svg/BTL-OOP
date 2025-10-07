/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.sap_xep_nv;
import java.util.*;

public class NhanVien implements Comparable<NhanVien>{
    private static int ma = 1;
    private int code;
    private String name;
    private String sex;
    private String dob;
    private String address;
    private String mst;
    private String contract;
    
    @Override
    public int compareTo(NhanVien other){
        String[] t1 = this.dob.split("/");
        String[] t2 = other.dob.split("/");

        int d1 = Integer.parseInt(t1[0]);
        int m1 = Integer.parseInt(t1[1]);
        int y1 = Integer.parseInt(t1[2]);

        int d2 = Integer.parseInt(t2[0]);
        int m2 = Integer.parseInt(t2[1]);
        int y2 = Integer.parseInt(t2[2]);
        if(y1 != y2){
            return y1 - y2;
        }
        else if(m1 != m2){
            return m1 - m2;
        }
        else{
            return d1 - d2;
        }
    }
    
    public void input(Scanner sc){
        code = ma;
        ma++;
        name = sc.nextLine();
        sex = sc.nextLine();
        dob = sc.nextLine();
        address = sc.nextLine();
        mst = sc.nextLine();
        contract = sc.nextLine();
    }
    
    public void output(){
        System.out.printf("%05d %s %s %s %s %s %s\n", code, name, sex, dob, address, mst, contract);
    }
    
}
