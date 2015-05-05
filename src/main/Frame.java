package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    private Container contentPane;
    JButton button;

    public Frame(){
        super("checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(new Board(500, 8));
        button = new JButton("press me");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
            int i = 5;
        });



        pack();
    }


    public static void main(String[] args){
        Frame board = new Frame();
        board.setVisible(true);
    }

}
