package org.example;

import javax.swing.*;

public class tabber {
        JFrame f;
        tabber(){
            f=new JFrame();
//            JTextArea ta=new JTextArea(400,400);
//            JPanel p1=new teacherpanel();
//            p1.add(ta);
            JPanel p2=new JPanel();
            JPanel p3=new JPanel();
            JPanel p4=new JPanel();
            JPanel p5=new JPanel();
            JTabbedPane tp=new JTabbedPane();
            tp.setBounds(50,50,400,450);
            //tp.add("Teacher",new teacherpanel());
            tp.add("Student",p2);
            tp.add("Class",p3);
            tp.add("Teacher schedule",p4);
            tp.add("Student schedule",p5);
            f.add(tp);
            f.setSize(500,600);
            f.setLayout(null);
            f.setVisible(true);
        }
}
