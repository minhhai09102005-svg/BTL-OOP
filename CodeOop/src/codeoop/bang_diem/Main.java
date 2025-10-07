/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.bang_diem;

import java.util.*;
import codeoop.bang_diem.HocSinh;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();                 // Số lượng học sinh
        List<HocSinh> list = new ArrayList<>();

        // Nhập dữ liệu
        for (int i = 0; i < n; i++) {
            HocSinh hs = new HocSinh();
            hs.input(sc);
            list.add(hs);
        }

        // Sắp xếp theo GPA giảm dần (Comparable đã làm sẵn)
        Collections.sort(list);

        // In ra danh sách
        for (HocSinh hs : list) {
            hs.output();
            System.out.println();
        }
    }
}
