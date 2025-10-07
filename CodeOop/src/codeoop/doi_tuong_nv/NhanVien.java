/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.doi_tuong_nv;
import java.util.*;
public class NhanVien {
    private static int ma = 1;
    private int code;
    private String name;
    private String sex;
    private String dob;
    private String address;
    private String mst;
    private String contract;
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
