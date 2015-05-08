package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class NewClientGame implements Serializable {

	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;

	private String server;
	private int port;
	private boolean flag;

	NewClientGame(String server, int port, boolean flag) {

		this.server = server;
		this.port = port;
		this.flag = flag;
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
		// display(msg);
		System.out.println(msg);

		try {
			sInput = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		new ListenFromServer().start();

		// try {
		// // if(username == null || username.trim().equals(""))
		// // username = "Anonymous";
		// //sOutput.writeObject(msg);
		// } catch (IOException eIO) {
		// display("Exception doing login : " + eIO);
		// disconnect();
		// return false;
		// }

		return true;
	}

	private void display(String msg) {
		// if (cg == null)
		System.out.println(msg);
		// else
		// cg.append(msg + "\n");
	}

	void sendMove(Move move) {
		try {
			sOutput.writeObject(move);
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
		Scanner scan = new Scanner(System.in);

		boolean b = scan.nextBoolean();

		NewClientGame client = new NewClientGame(serverAddress, portNumber, b);

		if (!client.start())
			return;

		// client.sendMove(new Move(4,4, 7, 7));

	}

	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {

					Move m = new Move(0, 0, 0, 0);
			
						sOutput.writeObject(m);
					Move res = (Move) sInput.readObject();
					System.out.println(res);
					
				
					// if (cg == null) {
					// System.out.println(msg);
					// System.out.print("> ");
					// // } else {
					// cg.append(msg);
					// }
				} catch (IOException e) {
					display("Goodbuy");
					break;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
