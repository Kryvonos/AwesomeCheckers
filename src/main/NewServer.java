package main;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;

public class NewServer {

	private static int uniqueId;
	private ArrayList<ClientThread> al;
	// private SimpleDateFormat sdf;
	private int port;
	private boolean keepGoing;
	private Boolean pointer = true;

	public NewServer(int port) {

		this.port = port;

		// sdf = new SimpleDateFormat("HH:mm:ss");

		al = new ArrayList<ClientThread>();
	}

	public void start() {
		keepGoing = true;

		try {

			ServerSocket serverSocket = new ServerSocket(port);

			while (keepGoing) {

				display("Server waiting for Clients on port " + port + ".");

				Socket socket = serverSocket.accept();

				if (!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);
				al.add(t);
				t.start();
			}

			try {
				serverSocket.close();
				for (int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					} catch (IOException ioE) {

					}
				}
			} catch (Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}

		catch (IOException e) {
			// String msg = sdf.format(new Date())
			// + " Exception on new ServerSocket: " + e + "\n";
			// display(msg);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		} catch (Exception e) {
		}
	}

	private void display(String msg) {
		// String time = sdf.format(new Date()) + " " + msg;
		// System.out.println(time);

	}

	private synchronized void broadcast(Move move) {

		// String time = sdf.format(new Date());
		// String messageLf = time + " " + message + "\n";

		System.out.print(move);

		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);

			if (!ct.writeMsg(move)) {
				al.remove(i);
				display("Disconnected Client ");
			}
		}
	}

	synchronized void remove(int id) {

		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);

			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	public static void main(String[] args) {
		int portNumber = 1500;
		NewServer server = new NewServer(portNumber);
		server.start();
	}

	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		int id;
		Boolean check = false;

		// String username;

		Move move;

		String date;

		ClientThread(Socket socket) throws ClassNotFoundException {

			id = ++uniqueId;
			this.socket = socket;

			System.out
					.println("Thread trying to create Object Input/Output Streams");
			try {

				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());

				// username = (String) sInput.readObject();

				// broadcast(new Move(0,0,0,0));
			} catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}

			// date = new Date().toString() + "\n";
		}

		public void run() {

			boolean keepGoing = true;
			while (keepGoing) {

				try {
					synchronized (check) {
						// System.out.println(check!=data.getState());

						try {
							if (id == 1 && pointer == true) {
								try {
									move = (Move) sInput.readObject();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(move);
								broadcast(move);
								pointer = false;
								check.notifyAll();
								check.wait();
							} else if (id == 2 && pointer == false) {
								try {
									move = (Move) sInput.readObject();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(move);
								broadcast(move);
								pointer = true;
								check.notifyAll();
								check.wait();
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (InterruptedException e) {
				}

			}

			remove(id);
			close();
		}

		private void close() {

			try {
				if (sOutput != null)
					sOutput.close();
			} catch (Exception e) {
			}
			try {
				if (sInput != null)
					sInput.close();
			} catch (Exception e) {
			}
			;
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}

		private boolean writeMsg(Move move) {

			if (!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(move);
			}

			catch (IOException e) {
				// display("Error sending message to " + username);
				// display(e.toString());
			}
			return true;
		}
	}

}
