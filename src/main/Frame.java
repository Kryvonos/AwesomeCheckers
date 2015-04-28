package main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    Container contentPane;

    public Frame(){
        super("checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(new Board(500, 8));
        pack();
    }


    public static void main(String[] args){
        Frame board = new Frame();
        board.setVisible(true);
    }

}
