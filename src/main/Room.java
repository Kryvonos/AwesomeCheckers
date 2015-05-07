package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Room implements Runnable {

	private boolean isOver = true;
	private String id = null;
	private int numberPlayers;
	private Socket[] sockets;
	private ObjectOutputStream[] out;
	private ObjectInputStream[] input;

	public Room(String id) {
		System.out.println("room");
		this.id = id;
		this.sockets = new Socket[2];
		this.out = new ObjectOutputStream[2];
		this.input = new ObjectInputStream[2];

		new Thread(this).start();
	}

	public synchronized int getNumberPlayers() {
		// TODO Auto-generated method stub
		return numberPlayers;
	}

	public synchronized void addPlayer(Socket socket) {
		if (sockets[0] == null) {
			sockets[0] = socket;
			try {
				out[0] = new ObjectOutputStream(socket.getOutputStream());
				input[0] = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++numberPlayers;
		} else {
			sockets[1] = socket;
			try {
				out[1] = new ObjectOutputStream(socket.getOutputStream());
				input[1] = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++numberPlayers;
		}
	}

	@Override
	public void run() {
		Move obj = null;
		while (isOver) {
			if (sockets[0] != null && sockets[1] != null) {
				try {
					boolean sending = true;
					while (sending) {
						sending = input[0].readBoolean();
						obj = (Move) input[0].readObject();

						out[1].writeObject(obj);
						out[1].flush();
					}
					sending = true;
					while (sending) {
						sending = input[1].readBoolean();
						obj = (Move) input[1].readObject();
						isOver = input[1].readBoolean();
						out[0].writeObject(obj);
						out[0].flush();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			sockets[0].close();
			sockets[1].close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isOver() {

		return isOver;
	}

}
