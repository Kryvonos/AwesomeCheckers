package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Chat extends JPanel implements ActionListener {
	private Client client;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JFormattedTextField jFormattedTextField1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
	private String ip;
	private int port;
	
	public Chat(String ip, int port) {
		this.ip = ip;
		this.port = port;
		init();
		String input = JOptionPane.showInputDialog("Enter login:");
		client = new Client("localhost", 1500, input, this);
		
		if (!client.start())
			return;
		
	}
	
	void append(String str) {
		jTextArea1.append(str);
		jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
		
	}

	
	public void init() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(new Color(244, 244, 244)));
		//add(new JLabel("There should be the chat"));
		
		jPanel1 = new javax.swing.JPanel();
		jFormattedTextField1 = new javax.swing.JFormattedTextField();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		jButton3.addActionListener(this);
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		//setBackground(new java.awt.Color(147, 156, 209));
		//setForeground(new java.awt.Color(180, 124, 124));


		jButton1.setText("Send");

	jButton2.setText("Online users");

	jButton3.setText("Logout");


		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addComponent(
												jButton2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												108,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jButton3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												87,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jButton1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												108,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jFormattedTextField1));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(
												jFormattedTextField1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton1)
														.addComponent(jButton2)
														.addComponent(jButton3))));

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1.setEditable(false);
		jTextArea1.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED));
		jScrollPane1.setViewportView(jTextArea1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jPanel1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														477, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										309,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(4, 4, 4)));

		//pack();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if it is the logout button
		if (o == jButton3) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
			return;
		}
		// if it the online users button
		if (o == jButton2) {
			client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
			return;
		}

		// send it is coming from the JTextField
		if (o == jButton1) {
			// just have to send the message
			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE,
					jFormattedTextField1.getText()));
			jFormattedTextField1.setText("");
			return;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Chat("127.0.0.1", 8081));
		
		frame.pack();
		frame.setVisible(true);
	}

}
