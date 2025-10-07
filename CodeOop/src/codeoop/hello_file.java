/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop;
import java.util.*;
import java.io.*;
public class hello_file {
    public static void main(String[] args) throws FileNotFoundException{
        File f = new File("Hello.txt");
        Scanner sc = new Scanner(f);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            System.out.println(line);
        }
    }
}
