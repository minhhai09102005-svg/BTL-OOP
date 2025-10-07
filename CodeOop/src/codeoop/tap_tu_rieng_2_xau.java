package codeoop;
import java.util.*;

public class tap_tu_rieng_2_xau {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine();
        while(t > 0){
            String s1 = sc.nextLine();
            String s2 = sc.nextLine();

            Scanner ss = new Scanner(s1);
            ArrayList<String> v1 = new ArrayList<>();
            while(ss.hasNext()){
                v1.add(ss.next());
            }

            Scanner xx = new Scanner(s2);
            ArrayList<String> v2 = new ArrayList<>();
            while(xx.hasNext()){
                v2.add(xx.next());
            }

            Collections.sort(v1);
            Collections.sort(v2);

            HashMap<String, Integer> mp = new HashMap<>();
            for(String x : v1){
                if(!v2.contains(x) && !mp.containsKey(x)){ 
                    System.out.print(x + " ");
                    mp.put(x, 1);
                }
            }
            System.out.println();
            t--;
        }
    }
}
