package codeoop;
import java.util.*;
public class hieu_snl1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t > 0){
            String s1 = sc.next();
            String s2 = sc.next();
            while(s1.length() < s2.length()){
                s1 = '0' + s1;
            }
            while(s1.length() > s2.length()){
                s2 = '0' + s2;
            }
            if(s1.compareTo(s2) < 0){
                String temp = s1;
                s1 = s2;
                s2 = temp;
            }
            String res = "";
            int remember = 0;
            for(int i = s1.length() - 1; i >= 0; i--){
                int temp = (s1.charAt(i) - '0') - (s2.charAt(i) - '0') - remember;
                if(temp < 0){
                    temp += 10;
                    remember = 1;
                }
                else{
                    remember = 0;
                }
                res = temp + res;
            }
            System.out.println(res);
            t--;
        }
    }
}
