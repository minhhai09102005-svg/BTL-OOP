/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;

public class email {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        HashMap<String, Integer> mp = new HashMap<>();
        while(t > 0){
            String name = sc.nextLine();
            Scanner ss = new Scanner(name);
            ArrayList<String> v = new ArrayList<>();
            while(ss.hasNext()){
                v.add(ss.next());
            }
            for(int i = 0; i < v.size(); i++){
                v.set(i, v.get(i).toLowerCase());
            }
            String s = "";
            s += v.get(v.size() - 1);
            for(int i = 0; i < v.size() - 1; i++){
                s += v.get(i).charAt(0);
            }
            if(mp.containsKey(s)){
                mp.put(s, mp.get(s) + 1);
            }
            else{
                mp.put(s, 1);
            }
            if(mp.get(s) == 1){
                System.out.print(s);
            }
            else{
                System.out.print(s);
                System.out.print(mp.get(s));
            }
            System.out.println("@ptit.edu.vn");
            t--;
        }
    }
}
