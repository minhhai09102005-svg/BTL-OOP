/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.tim_kiem_giang_vien;
import java.util.*;
public class GiangVien {
    private static int ma = 1;
    private int code;
    private String name;
    private String subject;
    private String tat = "";
    
    public void input(Scanner sc){
        code = ma;
        ma++;
        name = sc.nextLine();
        subject = sc.nextLine();
        String[] a = subject.split("\\s+");
        for(int i = 0; i < a.length; i++){
            tat += Character.toUpperCase(a[i].charAt(0));
        }
    }
    
    public void output(){
        System.out.printf("GV%02d %s %s\n", code, name, tat);
    }
    
    public boolean Search(String sub){
        if(name.toLowerCase().contains(sub.toLowerCase())){
            return true;
        }
        else return false;
    }
}
