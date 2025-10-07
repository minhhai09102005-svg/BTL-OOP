/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.diemDanh;
import java.util.*;
public class document {
    private String authors;
    private Date date;
    public void addAuthor(String name){
        authors = name;
    }
    public void addDate(Date ngay){
        date = ngay;
    }
    public String getAuthors(){
        return authors;
    }
    public Date getDate(){
        return date;
    }
}
