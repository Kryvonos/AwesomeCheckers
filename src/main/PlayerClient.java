package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayerClient implements Runnable {
	private static int client = 0;
	private int id;
	 private String host;
	 private int port;
	 private ObjectInputStream in = null;
	 private ObjectOutputStream out = null;
	 private boolean isRunning = true;

	public PlayerClient(int port, String host){
		this.host = host;
		this.port = port;
		id = client%2;
		client++;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		
			try {
				Socket socket = new Socket(host, port);
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				while(isRunning){
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}
	
	
	public void sendMove(Move move, boolean isOver){
		try {
			out.writeBoolean(isOver);
			out.writeObject(move);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getId(){
		return id;
	}
	public Object getObject(){
		Object move = null;
		try {
			move = in.readObject();
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
