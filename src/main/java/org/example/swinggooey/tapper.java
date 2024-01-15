package org.example.swinggooey;

import javax.swing.*;
public class tapper {
    JFrame f;
    tapper(){
        f=new JFrame();
        JTextArea ta=new JTextArea(400,400);
        JPanel p1=new JPanel();
        p1.add(ta);
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        JPanel p5=new JPanel();
        JTabbedPane tp=new JTabbedPane();
        tp.setBounds(50,50,200,200);
        tp.add("Teacher",p1);
        tp.add("Student",p2);
        tp.add("Class",p3);
        tp.add("Teacher schedule",p4);
        tp.add("Student schedule",p5);
        f.add(tp);
        f.setSize(500,600);
        f.setLayout(null);
        f.setVisible(true);
    }
    //public static void main(String[] args) {
     //   new tapper();
    //}
}