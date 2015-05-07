package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Chat extends JPanel implements ActionListener {
	private ClientChat client;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private JTextField jFormattedTextField1;
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
		client = new ClientChat("localhost", 1500, input, this);

		if (!client.start())
			return;

	}

	void append(String str) {
		jTextArea1.append(str);
		jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);

	}

	private JButton createFlatButton(String text) {
		final JButton button = new JButton(text);
		final Color hoverFillColor = new Color(151, 62, 63);

		final Color fillColor = new Color(141, 52, 53);

		button.setBackground(fillColor);
		button.setForeground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(true);
		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(hoverFillColor);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(fillColor);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		});
		return button;
	}

	public void init() {
		setOpaque(true);
		setBackground(new Color(0, 0, 0, 0));
		GridBagLayout gb = new GridBagLayout();
		setLayout(gb);
		GridBagConstraints c = new GridBagConstraints();
		jPanel1 = new JPanel();
		jFormattedTextField1 = new JTextField();
		jButton1 = createFlatButton("Send");
		jButton2 = createFlatButton("Online");
		jButton3 = createFlatButton("Logout");
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		jButton3.addActionListener(this);

		jTextArea1 = new JTextArea(5, 10);
		jTextArea1.setLineWrap(true);
		jTextArea1.setWrapStyleWord(true);
		jTextArea1.setEditable(false);
		jTextArea1.setBorder(BorderFactory.createLineBorder(new Color(244, 244,
				244)));
		jTextArea1.setBorder(BorderFactory.createCompoundBorder(
				jTextArea1.getBorder(),
				BorderFactory.createEmptyBorder(10, 17, 10, 17)));
		jScrollPane1 = new JScrollPane(jTextArea1);
		jFormattedTextField1.addActionListener(this);
		jFormattedTextField1.setPreferredSize(new Dimension(100, 70));
		jFormattedTextField1.setMargin(new Insets(10, 10, 0, 10));
		c.insets = new Insets(5, 5, 5, 5);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 0.5;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.BOTH;
		add(jScrollPane1, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(jFormattedTextField1, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.3;
		c.weighty = 0.1;
		add(jButton2, c);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.7;
		c.weighty = 0.1;
		add(jButton3, c);
		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 0.3;
		c.weighty = 0.1;
		add(jButton1, c);
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
		if (o == jButton3) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
			return;
		}
		if (o == jButton2) {
			client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
			return;
		}

		if (o == jButton1 || o == jFormattedTextField1) {
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
