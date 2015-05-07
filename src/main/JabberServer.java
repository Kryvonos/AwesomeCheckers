package main;

import java.net.Socket;

public class JabberServer implements Runnable {

	private Socket socket;
	private Server server;

	public JabberServer(Socket socket, Server server) {
		System.out.println("labber");
		this.socket = socket;
		this.server = server;
		new Thread(this).start();
	}

	@Override
	public void run() {
		for (Room r : server.getRoomsList()) {
			if (r.getNumberPlayers() < 2) {
				r.addPlayer(socket);
				return;
			}
		}
		String id = "game: " + server.getRoomsList().size();
		Room room = new Room(id);
		room.addPlayer(socket);
		server.getRoomsList().add(room);
	}

}
