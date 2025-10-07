import java.util.*;

public class xau_doi_xung {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t-- > 0){
            String s = sc.next();
            int i = 0, j = s.length() - 1;
            int count = 0;

            while(i < j){
                if(s.charAt(i) != s.charAt(j)){
                    count++;
                }
                i++;
                j--;
            }

            if(count == 1){
                System.out.println("YES");
            } 
            else if(count == 0 && s.length() % 2 == 1){
                System.out.println("YES");
            } 
            else {
                System.out.println("NO");
            }
        }
    }
}
