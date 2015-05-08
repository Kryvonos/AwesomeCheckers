package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JabberServer extends Thread {

	private Socket socket;
	private Server server;
	
	ObjectInputStream in;
	ObjectOutputStream out;

	public JabberServer(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(this).start();
	}

	public JabberServer(Socket socket) {
		this.socket = socket;

		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void run() {
//		for (Room r : server.getRoomsList()) {
//			if (r.getNumberPlayers() < 2) {
//				r.addPlayer(socket);
//				return;
//			}
//		}
//		String id = "game: " + server.getRoomsList().size();
//		Room room = new Room(id);
//		room.addPlayer(socket);
//		server.getRoomsList().add(room);
		System.out.println("serverThread run");
		try {
			System.out.println((Move) in.readObject());
			out.writeObject(new Move(0, 0, 0, 0));
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
