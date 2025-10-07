package codeoop.antique_shop;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Items> list = new ArrayList<>();
        while (true) {
            sc.nextLine();
            System.out.println("1. Nhap thong tin co vat moi");
            System.out.println("2. Hien thi danh sach co vat");
            System.out.println("3. Tim kiem theo ma");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim kiem theo ngay");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int choice = Integer.valueOf(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    Items it = new Items();
                    System.out.print("Nhap thong tin co vat: ");
                    it.input(sc);        
                    list.add(it);
                    System.out.println("=> Da them co vat vao danh sach");
                }
                case 2 -> {
                    System.out.println("\n--- Danh sach co vat ---");
                    for (Items i : list) {
                        i.output();
                    }
                }
                case 3 -> {
                    System.out.print("Nhap ma can tim: ");
                    int m = Integer.parseInt(sc.nextLine());
                    boolean found = false;

                    for (Items i : list) {
                        if (i.SearchByCode(m)) { 
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("NOT FOUND");
                    }
                }
                case 4 -> {
                    System.out.print("Nhap ten can tim: ");
                    String nameInput = sc.nextLine();
                    boolean found = false;

                    for (Items i : list) {
                        if (i.SearchByName(nameInput)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("NOT FOUND");
                    }

                }
                case 5 -> {
                    System.out.print("Nhap ngay can tim (dd/mm/yyyy): ");
                    String dateInput = sc.nextLine();
                    boolean found = false;

                    for (Items i : list) {
                        if (i.SearchByDate(dateInput)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("NOT FOUND");
                    }
                }
                case 0 -> {
                    System.out.println("Thoat chuong trinh...");
                    return;
                }
            }
        }
    }
}
