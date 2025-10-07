/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.diemDanh;
import java.util.*;
import codeoop.diemDanh.document;
public class book extends document{
    private String title;
    public void addTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
}
