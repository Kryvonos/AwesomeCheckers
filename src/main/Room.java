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
		 int numberStep = 0;
		 boolean sending = true;
		while (isOver) {
			if (sockets[0] != null && sockets[1] != null) {
				try {
					while (sending) {
						sending = input[numberStep%2].readBoolean();
						obj = (Move) input[numberStep%2].readObject();
						out[(numberStep+1)%2].writeObject(obj);
						out[(numberStep+1)%2].flush();

					}
					out[(numberStep+1)%2].writeObject(null);
					out[(numberStep+1)%2].flush();
					sending = true;
					++numberStep;
/*
					while (sending) {
						sending = input[1].readBoolean();
						obj = (Move) input[1].readObject();
						out[0].writeObject(obj);
						out[0].flush();

					}
					out[0].writeObject(null);
					out[0].flush();
*/
					break;
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
