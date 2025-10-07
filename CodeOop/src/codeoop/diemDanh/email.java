/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeoop.diemDanh;
import java.util.*;
import codeoop.diemDanh.document;
public class email extends document{
    private String subject;
    private String to;
    public void addSubject(String subject){
        this.subject = subject;
    }
    public void addTo(String to){
        this.to = to;
    }
    public String getSubject(){
        return subject;
    }
    public String getTo(){
        return to;
    }
}
