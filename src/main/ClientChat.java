package main;

import java.net.*;
import java.io.*;
import java.util.*;

/*
 * The Client that can be run both as a console or a GUI
 */
public class ClientChat {

	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;

	private Chat cg;

	private String server, username;
	private int port;

	ClientChat(String server, int port, String username) {

		this(server, port, username, null);
	}

	ClientChat(String server, int port, String username, Chat cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.cg = cg;
	}

	public boolean start() {

		try {
			socket = new Socket(server, port);
		} catch (Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":"
				+ socket.getPort();
		display(msg);

		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		new ListenFromServer().start();

		try {
			sOutput.writeObject(username);
		} catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}

		return true;
	}

	private void display(String msg) {
		if (cg == null)
			System.out.println(msg);
		else
			cg.append(msg + "\n");
	}

	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		} catch (IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	private void disconnect() {

		try {
			if (sInput != null)
				sInput.close();
		} catch (Exception e) {
		}
		try {
			if (sOutput != null)
				sOutput.close();
		} catch (Exception e) {
		}
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		}

	}

	public static void main(String[] args) {

		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";

		ClientChat client = new ClientChat(serverAddress, portNumber, userName);

		if (!client.start())
			return;

		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print("> ");

			String msg = scan.nextLine();

			if (msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));

				break;
			}

			else if (msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
			} else {
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}

		client.disconnect();

	}

	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					String msg = (String) sInput.readObject();

					if (cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					} else {
						cg.append(msg);
					}
				} catch (IOException e) {
					display("Goodbuy");
					break;
				}

				catch (ClassNotFoundException e2) {
				}
			}
		}
	}
}
