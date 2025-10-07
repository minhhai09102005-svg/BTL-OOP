package codeoop;
import java.util.*;
class Student {
    private String Code;
    private String fullname;
    private boolean gender;
    private double mark;
    
    public Student(String Code, String fullname, boolean gender, double mark) {
        this.Code = Code;
        this.fullname = fullname;
        this.gender = gender;
        this.mark = mark;
    }
    
    public String getCode() {
        return Code;
    }
    
    void setCode(String p_Code) {
        Code = p_Code;
    }
    
    void setMark(double p_mark) {
        mark = p_mark;
    }
    void toUpper_name() {
        fullname = fullname.toUpperCase();
    }
    
    void output() {
        System.out.printf("CODE: %s\n", Code);
        System.out.printf("fullname: %s\n", fullname);
        System.out.printf("gender: %b\n", gender);
        System.out.printf("CODE: %f\n", mark);
    }
}


public class diem_danh {
    public static Student[] ds = new Student[10];
    public static Student a;
    public static void solve() {
        Scanner sc = new Scanner(System.in);
            int TC = sc.nextInt();
            switch(TC) {
                case 1:
                    sc.nextLine();
                    String code = sc.nextLine();
                    String fullName = sc.nextLine();  
                    boolean gen = false;
                    double  mark = sc.nextDouble();
                    a = new Student(code, fullName, gen, mark);
                    break;
                case 2:
                    
                    break;
                case 3:
                    a.toUpper_name();
                    break;
                case 4:
                    a.setMark(sc.nextDouble());
                    break;
                case 0:
                    break;
            }
    }
    public static void main(String arg[]) {
        while(true) {
            solve();
        }
    }
}  