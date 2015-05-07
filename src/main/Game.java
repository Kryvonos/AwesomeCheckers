package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Game extends JFrame implements Runnable {

	public static int WIDTH = 700;
	public static int HEIGHT = 500;
	
	public final String HOST;
	public final int PORT;
	
	private Container contPane;
	private Color bgColor = new Color(250, 250, 250);
	
	private CheckersPanel world = new CheckersPanel(this);
	private Chat chat;
	private JPanel statusLine = new JPanel();
	
	private Player player;
	private Enemy enemy;
	private Player currentPlayer;
	
	private boolean isRunning = true;
	
	public Game(String host, int port) {
		super("AwesomeCheckers");
		
		HOST = host;
		PORT = port;
		chat = new Chat(HOST, PORT);
		
		player = new Player();
		enemy = new Enemy();
		currentPlayer = player;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(false);
	
        contPane = getContentPane();
        contPane.setLayout(new GridBagLayout());
        contPane.setBackground(bgColor);
        
        init();
        
        pack();
        setResizable(false);
		setVisible(true);
	}
	
	public Game() {
		this(null, 0);
	}
	
	private void init() { 
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .5f;
        c.weighty = .001f;
        c.insets = new Insets(30, 30, 30, 30);
        contPane.add(world, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = .2f;
        c.anchor = GridBagConstraints.LINE_START;        
        contPane.add(chat, c);
            
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = .999f;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_END;
        statusLine.add(new JLabel("Status line"));
        statusLine.setBackground(new Color(240, 240, 240));
        contPane.add(statusLine, c);
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public void run() {	
		while (isRunning) {
			world.update();
			world.repaint();

            try {
                Thread.sleep(10);
            } catch (Exception e) {}
		}
	}
	
	
	public static void main(String[] args) {
		Game checkers = new Game("127.0.0.1", 8081);
		
		checkers.start();
	}

}
