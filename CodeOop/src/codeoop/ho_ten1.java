/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;

import java.util.*;

public class ho_ten1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        while(t > 0){
            String s = sc.nextLine();
            Scanner scan = new Scanner(s);
            ArrayList<String> name = new ArrayList<>();
            while(scan.hasNext()){
                name.add(scan.next());
            }
            for(int i = 0; i < name.size(); i++){
               name.set(i, name.get(i).toLowerCase());
            }
            for(int i = 0; i < name.size(); i++){
               String cur = name.get(i);
               String newname = cur.substring(0, 1).toUpperCase() + cur.substring(1);
               name.set(i, newname);
            }
            for(int i = 0; i < name.size(); i++){
                System.out.print(name.get(i) + " ");
            }
            System.out.println();
            t--;
        }
    }
}
