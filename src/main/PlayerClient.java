package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PlayerClient implements Runnable {
	private static int client = 0;
	private int id;
	private String host;
	private int port;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private boolean isRunning = true;

	public PlayerClient(int port, String host) {
		this.host = host;
		this.port = port;
		id = client % 2;
		client++;
		new Thread(this).start();
	}

	@Override
	public void run() {

		try {
			Socket socket = new Socket(Server.HOST, Server.PORT);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			try {
				while (isRunning) {
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// }
				// System.out.println("nothing");
			} finally {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMove(Move move, boolean isOver) {
		try {

			out.writeBoolean(isOver);
			out.writeObject(move);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(out);
	}

	public int getId() {
		return id;
	}

	public ArrayList<Move> getObject() {
		System.out.println("get Move");
		Move abj = null;
		ArrayList<Move> move = new ArrayList<Move>();
		try {
			abj = (Move) in.readObject();
			while (abj != null) {
				move.add(abj);
				abj = (Move) in.readObject();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return move;
	}
}
