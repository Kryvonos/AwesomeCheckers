package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Chat extends JPanel {
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	
	private String ip;
	private int port;
	
	public Chat(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		init();
	}
	
	public void init() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(new Color(244, 244, 244)));
		add(new JLabel("There should be the chat"));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Chat("127.0.0.1", 8081));
		
		frame.pack();
		frame.setVisible(true);
	}

}
