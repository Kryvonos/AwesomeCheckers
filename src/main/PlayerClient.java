package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.sun.xml.internal.fastinfoset.stax.events.ReadIterator;

public class PlayerClient implements Runnable {
	private static int client = 0;
	private int id;
	private String host;
	private int port;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private boolean isRunning = true;
	private Game game;
	private Move move = null;
	private boolean isOver;
	ArrayList<Move> moves;
	private Socket socket;

	public PlayerClient(int port, String host, Game game) {
		this.host = host;
		this.port = port;
		this.game = game;
		try {
			socket = new Socket(Server.HOST, Server.PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			new Thread(this).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public void run() {
		System.out.println("I'm in run client");
		while (isRunning){
			try {
				out.writeObject(new Move(34,5,67,8));
				out.flush();
				
				System.out.println((Move) in.readObject());
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("fail to close socket");
				}
			}
			
		}
		
	}

	public void sendMove(Move move, boolean isOver) {

		this.move = move;
		this.isOver = isOver;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Move> getMoveList() {
		return moves;
	}
	
	public static void main (String args[]){
		PlayerClient player = new PlayerClient(Server.PORT, Server.HOST, new Game());
	}

}
