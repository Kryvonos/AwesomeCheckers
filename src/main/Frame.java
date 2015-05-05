package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    private Container contentPane;
    JButton button;
    JButton button2;
    Server server = new Server();
    Client client = new Client();
    public Frame(){
        super("checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        button = new JButton("press me");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("action button");

                server.getPlayerStep(20, 30);

                System.out.println(server.getName() + " - try");
            }
        });
         contentPane.add(button);

        button2 = new JButton("press me client");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("action button");

                client.getPlayerStep(60, 79);

                System.out.println(client.getName() + " - try");
            }
        });
        contentPane.add(button2);

        pack();
    }


    public static void main(String[] args){
        Frame board = new Frame();
        board.setVisible(true);
    }

}
